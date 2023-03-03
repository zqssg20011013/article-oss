package com.example.admin.Controller;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.admin.PO.Admin;
import com.example.admin.PO.Result;
import com.example.admin.PO.User;
import com.example.admin.PO.admin_type;
import com.example.admin.Service.UpdateService;
import com.example.admin.Service.Userservice;
import com.example.admin.dto.PageDto;
import com.example.admin.dto.banDto;
import com.example.admin.dto.loginDto;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
/**
 * @project: blog
 * @ClassName: AdminController
 * @author: smallwei
 * @creat: 2022/8/6 1:58
 * 描述:
 */
@RefreshScope
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    Userservice userservice;
    @Autowired
    UpdateService updateService;
    
    @RequestMapping("/post")

    public String  get2(){
   //  String defult=    restTemplate.getForObject("http://service-es/",String.class);
        String defult=    restTemplate.getForObject("http://service-es/es/get",String.class);
         return defult;
    }
    @RequestMapping("/login")
     @SentinelResource(value="login")
    @ResponseBody
    public Result  login(@RequestBody Admin  user){
        if(  userservice.gettype(user.getUsername())){
            return  new Result(null,"登陆成功",103);
        }
        if( userservice.getlockype(user.getUsername())){
            return new Result(null,"登录失败",105);
        }
        user = userservice.login(user);
        if(user!=null){
        String token= UUID.randomUUID()+"";
        redisTemplate.opsForValue().setIfAbsent(token,user, Duration.ofDays(7));

         return new Result(token,"登录成功",100);
        }
        return new Result(null,"登录失败",104);
    }
    @SentinelResource(value = "login")
    @RequestMapping("/token")
    @ResponseBody
    public  Result loginByToken(@RequestBody String token){

         JSONObject jsonObject=JSONObject.parseObject(token);
        token= jsonObject.getString("token");

        Admin user= (Admin) redisTemplate.opsForValue().get(token);
        System.out.print(user);
        User User = userservice.getUser(user);
         user=  userservice.login(user);
        if(user!=null){
            return new Result(User,"登录成功",100);
        }
        return new Result(null,"登录失败",104);
    }
    @RequestMapping("/getbytoken")
    @ResponseBody
    public User getUserByToken(@RequestBody String token){
        JSONObject jsonObject=JSONObject.parseObject(token);
        token= jsonObject.getString("token");

        Admin user= (Admin) redisTemplate.opsForValue().get(token);
        User User = userservice.getUser(user);
        if(user!=null){
            System.out.print(User);
            return User;
        }
        return new User();
    }
    @RequestMapping("/getbypassword")
    @ResponseBody
    public User user(@RequestBody loginDto dto){

        User user = userservice.getuser(dto.getUsername());

        return user;
    }
    // User信息
    @SentinelResource(value = "UpdateUseer")
    @RequestMapping("/UpdateUseer")
    @ResponseBody
    public User Update(@RequestBody User user){
        System.out.print(   user);

        if(user!=null){
          user=  userservice.updset(user);
            return user;
        }
        return new User();
    }
    //image
    @SentinelResource(value = "UpdateImage")
    @RequestMapping("/UpdateImage")
    @ResponseBody
    public String UpdateImage(@RequestParam String username, @RequestParam("file") MultipartFile file)
            throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        User user=userservice.getuser(username);
        if(user==null){
            return null;
        }else{
             String url=updateService.UpdateImg(file.getInputStream(),username,file.getOriginalFilename(),file.getContentType());
                user.setImgeUrl(url);
                userservice.updset(user);
        }
         return "更新成功";
    }
     @RequestMapping("/search")
     @ResponseBody
    public List<admin_type> search(@RequestBody  String username){
         JSONObject jsonObject=JSONObject.parseObject(username);
         username= jsonObject.getString("username");

         return  userservice.Search(username);
     }
    @RequestMapping("/list")
    @ResponseBody
    public List<admin_type> list(){
        return  userservice.List();
    }
    @RequestMapping("/create")
    @ResponseBody
    public void  create(@RequestBody  Admin admin) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
           userservice.create(admin);
           updateService.create(admin.getUsername());
           userservice.type(admin.getUsername());

    }
    @ResponseBody
    @RequestMapping("/List_User")
    public PageDto List(@RequestBody  String currentpage){
        Integer page = Integer.parseInt(JSON.parseObject(currentpage).get("currentpage").toString());

        return  userservice.selectPage( page);
    } @ResponseBody
    @RequestMapping("/manager")
    public void loginmanaer(){

    }
    @ResponseBody
    @RequestMapping("/loginsearch")
    public void loginsearch(){

    }
    @ResponseBody
    @RequestMapping("/loginUserspace")
    public void loginuserspace(){

    }
    @ResponseBody
    @RequestMapping("/Bannedorunclock")
    public void  bannedorunclock(@RequestBody banDto dto){
        System.out.print(dto);
    userservice.setBanned_state(dto);
    }
    @ResponseBody
    @RequestMapping("/cache")
    public void  cache(@RequestBody String token){
        JSONObject jsonObject=JSONObject.parseObject(token);
        token= jsonObject.getString("token");
         redisTemplate.delete(token);
    }

}


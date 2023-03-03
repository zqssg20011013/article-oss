package com.example.admin.Controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.admin.PO.image;
import com.example.admin.Service.UpdateService;
import com.example.admin.Service.Userservice;
import com.example.admin.Service.imageService;
import com.example.admin.dto.Recommend;
import com.example.admin.dto.Url;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @project: blog
 * @ClassName: imagController
 * @author: smallwei
 * @creat: 2022/9/1 19:09
 * 描述:
 */
@Controller
@CrossOrigin
@RequestMapping("/image")
public class imagController {
    @Autowired
    imageService imageservice;
    @Autowired
    Userservice userservice;
    @Autowired
    RestTemplate restTemplate;;
    @Autowired
    UpdateService updateService;
    @ResponseBody
    @RequestMapping("/add")
    public ResponseEntity<String> add(@RequestParam() String articleid, @RequestParam String username, @RequestParam("file")MultipartFile file) throws IOException {
         System.out.print( file.getOriginalFilename());
        image image=  imageservice.getImage(articleid);
        if(image==null){
         image=new image();
        image.setUsername(username);
        image.setArticleid(articleid);
            byte[]bytes=   file.getBytes();
            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("file", bytes);
            requestBody.add("username",username);
            requestBody.add("filename",file.getOriginalFilename());
            requestBody.add("contenttype",file.getContentType());
          //  requestBody.add("username",username);
            System.out.print(requestBody.values());
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> Entity = new HttpEntity<>(requestBody, header);
           ResponseEntity<String> result=  restTemplate.postForEntity("http://server-common/uploadImg",Entity,String.class);
            image.setUrl(result.toString());
            imageservice.add(image);
            return result;
        }else {
            byte[] bytes = file.getBytes();
            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("bytes",bytes);
            requestBody.add("username",username);
            requestBody.add("filename",file.getOriginalFilename());
            requestBody.add("contenttype",file.getContentType());
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> Entity = new HttpEntity<>(requestBody, header);
            ResponseEntity<String> result = restTemplate.postForEntity("http://server-common/uploadImg",Entity, String.class);
            imageservice.upser(image);
            return result;
        }



    }
    @ResponseBody
    @RequestMapping("/add2")
    public String add2(@RequestParam() String articleid, @RequestParam String username, @RequestParam("file")MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        image image=  imageservice.getImage(articleid);
        if(image==null){
            image=new image();
            image.setUsername(username);
            image.setArticleid(articleid);
          String url=  updateService.UpdateImg(file.getInputStream(),username,file.getOriginalFilename(),file.getContentType());
           image.setUrl(url);
          imageservice.add(image);

        }

        String url=  updateService.UpdateImg(file.getInputStream(),username,file.getOriginalFilename(),file.getContentType());
        image.setUrl(url);
        imageservice.upser(image);
        Url dto=new Url(url,articleid);
         return  restTemplate.postForObject("http://server-common/common/update",dto, String.class);

    }

    @ResponseBody
    @RequestMapping("/List")
    public JSONArray List(@RequestBody String username ){
        System.out.print(username);
        JSONObject jsonObject=JSONObject.parseObject(username);
        username= jsonObject.getString("username");
        List<String> articleList=imageservice.ImageList(username);
        JSONArray jsonarray=JSONArray.parseArray(JSON.toJSONString(articleList));
        return jsonarray;
    }
    @ResponseBody
    @RequestMapping("/get")
    public image Get(@RequestBody String id){
       System.out.print(id);
       System.out.print( imageservice.getImage(id));

        return imageservice.getImage(id);

    }
    @ResponseBody
    @RequestMapping("/delete")
    public String  delete(@RequestBody String id){
        JSONObject jsonObject=JSONObject.parseObject(id);
        id= jsonObject.getString("id");
        System.out.print(id);
        if(  imageservice.getImage(id)!=null){
            imageservice.delete(id);
        }
         restTemplate.postForObject("http://server-common/common/deleteById",id,String.class);
        return "删除成功";
    }
    @ResponseBody
    @RequestMapping("/removerecommend")
    public String remove(@RequestBody String id){
        JSONObject jsonObject=JSONObject.parseObject(id);
        id= jsonObject.getString("id");
       Recommend recommend=new Recommend(imageservice.getImage(id),id);
        restTemplate.postForObject("http://server-common/common/concelrecommend",recommend,String.class);
        return "删除成功";
    }
    @ResponseBody
    @RequestMapping("/recommend")
    public String recommend(@RequestBody String id){
        JSONObject jsonObject=JSONObject.parseObject(id);
        id= jsonObject.getString("id");
        Recommend recommend=new Recommend(imageservice.getImage(id),id);
        restTemplate.postForObject("http://server-common/common/recommend",recommend,String.class);
        return "添加成功";
    }
    @ResponseBody
    @RequestMapping("/audit")
    public void audit(@RequestBody String id){
        JSONObject jsonObject=JSONObject.parseObject(id);
        id= jsonObject.getString("id");
        Recommend recommend=new Recommend(imageservice.getImage(id),id);
        restTemplate.postForObject("http://server-common/common/audit",recommend,String.class);

    }
    @ResponseBody
    @RequestMapping("/removeaudit")
    public String removeAudit(@RequestBody String id){
        JSONObject jsonObject=JSONObject.parseObject(id);
        id= jsonObject.getString("id");
        Recommend recommend=new Recommend(imageservice.getImage(id),id);
        restTemplate.postForObject("http://server-common/common/removeaudit",recommend,String.class);
        return "删除成功";
    }
}



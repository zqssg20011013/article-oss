package com.example.servercommon.controler;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.servercommon.dto.*;
import com.example.servercommon.pojo.article;
import com.example.servercommon.pojo.result;
import com.example.servercommon.service.dbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @project: blog
 * @ClassName: commonController
 * @author: smallwei
 * @creat: 2022/8/12 7:32
 * 描述:
 */
@CrossOrigin
@Controller
@RequestMapping("/common")
public class commonController {
    @Autowired
    dbService dbservice;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @ResponseBody
    @RequestMapping("/save")
    @SentinelResource(value="save")
    public result save(@RequestBody article article) {
        if(article.getRecomend()==null){
            article.setRecomend(false);
        }
        dbservice.add(article);
        String entity = restTemplate.postForObject("http://service-es/es/save", article, String.class);
        return new result(entity,"添加成功",200);
    }
    @ResponseBody
    @SentinelResource(value="update")
    @RequestMapping("/update")
    public String save(@RequestBody Url url ) {
        article article=dbservice.getById(url.getArticleid());
        article.setUrl(url.getUrl());
        dbservice.Update(article);
        return "更新成功";
    }

    @ResponseBody
    @RequestMapping("/delete")
    public void delete(@RequestBody List<article> articleList) {
        dbservice.delete(articleList);
        restTemplate.postForObject("http://service-es/es/delete", articleList, String.class);
    }
    @ResponseBody
    @RequestMapping("/deleteById")
    public void DeleteById(@RequestBody String id) {
        dbservice.DeleteById(id);
        restTemplate.postForObject("http://service-es/es/deleteById", id, String.class);
    }
    @ResponseBody
    @RequestMapping("/search")
    @SentinelResource(value="search")
    public Pagedto search(@RequestBody Searchdto dto) {
    Pagedto pagedto =   restTemplate.postForObject("http://service-es/es/searchLimit", dto,Pagedto.class);
        return pagedto;
}
    @ResponseBody
    @RequestMapping("/searchbytitileandcontent")
    public List<article>searchbytitileandcontent(@RequestBody article article){
        List<article>articleList= (List<com.example.servercommon.pojo.article>) restTemplate.postForObject("http://service-es/es/searchbytitleandcontent", article,List.class);
        return articleList;
    }
    @ResponseBody
    @RequestMapping("/ListById")
    public JSONArray List(@RequestBody atDto dto ){
        List<article>articleList=dbservice.GetByUserId(dto.getId());
        JSONArray jsonarray=JSONArray.parseArray(JSON.toJSONString(articleList));
        return jsonarray;
    }
    @Transactional
    @ResponseBody
    @RequestMapping("/recommend")
    public String Recommend(@RequestBody String id){
        JSONObject jsonObject=JSONObject.parseObject(id);
        id= jsonObject.getString("id");
        article article=(dbservice.getById(id));
        article.setRecomend(true);
        dbservice.Update(article);
        redisTemplate.opsForList().rightPush("recommend",dbservice.getById(id));
        return "推荐成功 ";
    }
    @Transactional
    @ResponseBody
    @RequestMapping("/concelrecommend")
   public String Concelrecommend(@RequestBody String id){
        JSONObject jsonObject=JSONObject.parseObject(id);
        id= jsonObject.getString("id");
        System.out.print(id);
        article article=(dbservice.getById(id));
        redisTemplate.opsForList().remove("recommend",0,article);
        article.setRecomend(false);
        dbservice.Update(article);
        return "删除成功";
    }
    @ResponseBody
    @RequestMapping("/audit")
    public String audit(@RequestBody String id){
        JSONObject jsonObject=JSONObject.parseObject(id);
        id= jsonObject.getString("id");
        redisTemplate.opsForList().rightPush("audit",   dbservice.getById(id));

        redisTemplate.opsForList().remove("recommend",0,dbservice.getById(id));
        return "通过";

    }
    @ResponseBody
    @RequestMapping("/ListAll")
    public recommendDto ListRecommend(@RequestBody String currentpage){
        Integer page = Integer.parseInt(JSON.parseObject(currentpage).get("currentpage").toString());
        long size=  redisTemplate.opsForList().size("recommend");
        int  totalpage= ((int)Math.ceil((double)size/(double)10));
        JSONArray jsonarray;
       if(page.intValue()==1){
           List list=  redisTemplate.opsForList().range("recommend",0,9);
          
          jsonarray=JSONArray.parseArray(JSON.toJSONString(list));
       }else{
          int left=(page-1)*10;
          int right=left+9;
           if(right>size){
               right= (int) size;
           }
        List list=  redisTemplate.opsForList().range("recommend",left,right);
         jsonarray=JSONArray.parseArray(JSON.toJSONString(list));

           }
        recommendDto dto=new recommendDto(jsonarray,totalpage,page);
        return dto;
    }
    //image
    @ResponseBody
    @RequestMapping("/ListAllimage")
    public JSONArray ListRecommendImage(){
        List list=  redisTemplate.opsForList().range("recommendImage",0,-1);
        JSONArray jsonarray=JSONArray.parseArray(JSON.toJSONString(list));
        return jsonarray;
    }
    @ResponseBody
    @RequestMapping("/ListAudit")
    public auditDto Listaudit(@RequestBody String currentpage){
        Integer page = Integer.parseInt(JSON.parseObject(currentpage).get("currentpage").toString());
        long size=  redisTemplate.opsForList().size("audit");
        int  totalpage= ((int)Math.ceil((double)size/(double)10));
        JSONArray jsonarray;
        if(page.intValue()==1){
            List list=  redisTemplate.opsForList().range("audit",0,9);

            jsonarray=JSONArray.parseArray(JSON.toJSONString(list));
        }else{
            int left=((page-1)*10);
            int right=left+9;
            if(right>size){
                right= (int) size;
            }
            List list=  redisTemplate.opsForList().range("audit",left,right);
            jsonarray=JSONArray.parseArray(JSON.toJSONString(list));

        }
        auditDto dto=new auditDto(jsonarray,totalpage,page);
        return dto;
    }
    @Transactional
    @ResponseBody
    @RequestMapping("/removeaudit")
    public String removeAudit(@RequestBody  String id){
        JSONObject jsonObject=JSONObject.parseObject(id);
        id= jsonObject.getString("id");
        redisTemplate.opsForList().remove("audit",0,dbservice.getById(id));

        return "删除成功";
    }
    @ResponseBody
    @RequestMapping("/Search")
    public JSONArray search(@RequestBody String title){
        JSONObject jsonObject=JSON.parseObject(title);
        title= jsonObject.getString("title");
      article article= new article();
      article.setTitle(title);
        List<article>articleList= (List<com.example.servercommon.pojo.article>) restTemplate.postForObject("http://service-es/es/search", article,Object.class);
        JSONArray jsonarray=JSONArray.parseArray(JSON.toJSONString(articleList));
        return jsonarray;
    }
    @ResponseBody
    @RequestMapping("/ListDesc")
    public List<article> ListDesc(@RequestBody atDto dto){
       return  dbservice.listDesc(dto.getCurrentpage(),dto.getPagesize(),dto.getId());
    }
    @ResponseBody
    @RequestMapping("/random")
    public List<article> random(){
    Random random =new Random();
    Long size=redisTemplate.opsForList().size("audit");
    int min=1;
    List<article> articleList = new ArrayList<article>();
    for(int i=0;i<8;i++){
       int num= random.nextInt(size.intValue()-min+1);
       articleList.add((article) redisTemplate.opsForList().index("audit",num));
    }
    return articleList;
    }

}
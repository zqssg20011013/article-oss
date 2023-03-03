package com.example.servicees.Controller;

import com.example.servicees.dto.Pagedto;
import com.example.servicees.dto.Searchdto;
import com.example.servicees.dto.searchPage;
import com.example.servicees.pojo.article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @project: blog
 * @ClassName: esController
 * @author: smallwei
 * @creat: 2022/8/6 16:45
 * 描述:
 */
@RefreshScope
@CrossOrigin
@RestController
@RequestMapping("/es")
public class esController {
    @Autowired
    com.example.servicees.service.esService esService;
     @ResponseBody
    @RequestMapping("/save")
     public  String save(@RequestBody article article){
        esService.save(article);
        System.out.print(article);
        return "ok";
     }
    @ResponseBody
     @RequestMapping("/search")
     public List<article>  Search(@RequestBody Searchdto dto){
     List<article> articleList= esService.Search(dto.getTitle());
        List<article> articleList2= esService.searchByTipe(dto.getType());
        articleList.stream().forEach(i-> {
            for (article i2 : articleList2) {
                if (i2 != i) {
                    articleList.add(i2);
                }
            }
        } );
        System.out.print(articleList);
      return articleList;
     }
    @ResponseBody
     @RequestMapping ("/delete")
    public void delete(@RequestBody  List<article> articleList){
        esService.delete(articleList);
     }
    @ResponseBody
     @RequestMapping ("/deleteById")
    public void delete(@RequestBody  String id){
        esService.deleteById(id);
    }
    @ResponseBody
    @RequestMapping ("/searchLimit")
    public Pagedto searchLimit(@RequestBody searchPage page){
        Integer current = Integer.parseInt(page.getCurrentpage());

        return esService.SearchLImit(page.getTitle(),page.getType(),current);
    }
    @ResponseBody
    @RequestMapping("/hello")
    public void hello(){
         System.out.print("hello,this is test");
    }
    @ResponseBody
    @RequestMapping("/hello2")
    public int hello(@RequestBody int id ){
        System.out.print("hello,this is test2");
        return id;
    } @ResponseBody
    @RequestMapping("/test")
    public List<article>  Search2(@RequestParam("title") String title,@RequestParam("type")String type){
        List<article> articleList= esService.Search(title);
        List<article> articleList2= esService.searchByTipe(type);
        articleList.stream().forEach(i-> {
            for (article i2 : articleList2) {
                if (i2 != i) {
                    articleList.add(i2);
                }
            }
        } );
        System.out.print(articleList);
        return articleList;
    }

}

package com.example.servercommon.service;
import com.example.servercommon.Utills.SpringDataPageable;

import com.example.servercommon.pojo.article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @project: blog
 * @ClassName: dbService
 * @author: smallwei
 * @creat: 2022/8/12 7:51
 * 描述:
 */
@Service
public class dbService {
    @Autowired
    com.example.servercommon.dao.mongodbDAO mongodbDAO;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;
    public void add(article article){
        String lockKey = "lockkey";
        try {
            boolean result=redisTemplate.opsForValue().setIfAbsent(lockKey,"db-lock");
            mongodbDAO.save(article);
        }finally {
            redisTemplate.delete(lockKey);
        }
    }
    public void delete(List<article> article){
        String lockKey = "lockkey";
        try {
            boolean result=redisTemplate.opsForValue().setIfAbsent(lockKey,"db-lock");
            mongodbDAO.deleteAll(article);

        }finally {
            redisTemplate.delete(lockKey);
        }
    }
    public List<article > GetByUserId(String Userid){
        return  mongodbDAO.getAllByUserid(Userid);
    }
    public List<article> GetByTitle(String Title){
        return mongodbDAO.getAllByTitle(Title);
    }
    public void DeleteById(String id){
         mongodbDAO.deleteById(id);
    }
    public article getById(String id){
        return mongodbDAO.getById(id);
    }
   public void Update(article article){
     mongodbDAO.save(article);
   }
    public void getPageNum(){
    }
    public List<article> listDesc( int currentpage,int pageSize,String userid) {

        List<article> articleList;
          Query  querycount=new Query();
        querycount.addCriteria(Criteria.where("userid").is(userid));
     long count=   mongoTemplate.count(querycount,"article");
     System.out.print(mongoTemplate.count(querycount,"article"));
     System.out.print(count);
        int pageNum = ((int) count + pageSize - 1)/pageSize;
        Query query = new Query();
        // 通过 _id 来排序
        query.with(Sort.by(Sort.Direction.ASC, "id"));
        if (pageNum != 1) {
            // number 参数是为了查上一页的最后一条数据
            int number = (pageNum - 1) * pageSize;
            query.limit(number);

            List<article> titles = mongoTemplate.find(query, article.class);
            // 取出最后一条
            article article = titles.get(titles.size() - 1);

            // 取到上一页的最后一条数据 id，当作条件查接下来的数据
            String id = article.getId();

            // 从上一页最后一条开始查（大于不包括这一条）
            query.addCriteria(Criteria.where("id").gt(id));
        }
        // 页大小重新赋值，覆盖 number 参数
        query.limit(pageSize);
        // 即可得到第n页数据
        articleList = mongoTemplate.find(query, article.class);

        return articleList;
    }
    public Page<article> paginationQuery(Integer pageNum) {

        SpringDataPageable pageable = new SpringDataPageable();
        Query query = new Query();
        List<article> orders = new ArrayList<article>();  //排序
        // 开始页
        pageable.setPagenumber(pageNum);
        // 每页条数
        pageable.setPagesize(5);
        // 排序
        // 查询出一共的条数
        Long count = mongoTemplate.count(query, article.class);
        // 查询
        List<article> list = mongoTemplate.find(query.with(pageable), article.class);
        // 将集合与分页结果封装
        Page<article> pagelist = new PageImpl<article>(list, pageable, count);

        return pagelist;
    }


}

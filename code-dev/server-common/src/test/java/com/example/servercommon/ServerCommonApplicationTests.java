package com.example.servercommon;

import com.alibaba.fastjson.JSONObject;
import com.example.servercommon.Utills.QiniuCloudUtil;
import com.example.servercommon.controler.commonController;
import com.example.servercommon.dao.mongodbDAO;
import com.example.servercommon.dto.Searchdto;
import com.example.servercommon.pojo.article;
import com.example.servercommon.pojo.image;
import com.example.servercommon.service.UpdateService;
import com.example.servercommon.service.dbService;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
@SpringBootTest
class ServerCommonApplicationTests {
   @Autowired
RedisTemplate redisTemplate;
   @Autowired
   dbService db;
    @Test
    void contextLoads() throws Exception {
       redisTemplate.opsForList().remove("recommend",0,db.getById("63ee215849a8587d66deed9f"));
}}
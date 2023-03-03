package com.example.admin.utiis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @project: blog
 * @ClassName: page
 * @author: smallwei
 * @creat: 2023/2/12 16:35
 * 描述:
 */

@Configuration
@ConditionalOnClass(value = {PaginationInterceptor.class})
 public class MybatisPlusconfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }}

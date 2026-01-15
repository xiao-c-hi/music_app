package com.example.courses.music.config;

import com.example.courses.music.service.SearchService;
import com.example.courses.music.service.impl.DatabaseSearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用来提供Bean
 */
@Configuration
public class BeanFactoryConfig {
    /**
     * 获取搜索服务
     *
     * @return
     */
    @Bean
    SearchService searchService() {
        //使用数据库实现
        return new DatabaseSearchService();

        //使用Elasticsearch实现
//        return new ElasticSearchService();
    }
}

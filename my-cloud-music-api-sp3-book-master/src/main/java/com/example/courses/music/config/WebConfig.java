package com.example.courses.music.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

/**
 * web配置
 */
@Configuration
public class WebConfig {
    /**
     * 用来根据响应body生成etag
     * 添加这个bean以后
     * 对于get请求就会生成etag
     * 同时get请求如果有etag就会自动处理
     * <p>
     * Etag只会节省网络带宽资源
     * 并不会节省服务器资源
     * 因为服务器会为每个响应计算Etag
     * javax.servlet.Filter
     *
     * @return
     */
    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
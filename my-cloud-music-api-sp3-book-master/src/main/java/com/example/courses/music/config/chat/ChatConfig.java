package com.example.courses.music.config.chat;

import io.rong.RongCloud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 聊天配置
 */
@Configuration
public class ChatConfig {
    /**
     * 聊天配置
     */
    @Autowired
    private ChatProperties properties;

    /**
     * 获取融云客户端
     *
     * @return
     */
    @Bean
    public RongCloud rongCloud() {
        //他内部实现就是单例，所以也可以不用现在这样封装，直接在用的地方调用如下方法
        return RongCloud.getInstance(properties.getKey(), properties.getSecret());
    }
}
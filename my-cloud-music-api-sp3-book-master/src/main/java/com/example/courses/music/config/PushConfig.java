package com.example.courses.music.config;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 极光推送配置
 */
@Configuration

//通过这种方法把配置文件中前缀为push开通的自动映射到当前类字段
//好处是不用每个字段写value注解了
@ConfigurationProperties(prefix = "push")
public class PushConfig {
    /**
     * key
     */
    private String key;

    /**
     * secret
     */
    private String secret;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * 提供极光推送客户端
     *
     * @return
     */
    @Bean
    JPushClient jPushClient() {
        JPushClient jPushClient = new JPushClient(secret, key, null, ClientConfig.getInstance());
        return jPushClient;
    }
}

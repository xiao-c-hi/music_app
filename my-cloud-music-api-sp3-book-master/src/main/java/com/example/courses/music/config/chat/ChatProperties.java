package com.example.courses.music.config.chat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 聊天配置
 */
@Component

//自动将配置文件chat开头的内容映射到本类
@ConfigurationProperties(prefix = "chat")
public class ChatProperties {
    private String key;

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
}

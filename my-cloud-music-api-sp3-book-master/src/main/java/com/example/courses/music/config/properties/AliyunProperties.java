package com.example.courses.music.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云配置
 */
@Component
@ConfigurationProperties(prefix = "aliyun")
public class AliyunProperties {
    private String key;

    private String secret;

    private AliyunLiveProperties live;

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

    public AliyunLiveProperties getLive() {
        return live;
    }

    public void setLive(AliyunLiveProperties live) {
        this.live = live;
    }
}

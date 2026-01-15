package com.example.courses.music.config.live;

import com.aliyun.imp20210630.Client;
import com.aliyun.teaopenapi.models.Config;
import com.example.courses.music.config.properties.AliyunProperties;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class LiveConfig {
    @Autowired
    AliyunProperties properties;

    @Bean
    public Client client() {
        // 1. 配置Config
        Config authConfig = new Config();
        authConfig.accessKeyId = properties.getKey();
        authConfig.accessKeySecret = properties.getSecret();

        // 请指定线上配置域名
        authConfig.endpoint = "imp.aliyuncs.com";

        // 2. 实例化Client
        try {
            return new Client(authConfig);
        } catch (Exception e) {
            throw new CommonException(Constant.ERROR_INIT_LIVE, Constant.ERROR_INIT_LIVE_MESSAGE);
        }
    }
}

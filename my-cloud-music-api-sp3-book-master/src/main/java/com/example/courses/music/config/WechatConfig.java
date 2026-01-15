package com.example.courses.music.config;

import me.chanjar.weixin.common.service.WxOAuth2Service;
import me.chanjar.weixin.open.api.WxOpenConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenOAuth2ServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信配置
 */
@Configuration
public class WechatConfig {
//    @Bean
//    public WxOpenService wxOpenService() {
//        WxOpenConfigStorage config = new WxOpenInMemoryConfigStorage();
//
//        config.setComponentAppId("wx672a5ce2ea3a3f4f"); // 设置微信公众号的appid
//        config.setComponentAppSecret("65f241ce6cf472c55c16107bfa22ed6d");
//
//        WxOpenService wxService = new WxOpenServiceImpl();// 实际项目中请注意要保持单例，不要在每次请求时构造实例，具体可以参考demo项目
//        wxService.setWxOpenConfigStorage(config);
////        wxService.setMultiConfigStorages();
//        return wxService;
//    }

    @Bean
    public WxOAuth2Service wxOpenOAuth2Service() {
        WxOpenConfigStorage wxOpenConfigStorage = new WxOpenInMemoryConfigStorage();
        wxOpenConfigStorage.setComponentAppId(Config.WECHAT_AK);
        wxOpenConfigStorage.setComponentAppSecret(Config.WECHAT_SK);

//        WxOpenComponentService wxOpenComponentService = new WxOpenComponentServiceImpl(wxOpenService);
        WxOpenOAuth2ServiceImpl r = new WxOpenOAuth2ServiceImpl(wxOpenConfigStorage.getComponentAppId(), wxOpenConfigStorage.getComponentAppSecret());
        r.setWxOpenConfigStorage(wxOpenConfigStorage);

        return r;
    }

}

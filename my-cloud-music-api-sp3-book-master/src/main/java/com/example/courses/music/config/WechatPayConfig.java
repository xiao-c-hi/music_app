package com.example.courses.music.config;

import com.github.wxpay.sdk.MyWechatPayConfig;
import com.github.wxpay.sdk.WXPay;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付配置
 */
@Configuration
public class WechatPayConfig {
    /**
     * 获取微信支付api对象
     *
     * @return
     * @throws Exception
     */
    @Bean
    public WXPay wxPay() throws Exception {
        MyWechatPayConfig config = new MyWechatPayConfig();
        WXPay wxPay = new WXPay(config);
        return wxPay;
    }
}

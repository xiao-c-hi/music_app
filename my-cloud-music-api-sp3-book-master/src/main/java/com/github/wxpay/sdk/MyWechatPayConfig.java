package com.github.wxpay.sdk;

import com.example.courses.music.config.Config;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义微信配置文件
 * 主要设置app id
 * 签名文件等信息
 */
public class MyWechatPayConfig extends WXPayConfig {

    private final byte[] certData;

    /**
     * 构造方法
     */
    public MyWechatPayConfig() throws IOException {
        //读取resources/data目录下面的证书文件
        ClassPathResource classPathResource = new ClassPathResource(Config.WECHAT_PAY_CERT);
        InputStream inputStream = classPathResource.getInputStream();

        //读取系统文件
//        File file = new File(Constant.WECHAT_PAY_CERT);
//        FileInputStream inputStream = new FileInputStream(file);

        //读取到byte数组
        certData = new byte[inputStream.available()];
        inputStream.read(certData);
        inputStream.close();
    }

    /**
     * appId
     * app支付在开放平台查看
     * 其他支付在微信公众号后台-开发-基本配置-开发者ID(AppID)
     * 必须在商户后台，将这个appId关联到商户
     *
     * @return
     */
    @Override
    String getAppID() {
        return "wx672a5ce2ea3a3f4f";
    }

    /**
     * 商户id
     * 在商户后台查看
     *
     * @return
     */
    @Override
    String getMchID() {
        return "1525455181";
    }

    /**
     * 在商户后台-API安全-API密钥里面申请
     *
     * @return
     */
    @Override
    String getKey() {
        return Config.WECHAT_PAY_API_AS;
    }

    /**
     * 获取证书输入流
     *
     * @return
     */
    @Override
    InputStream getCertStream() {
        ByteArrayInputStream stream = new ByteArrayInputStream(certData);
        return stream;
    }

    /**
     * 获取微信支付域名
     *
     * @return
     */
    @Override
    IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            /**
             * 用来统计sdk一些信息
             * @param domain            域名。 比如：api.mch.weixin.qq.com
             * @param elapsedTimeMillis 耗时
             * @param ex                网络请求中出现的异常。
             *                          null表示没有异常
             *                          ConnectTimeoutException，表示建立网络连接异常
             */
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            /**
             * 获取微信支付域名
             * @param config 配置
             * @return
             */
            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
    }
}

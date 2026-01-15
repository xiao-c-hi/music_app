package com.example.courses.music.config;

public class Config {
    /**
     * 微信id
     */
    public static final String WECHAT_AK = "wx672a5ce2ea3a3f4f";
    public static final String WECHAT_SK = "65f241ce6cf472c55c16107bfa22ed6d";

    /**
     * 支付宝通知url
     */
    public static final String ALIPAY_NOTIFY_URL = "%s/v1/callbacks/alipay";

    //region 微信支付
    /**
     * 在商户后台-API安全-API密钥里面申请
     */
    public static final String WECHAT_PAY_API_AS = "3d884c98671b0b32d9cc13efb577e482";

    /**
     * 微信支付证书路径
     * <p>
     * 在商户后台-API安全-API证书里面申请
     * 官方文档-什么是API证书？如何获取API证书？https://kf.qq.com/faq/161222NneAJf161222U7fARv.html
     * 在调用微信支付安全级别较高的接口（如：退款、企业红包、企业付款）时才用到
     * 目的测试普通支付（统一下单，支付回调通知）可以不用
     */
//    public static final String WECHAT_PAY_CERT = "/Users/smile/Downloads/files/wechat_pay_cert.p12";

    //相对resource目录，但这个属于高度机密
    //不建议和jar放一起
    public static final String WECHAT_PAY_CERT = "data/wechat/wechat_pay_cert.p12";
    //endregion

    /**
     * 微信支付回调
     */
    public static final String WECHAT_PAY_NOTIFY_URL = "%s/v1/callbacks/wechat-pay";

    //region elastic配置
    /**
     * 地址
     */
    public static final String ELASTIC_ENDPOINT = "localhost";

    /**
     * 端口
     */
    public static final int ELASTIC_PORT = 9200;

    /**
     * 协议
     */
    public static final String ELASTIC_SCHEME = "http";

    //endregion

    /**
     * 文件上传目录
     *
     * 可以是相对路径，也可以是绝对路径
     */
//    public static final String DIR_UPLOAD = "C:/temp/";
//    public static final String DIR_UPLOAD = "/Users/smile/Downloads/files/";
    public static final String DIR_UPLOAD = "/www/wwwroot/rs.ixuea.com/music/uploads/";
//    public static final String DIR_UPLOAD = "C:/Users/smile/Downloads/uploads/";
}

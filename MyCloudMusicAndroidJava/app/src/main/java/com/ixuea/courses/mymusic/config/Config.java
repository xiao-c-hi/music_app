package com.ixuea.courses.mymusic.config;

import com.ixuea.courses.mymusic.BuildConfig;

public class Config {
    /**
     * 默认延时时间
     */
    public static final long SPLASH_DEFAULT_DELAY_TIME = 1000;

    /**
     * 是否是调试模式
     */
    public static final boolean DEBUG = BuildConfig.DEBUG;

    /**
     * 端点
     */
    public static String ENDPOINT = BuildConfig.ENDPOINT;

    /**
     * 资源端点
     */
    public static String RESOURCE_ENDPOINT = BuildConfig.RESOURCE_ENDPOINT;

    /**
     * 网络缓存目录大小
     * 100M
     */
    public static final long NETWORK_CACHE_SIZE = 1024 * 1024 * 100;

    //region 阿里云
    /**
     * 阿里云OSS AK
     */
    public static final String ALIYUN_AK = "YOUR_ALIYUN_AK"; // 占位符，替换真实AK

    /**
     * 阿里云OSS SK
     */
    public static final String ALIYUN_SK = "YOUR_ALIYUN_SK"; // 占位符，替换真实SK

    /**
     * 阿里云OSS Bucket
     */
    public static final String ALIYUN_OSS_BUCKET_NAME = "YOUR_ALIYUN_BUCKET_NAME";

    /**
     * 阿里云OSS Bucket 地址
     */
    public static final String BUCKET_ENDPOINT = "oss-cn-beijing.aliyuncs.com";

    /**
     * 聊天key
     */
    public static final String IM_KEY = "YOUR_IM_KEY"; // 占位符

    //endregion

    /**
     * 二维码地址
     */
    public static final String QRCODE_URL = "http://www.ixuea.com";

    /**
     * 用户二维码
     */
    public static final String USER_QRCODE_URL = String.format("%s?u=%%s", QRCODE_URL);

    //region 百度语音
    public static final String BAIDU_VOICE_ID = "YOUR_BAIDU_VOICE_ID"; // 占位符
    public static final String BAIDU_VOICE_KEY = "YOUR_BAIDU_VOICE_KEY"; // 占位符
    public static final String BAIDU_VOICE_SECRET = "YOUR_BAIDU_VOICE_SECRET"; // 占位符
    //endregion

    /**
     * 小米推送信息
     */
    public static final String MI_ID = "YOUR_MI_ID"; // 占位符
    public static final String MI_KEY = "YOUR_MI_KEY"; // 占位符

    /**
     * 微信id
     */
    public static final String WECHAT_AK = "YOUR_WECHAT_AK"; // 占位符

    /**
     * 腾讯Bugly
     */
    public static final String BUGLY_APP_KEY = "YOUR_BUGLY_APP_KEY"; // 占位符
}
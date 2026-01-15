package com.example.courses.music.model;


/**
 * 阿里云oss上传文件配置模型
 */
public class AliyunOSSUploadConfig {
    /**
     * ak
     */
    private String key;

    /**
     * sk
     */
    private String secret;

    /**
     * 安全字符串
     */
    private String security;

    /**
     * 过期时间
     */
    private String expire;

    /**
     * 生成token的其他标识
     * 客户端基本上用不到
     */
    private String session;

    /**
     * 构造方法
     *
     * @param key
     * @param secret
     * @param security
     * @param expire
     * @param session
     */
    public AliyunOSSUploadConfig(String key, String secret, String security, String expire, String session) {
        this.key = key;
        this.secret = secret;
        this.security = security;
        this.expire = expire;
        this.session = session;
    }

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

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}

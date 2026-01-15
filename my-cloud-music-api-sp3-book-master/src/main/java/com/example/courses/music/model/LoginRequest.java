package com.example.courses.music.model;

/**
 * 手机号验证码登录是请求参数
 * <p>
 * 也可以复用User模型
 */
public class LoginRequest extends Base {
    private String email;

    private String phone;

    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 登录平台
     * 取值constant中的平台
     */
    private byte platform;

    /**
     * 设备名称
     * 例如：小米11
     */
    private String device;

    /**
     * 推送id
     */
    private String push;

    /**
     */
    private String qqId;

    /**
     * wechat登录后的code
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317853&lang=zh_CN
     */
    private String wechatId;

    /**
     * 微信语言
     */
    private String lang;

    private String nickname;

    /**
     * 头像
     */
    private String icon;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte getPlatform() {
        return platform;
    }

    public void setPlatform(byte platform) {
        this.platform = platform;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getPush() {
        return push;
    }

    public void setPush(String push) {
        this.push = push;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

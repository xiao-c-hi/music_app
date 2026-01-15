package com.example.courses.music.model;

/**
 * 验证码请求参数
 * <p>
 * 也可以复用User模型
 */
public class CodeRequest extends Base {
    private String phone;
    private String email;

    /**
     * 如果发送频繁了，也可以邀请发送验证码时，传递图形验证码
     */
    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

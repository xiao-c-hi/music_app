package com.example.courses.music.model.request;

import com.example.courses.music.model.Base;

/**
 * 获取直播认证token请求参数对象
 */
public class LiveTokenRequest extends Base {
    private String secret;
    private String userId;
    private String deviceId;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}

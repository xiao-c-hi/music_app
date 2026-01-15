package com.example.courses.music.model.response;

import com.example.courses.music.model.Base;

/**
 * 直播token响应
 */
public class LiveTokenResponse extends Base {
    // 用于长链接建连的token
    public String token;

    // 更新Token，若AccessToken过期，则可以使用RefreshToken再次获取新Token
    public String refreshToken;

    // 登录token过期时间(毫秒)
    public Long accessTokenExpiredTime;

    public LiveTokenResponse(String token, String refreshToken, Long accessTokenExpiredTime) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.accessTokenExpiredTime = accessTokenExpiredTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getAccessTokenExpiredTime() {
        return accessTokenExpiredTime;
    }

    public void setAccessTokenExpiredTime(Long accessTokenExpiredTime) {
        this.accessTokenExpiredTime = accessTokenExpiredTime;
    }
}

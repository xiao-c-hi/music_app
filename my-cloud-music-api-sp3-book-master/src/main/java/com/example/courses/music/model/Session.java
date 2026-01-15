package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.courses.music.util.MD5Util;

/**
 * 登录后，返回用户token模型
 */
public class Session extends Common {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 登录后token
     */
    @TableField(exist = false)
    private String session;

    /**
     * 登录平台
     * 取值constant中的平台
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte platform;

    /**
     * 设备名称
     * 例如：小米11
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String device;

    /**
     * 推送id
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String push;

    /**
     * 登录ip
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String ip;

    /**
     * 聊天token
     */
    @TableField(exist = false)
    private String chatToken;

    /**
     * 直播token
     */
    @TableField(exist = false)
    private String liveToken;

    /**
     * 角色
     */
    @TableField(exist = false)
    private String roles;

    public Session() {
    }

    public Session(String userId, String session, byte platform, String device, String push, String chatToken, String ip, String roles) {
        this.userId = userId;
        this.session = session;
        this.platform = platform;
        this.device = device;
        this.push = push;
        this.chatToken = chatToken;
        this.ip = ip;
        this.roles = roles;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getChatToken() {
        return chatToken;
    }

    public void setChatToken(String chatToken) {
        this.chatToken = chatToken;
    }

    public String getLiveToken() {
        return liveToken;
    }

    public void setLiveToken(String liveToken) {
        this.liveToken = liveToken;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * 计算id
     *
     * @param userId
     * @param platform
     * @return
     */
    public static String generateId(String userId, byte platform) {
        String idString = String.format("%s%d", userId, platform);
        return String.valueOf(MD5Util.encrypt(idString).hashCode());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte getPlatform() {
        return platform;
    }

    public void setPlatform(byte platform) {
        this.platform = platform;
    }

    public String getPush() {
        return push;
    }

    public void setPush(String push) {
        this.push = push;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}

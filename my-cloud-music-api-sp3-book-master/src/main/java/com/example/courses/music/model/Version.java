package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 版本模型
 */
public class Version extends Base {
    /**
     * 版本号：例如：3.0.1
     * 如果有新版本，该字段有值
     */
    private String name;

    /**
     * int类型版本
     */
    private int code;

    /**
     * 更新描述
     */
    private String detail;

    /**
     * 相对下载地址
     */
    private String uri;

    /**
     * 平台
     * 取值constant中的平台
     */
    private byte platform;

    /**
     * 是否强制更新，0：否；1：是
     */
    @TableField("`force`")
    private boolean force;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public byte getPlatform() {
        return platform;
    }

    public void setPlatform(byte platform) {
        this.platform = platform;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }
}
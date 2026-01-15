package com.example.courses.music.model;

/**
 * 资源模型
 */
public class Resource extends Common {
    /**
     * 地址
     */
    private String uri;

    /**
     * 动态id
     */
    private String feedId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 类型，0：图片；10：视频
     */
    private byte style;

    public Resource() {
    }

    public Resource(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte getStyle() {
        return style;
    }

    public void setStyle(byte style) {
        this.style = style;
    }
}

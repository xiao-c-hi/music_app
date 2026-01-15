package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 视频模型
 */
public class Video extends Common {
    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    @Length(min = 1, max = 30, message = "标题长度为1~30位")
    private String title;

    /**
     * 点击数
     */
    private int clicksCount;

    /**
     * 点赞数
     */
    private int likesCount;

    /**
     * 评论数
     */
    private int commentsCount;

    /**
     * 视频地址
     */
    @NotBlank(message = "视频地址不能为空")
    private String uri;

    /**
     * 封面
     */
    @NotBlank(message = "封面地址不能为空")
    private String icon;

    /**
     * 视频时长
     * <p>
     * 单位秒
     */
    private int duration;

    /**
     * 视频宽
     */
    private int width;

    /**
     * 视频高
     */
    private int height;

    /**
     * 创建视频用户id
     */
    private String userId;

    /**
     * 创建视频用户
     */
    @TableField(exist = false)
    private User user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getClicksCount() {
        return clicksCount;
    }

    public void setClicksCount(int clicksCount) {
        this.clicksCount = clicksCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

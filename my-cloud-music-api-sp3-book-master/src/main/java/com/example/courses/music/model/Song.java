package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 音乐模型
 */
public class Song extends Common {
    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    @Length(min = 1, max = 30, message = "标题长度必须为1~30位")
    private String title;

    /**
     * 封面
     */
    @NotBlank(message = "封面不能为空")
    private String icon;

    /**
     * 当前音乐的url
     * <p>
     * 相对地址
     * 真实项目中，可能会区分不同的码率
     * 但根据不同的实现，会不同的方式
     */
    @NotBlank(message = "音乐地区不能为空")
    private String uri;

    /**
     * 点击数
     */
    private int clicksCount;

    /**
     * 评论数
     */
    private int commentsCount;

    /**
     * 歌词类型
     * <p>
     * 0:lrc，10:ksc
     */
    private Integer style;

    /**
     * 歌词
     */
    private String lyric;

    /**
     * 谁创建这首音乐
     */
    @TableField(exist = false)
    private User user;

    /**
     * 创建音乐用户id
     * <p>
     * 保存数据用
     */
    private String userId;

    /**
     * 歌手
     */
    @TableField(exist = false)
    private User singer;

    /**
     * 歌手Id
     * <p>
     * 这里引用的是user
     * 真实项目中，可能会将歌手放到单独的表中
     * 因为不是每个歌手都会来平台创建用户
     * 保存数据用
     */
    @NotBlank(message = "歌手不能为空")
    private String singerId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getClicksCount() {
        return clicksCount;
    }

    public void setClicksCount(int clicksCount) {
        this.clicksCount = clicksCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getSinger() {
        return singer;
    }

    public void setSinger(User singer) {
        this.singer = singer;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }
}
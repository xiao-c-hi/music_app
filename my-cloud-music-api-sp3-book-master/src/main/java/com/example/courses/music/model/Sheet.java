package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 歌单
 */
public class Sheet extends Common {
    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    @Length(min = 1, max = 30, message = "标题长度必须为1~30位")
    private String title;

    /**
     * 封面
     */
    private String icon;

    /**
     * 描述
     */
    private String detail;

    /**
     * 创建歌单的用户
     * exist：false，表示字段不存在数据库中，就不会生成到sql中
     */
    @TableField(exist = false)
    private User user;

    /**
     * 创建歌单的用户id
     */
    private String userId;

    /**
     * 点击数
     */
    private int clicksCount;

    /**
     * 收藏数
     */
    private int collectsCount;

    /**
     * 评论数
     */
    private Integer commentsCount;

    /**
     * 音乐数
     */
    private Integer songsCount;

    /**
     * 标签
     */
    @TableField(exist = false)
    private List<Tag> tags;

    /**
     * 音乐列表
     */
    @TableField(exist = false)
    private List<Song> songs;

    /**
     * 有值就表示收藏了该歌单
     */
    @TableField(exist = false)
    private String collectId;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public int getClicksCount() {
        return clicksCount;
    }

    public void setClicksCount(int clicksCount) {
        this.clicksCount = clicksCount;
    }

    public int getCollectsCount() {
        return collectsCount;
    }

    public void setCollectsCount(int collectsCount) {
        this.collectsCount = collectsCount;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public Integer getSongsCount() {
        return songsCount;
    }

    public void setSongsCount(Integer songsCount) {
        this.songsCount = songsCount;
    }
}

package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 评论模型
 */
public class Comment extends Common {
    /**
     * 内容
     */
    @NotBlank(message = "评论内容不能为空")
    @Length(min = 1, max = 140)
    private String content;

    /**
     * 被回复评论id
     */
    private String parentId;

    /**
     * 被回复评论
     */
    @TableField(exist = false)
    private Comment parent;

    /**
     * 视频id
     */
    private String videoId;

    /**
     * 歌单id
     */
    private String sheetId;

    /**
     * 动态id
     */
    private String feedId;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 歌单对象
     */
    @TableField(exist = false)
    private Sheet sheet;

    /**
     * 音乐id
     */
    private String songId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户
     */
    @TableField(exist = false)
    private User user;

    /**
     * 点赞数
     */
    private int likesCount;

    /**
     * 点赞关系id
     * <p>
     * 有值表示点赞了
     */
    @TableField(exist = false)
    private String likeId;

    /**
     * 图片列表
     * 逗号分割
     */
    private String media;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
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

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
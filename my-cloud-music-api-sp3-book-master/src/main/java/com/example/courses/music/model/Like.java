package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 点赞模型
 * 评论点赞，动态点赞都是
 */
@TableName(value = "`like`")
public class Like extends Common {
    /**
     * 评论id
     */
    private String commentId;

    /**
     * 动态id
     */
    private String feedId;

    /**
     * 用户id
     */
    private String userId;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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
}

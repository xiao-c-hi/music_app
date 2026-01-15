package com.example.courses.music.model;


/**
 * 好友模型
 */
public class Friend extends Common {
    /**
     * 当前用户id
     */
    private String followerId;

    /**
     * 对方id
     */
    private String followedId;

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getFollowedId() {
        return followedId;
    }

    public void setFollowedId(String followedId) {
        this.followedId = followedId;
    }
}

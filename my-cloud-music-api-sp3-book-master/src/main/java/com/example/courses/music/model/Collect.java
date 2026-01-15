package com.example.courses.music.model;

/**
 * 歌单收藏模型
 */
public class Collect extends Common {
    /**
     * 歌单id
     */
    private String sheetId;

    /**
     * 用户id
     */
    private String userId;

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

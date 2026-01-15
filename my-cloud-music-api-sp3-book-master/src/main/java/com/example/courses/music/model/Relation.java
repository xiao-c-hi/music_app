package com.example.courses.music.model;


/**
 * 歌单和音乐关系
 */
public class Relation extends Common {
    /**
     * 音乐id
     */
    private String songId;

    /**
     * 歌单id
     */
    private String sheetId;

    /**
     * 用户id
     */
    private String userId;

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

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

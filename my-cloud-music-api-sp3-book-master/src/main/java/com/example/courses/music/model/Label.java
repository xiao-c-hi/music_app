package com.example.courses.music.model;


import com.example.courses.music.util.MD5Util;

/**
 * 标签和具体的模型关系模型
 * <p>
 * 例如：标签和歌单的关系
 */
public class Label extends Common {
    /**
     * 标签id
     */
    private String tagId;

    /**
     * 歌单id
     */
    private String sheetId;

    /**
     * 用户id
     */
    private String userId;

    public Label() {
    }

    public Label(String tagId, String sheetId, String userId) {
        this.tagId = tagId;
        this.sheetId = sheetId;
        this.userId = userId;

        String id = String.format("%s%s%s", tagId, sheetId, userId);
        //做一次md5是为了把很长的字符串变短
        setId(String.valueOf(MD5Util.encrypt(id).hashCode()));
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
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

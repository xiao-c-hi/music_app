package com.example.courses.music.model;

/**
 * 获取直播认证token请求参数对象
 */
public class Room extends BaseId {
    /**
     * 直播间标题
     * 后面还可以修改
     */
    private String title;

    public Room() {
    }

    public Room(String id) {
        super(id);
    }

    public Room(String id, String title) {
        super(id);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

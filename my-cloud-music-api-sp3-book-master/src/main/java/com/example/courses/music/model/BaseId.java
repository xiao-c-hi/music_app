package com.example.courses.music.model;

/**
 * 通用id模型
 */
public class BaseId extends Base {
    /**
     * 数据id
     * 数据库是bitint，代码中之所以写字符串，是为了处理更通用
     */
    private String id;

    public BaseId() {
    }

    public BaseId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

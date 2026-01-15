package com.example.courses.music.model;


import java.util.List;

/**
 * 搜索建议返回模型
 */
public class Suggest extends Base {
    /**
     * 歌单搜索建议
     * 放到两个字段
     * 好处是客户端能判断出类型
     */
    private List<SuggestItem> sheets;

    /**
     * 用户搜索建议
     */
    private List<SuggestItem> users;

    public List<SuggestItem> getSheets() {
        return sheets;
    }

    public void setSheets(List<SuggestItem> sheets) {
        this.sheets = sheets;
    }

    public List<SuggestItem> getUsers() {
        return users;
    }

    public void setUsers(List<SuggestItem> users) {
        this.users = users;
    }
}

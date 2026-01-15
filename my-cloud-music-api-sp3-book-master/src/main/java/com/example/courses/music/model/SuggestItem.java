package com.example.courses.music.model;

/**
 * 搜索建议项
 */
public class SuggestItem extends BaseId {
    /**
     * 标题
     */
    private String title;

    public SuggestItem(String id, String title) {
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

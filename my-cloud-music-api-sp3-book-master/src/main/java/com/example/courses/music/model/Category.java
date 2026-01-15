package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.List;

public class Category extends BaseId {
    private String name;
    private String icon;
    private String parentId;
    private int sort;

    @TableField(exist = false)
    private List<Category> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

package com.example.courses.music.model;


import com.baomidou.mybatisplus.annotation.TableField;

import java.util.List;

/**
 * 歌单标签
 */
public class Tag extends Common {
    /**
     * 标题
     */
    private String title;

    /**
     * 父级标签id
     */
    private String parentId;

    /**
     * 下级标签
     */
    @TableField(exist = false)
    private List<Tag> data;

    /**
     * 标签id
     * <p>
     * 用来接收客户端传递的数据
     */
    @TableField(exist = false)
    private String tagId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Tag> getData() {
        return data;
    }

    public void setData(List<Tag> data) {
        this.data = data;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}

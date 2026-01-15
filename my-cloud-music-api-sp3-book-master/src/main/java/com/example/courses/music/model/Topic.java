package com.example.courses.music.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 话题模型
 */
public class Topic extends Common {
    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    @Length(min = 1, max = 30, message = "标题长度必须为1~30位")
    private String title;

    /**
     * 封面
     */
    @NotBlank(message = "封面不能为空")
    private String icon;

    /**
     * 描述
     */
    private String detail;

    /**
     * 参与人数
     */
    private int joinsCount;

    /**
     * 用户id
     */
    private String userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getJoinsCount() {
        return joinsCount;
    }

    public void setJoinsCount(int joinsCount) {
        this.joinsCount = joinsCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

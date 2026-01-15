package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 广告模型
 */
public class Ad extends Common {
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
     * 跳转地址
     */
    private String uri;

    /**
     * 类型，0：图片；10：视频；20：应用
     */
    private byte style;

    /**
     * 显示位置，0：首页banner；10：启动界面
     */
    private int position;

    /**
     * 排序
     * <p>
     * 值越大，越靠前
     */
    private int sort;

    /**
     * 用户id
     * <p>
     * 保存数据/更新数据时用
     */
    private String userId;

    /**
     * 谁创建的
     */
    @TableField(exist = false)
    private User user;

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte getStyle() {
        return style;
    }

    public void setStyle(byte style) {
        this.style = style;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
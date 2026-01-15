package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.List;

/**
 * 规则（权限，菜单）
 */
public class Rule extends Common {
    private String title;

    /**
     * 唯一标识，最后一级是权限例如：user:add
     * 其他级表示菜单，例如：systems表示系统，users表示用户，前端可以根据这个字段显示对应的界面
     */
    private String value;
    private String name;
    private String icon;
    private String component;
    private String path;
    private String uri;
    private String method;

    /**
     * 菜单meta信息，前端框架使用
     */
    private transient RuleMeta meta ;

    @TableField("`condition`")
    private String condition;
    private String parentId;
    private Integer status;

    /**
     * 菜单排序，前端只对第一级有效
     *
     * 当我们会在后台返回数据就排序好
     */
    private Integer orderNo;

    /**
     * 当前路由不再菜单显示
     *
     * 也需要返回前端，因为对于目前用的前端后台管理系统来说
     * 例如：用户详情界面，不需要再左侧菜单显示出来，但也需要添加前端路由里面
     * 这样才能实现在用户列表，点击详情按钮，显示用户界面
     */
    private boolean hideMenu;

    /**
     * 当前激活的菜单。用于配置详情页时左侧激活的菜单路径
     *
     * 主要是前端使用管理系统使用
     */
    private String currentActiveMenu;

    private transient List<Rule> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return orderNo;
    }

    public void setSort(Integer sort) {
        this.orderNo = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Rule> getChildren() {
        return children;
    }

    public void setChildren(List<Rule> children) {
        this.children = children;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public RuleMeta getMeta() {
        if (meta == null) {
            meta=new RuleMeta(this.title,this.icon,this.hideMenu);
        }
        return meta;
    }

    public void setMeta(RuleMeta meta) {
        this.meta = meta;
    }

    public boolean isHideMenu() {
        return hideMenu;
    }

    public void setHideMenu(boolean hideMenu) {
        this.hideMenu = hideMenu;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getCurrentActiveMenu() {
        return currentActiveMenu;
    }

    public void setCurrentActiveMenu(String currentActiveMenu) {
        this.currentActiveMenu = currentActiveMenu;
    }
}

package com.example.courses.music.model;

/**
 * 菜单meta信息，前端框架使用
 */
public class RuleMeta extends Base{
    private String title;
    private String icon;
    private boolean hideMenu;

    public RuleMeta(String title, String icon,boolean hideMenu) {
        this.title = title;
        this.icon = icon;
        this.hideMenu = hideMenu;
    }

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

    public boolean isHideMenu() {
        return hideMenu;
    }

    public void setHideMenu(boolean hideMenu) {
        this.hideMenu = hideMenu;
    }
}

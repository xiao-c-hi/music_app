package com.example.courses.music.model;

public class Button {
    private String icon;
    private String uri;

    public Button(String icon) {
        this.icon = icon;
    }

    public Button(String icon, String uri) {
        this.icon = icon;
        this.uri = uri;
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
}

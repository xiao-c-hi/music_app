package com.example.courses.music.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 第三方账号
 */
public class Account extends Base {
    /**
     * 平台
     */
    @NotNull(message = "平台不能为空")
    private Integer platform;

    /**
     * 第三方登录后的id
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}

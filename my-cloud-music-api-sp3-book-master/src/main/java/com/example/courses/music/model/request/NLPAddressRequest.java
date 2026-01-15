package com.example.courses.music.model.request;

import javax.validation.constraints.NotBlank;

/**
 * NLP地址请求参数
 */
public class NLPAddressRequest {
    @NotBlank(message = "文本不能为空")
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

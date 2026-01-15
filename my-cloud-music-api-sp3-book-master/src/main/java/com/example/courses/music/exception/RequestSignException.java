package com.example.courses.music.exception;

import com.example.courses.music.util.Constant;

/**
 * 请求体签名错误异常
 */
public class RequestSignException extends CommonException {
    /**
     * 构造方法
     */
    public RequestSignException() {
        super(Constant.ERROR_PARAMS_SIGN, Constant.ERROR_PARAMS_SIGN_MESSAGE);
    }
}

package com.example.courses.music.exception;

import com.example.courses.music.util.Constant;

/**
 * 请求体解密错误异常
 */
public class RequestDecryptException extends CommonException {
    /**
     * 构造方法
     */
    public RequestDecryptException() {
        super(Constant.ERROR_PARAMS_DECRYPT, Constant.ERROR_PARAMS_DECRYPT_MESSAGE);
    }
}

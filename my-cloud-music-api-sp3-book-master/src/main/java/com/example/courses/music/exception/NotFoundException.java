package com.example.courses.music.exception;

import com.example.courses.music.util.Constant;

/**
 * 资源不存在异常
 */
public class NotFoundException extends CommonException {
    /**
     * 构造方法
     */
    public NotFoundException() {
        super(Constant.ERROR_NOT_FOUND, Constant.ERROR_NOT_FOUND_MESSAGE);
    }
}

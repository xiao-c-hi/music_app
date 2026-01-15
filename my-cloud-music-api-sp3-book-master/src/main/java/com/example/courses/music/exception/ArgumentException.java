package com.example.courses.music.exception;

import com.example.courses.music.util.Constant;

/**
 * 参数异常
 */
public class ArgumentException extends CommonException {
    /**
     * 构造方法
     */
    public ArgumentException() {
        super(Constant.ERROR_ARGUMENT, Constant.ERROR_ARGUMENT_MESSAGE);
    }
}

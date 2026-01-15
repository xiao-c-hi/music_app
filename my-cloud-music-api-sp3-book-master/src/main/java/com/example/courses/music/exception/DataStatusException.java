package com.example.courses.music.exception;

import com.example.courses.music.util.Constant;

/**
 * 数据状态错误
 * 例如：订单已经支付了，不能再次支付
 */
public class DataStatusException extends CommonException {
    /**
     * 构造方法
     */
    public DataStatusException() {
        super(Constant.ERROR_DATA_STATUS, Constant.ERROR_DATA_STATUS_MESSAGE);
    }
}
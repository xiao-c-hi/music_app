package com.example.courses.music.exception;

import com.example.courses.music.util.Constant;

/**
 * 保存数据异常
 */
public class SaveDataException extends CommonException {
    /**
     * 构造方法
     */
    public SaveDataException() {
        super(Constant.ERROR_SAVE_DATA, Constant.ERROR_SAVE_DATA_MESSAGE);
    }
}

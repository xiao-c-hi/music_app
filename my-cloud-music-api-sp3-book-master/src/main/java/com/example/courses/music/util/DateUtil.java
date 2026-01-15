package com.example.courses.music.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具类
 */
public class DateUtil {
    /**
     * 转为yyyy-MM-dd HH:mm
     *
     * @param data
     * @return
     */
    public static String yyyyMMddHHmmss(Date data) {
        if (data == null) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(data);
    }
}

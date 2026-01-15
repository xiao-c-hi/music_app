package com.example.courses.music.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtil {

    /**
     * 时间+day 是否大于当前时间
     *
     * @param old
     * @param days
     * @return
     */
    public static boolean plusDaysAfterNow(Timestamp old, int days) {
        //将Timestamp转为LocalDateTime
        LocalDateTime localDateTime = getLocalDateTime(old);

        //将原来的时间+指定的天
        localDateTime = localDateTime.plusDays(days);

        return isAfterNow(localDateTime);
    }

    /**
     * 时间+minutes分钟 是否大于当前时间
     *
     * @param old
     * @param minutes
     * @return
     */
    public static boolean plusMinutesAfterNow(Date old, int minutes) {
        //转为LocalDateTime
        //这里使用了Java8的日期时间api
        long timestamp = old.getTime();

        Instant instant = Instant.ofEpochMilli(timestamp);

        LocalDateTime oldLocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        //+指定分钟
        oldLocalDateTime = oldLocalDateTime.plusMinutes(minutes);

        //是否大于当前时间
        return isAfterNow(oldLocalDateTime);
    }

    /**
     * 将Timestamp转为LocalDateTime
     *
     * @param old
     * @return
     */
    private static LocalDateTime getLocalDateTime(Timestamp old) {
        //这里使用了Java8的日期时间api
        long timestamp = old.getTime();

        Instant instant = Instant.ofEpochMilli(timestamp);

        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * 是否大于当前时间
     *
     * @param oldLocalDateTime
     * @return
     */
    private static boolean isAfterNow(LocalDateTime oldLocalDateTime) {
        //获取当前时间
        LocalDateTime nowLocalDateTime = LocalDateTime.now();

        //是否在参数时间后面
        //例如：14点，在13后面
        return oldLocalDateTime.isAfter(nowLocalDateTime);
    }

}
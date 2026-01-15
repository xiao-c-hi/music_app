package com.example.courses.music.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * id工具类
 */
public class IDUtil {
    /**
     * 获取uuid，去除了中划线
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成订单号
     * <p>
     * 订单号 18位
     * 时间戳14位，精确到秒
     * 随机数4位
     *
     * @return
     */
    public static String getOrderNumber() {
        //时间戳
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        //当前时间
        LocalDateTime nowLocalDateTime = LocalDateTime.now();

        //格式化
        String timeString = nowLocalDateTime.format(dateTimeFormatter);

        String randString = String.valueOf(RandomUtil.int6());

        //格式化
        return String.format("%s%s", timeString, randString);
    }
}
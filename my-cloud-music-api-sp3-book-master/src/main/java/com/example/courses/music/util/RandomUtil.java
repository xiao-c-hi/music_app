package com.example.courses.music.util;

/**
 * 随机工具类
 */
public class RandomUtil {
    /**
     * 随机生成6位整数
     * <p>
     * 生成10 0000~100 0000之间的随机数，不包括100 0000
     *
     * @return
     */
    public static int int6() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }
}

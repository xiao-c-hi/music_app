package com.example.courses.music.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCrypt工具类
 */
public class BCryptUtil {

    /**
     * 加盐
     *
     * @param data
     * @return
     */
    public static String format(String data) {
        return String.format(Constant.FORMAT_SALT, data);
    }

    /**
     * 加密
     *
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        //加盐
        data = format(data);

        //加密
        BCryptPasswordEncoder encoder = getEncoder();

        return encoder.encode(data);
    }

    /**
     * 是否匹配
     *
     * @param raw    未加密的数据
     * @param encode 加密后的数据
     * @return
     */
    public static boolean matchEncode(String raw, String encode) {
        //加盐
        raw = format(raw);

        //判断是否匹配
        return getEncoder().matches(raw, encode);
    }

    /**
     * 获取密码加密器
     *
     * @return
     */
    private static BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
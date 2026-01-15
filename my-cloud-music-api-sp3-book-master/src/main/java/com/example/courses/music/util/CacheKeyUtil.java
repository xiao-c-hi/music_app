package com.example.courses.music.util;

/**
 * 缓存Key工具类
 */
public class CacheKeyUtil {
    /**
     * 证码缓存key
     */
    public static final String KEY_CODE_DATA = "codes:%s:data";
    public static final String KEY_CODE_LIMIT = "codes:%s:limit";
    public static final String KEY_CODE_COUNT = "codes:%s:count";

    /**
     * 验证码key
     *
     * @param data 目标手机号，邮箱等；如果是图形验证码，就是验证码
     * @return
     */
    public static String codeDataKey(String data) {
        return String.format(KEY_CODE_DATA, data);
    }

    /**
     * 验证码频率key
     *
     * @param data
     * @return
     */
    public static String codeLimitKey(String data) {
        return String.format(KEY_CODE_LIMIT, data);
    }

    /**
     * 验证码数量
     *
     * @param data
     * @return
     */
    public static String codeCountKey(String data) {
        return String.format(KEY_CODE_COUNT, data);
    }
}

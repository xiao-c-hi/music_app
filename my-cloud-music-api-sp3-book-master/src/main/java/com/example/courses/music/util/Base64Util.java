package com.example.courses.music.util;

import org.apache.commons.codec.binary.Base64;

/**
 * Java Base64工具类
 * 使用apache commons codec实现
 */
public class Base64Util {
    /**
     * 把byte数组编码
     *
     * @param data
     * @return 返回编码后的字符串
     */
    public static String encodeByte2String(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    /**
     * 把字符串解码
     *
     * @param data
     * @return 返回解码后的byte数组
     */
    public static byte[] decodeString2Byte(String data) {
        return Base64.decodeBase64(data);
    }
}

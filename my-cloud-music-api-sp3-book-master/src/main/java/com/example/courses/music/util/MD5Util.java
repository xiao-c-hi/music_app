package com.example.courses.music.util;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * md5工具类
 */
public class MD5Util {
    /**
     * md5签名
     *
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        //加盐
        data = SaltUtil.wrap(data);

        //org.springframework.util
        try {
            return DigestUtils.md5DigestAsHex(data.getBytes(Constant.CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

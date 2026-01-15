package com.example.courses.music.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * sha工具类
 */
public class SHAUtil {
    /**
     * sha1 签名
     *
     * @param data
     * @return
     */
    public static String sha256(String data) {
        //加盐
        data = SaltUtil.wrap(data);

        //签名
        //org.apache.commons.codec.digest
        return DigestUtils.sha256Hex(data);
    }
}
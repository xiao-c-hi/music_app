package com.example.courses.music.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * AES工具类测试
 */
public class AESUtilTests {
    /**
     * 测试aes128加密
     */
    @Test
    public void testEncrypt() {
        //相等测试
        String data = AESUtil.encrypt("ixueaedu");
        assertEquals("r4+kYpb2MAQ2JAcRuAmkruZutzNhrnF0sDBn3j1D9FxnDo7m9rF2fykSw7mJq6mi",data);

        //不相等
        assertNotEquals("ixueaedu",data);
    }

    /**
     * 测试aes128解密
     */
    @Test
    public void testDecrypt() {
        //相等测试
        String data = AESUtil.decrypt("r4+kYpb2MAQ2JAcRuAmkruZutzNhrnF0sDBn3j1D9FxnDo7m9rF2fykSw7mJq6mi");
        assertEquals("ixueaedu",data);

        //不相等测试
        assertNotEquals("ixuea",data);
    }
}

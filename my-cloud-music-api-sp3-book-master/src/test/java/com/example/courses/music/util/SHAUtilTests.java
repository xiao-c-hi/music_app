package com.example.courses.music.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * 哈希工具类测试
 */
public class SHAUtilTests {
    @Test
    public void testSHA256() {
        //相等测试
        String data = SHAUtil.sha256("ixueaedu");
        assertEquals("34f5c359f8f54d9545d703ffd277e971a1c102b0855f6d6ed759c228545d6e02",data);

        //不相等
        assertNotEquals("ixueaedu",data);
    }
}

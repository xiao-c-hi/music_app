package com.example.courses.music.service;

import com.example.courses.music.BaseTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 直播服务测试
 */
public class LiveServiceTests extends BaseTests {
    @Autowired
    private LiveService service;

    /**
     * 测试 删除直播间
     */
    @Test
    public void testDestroyRoom() {
        service.destroyRoom("79760dab-acbc-430d-84f8-6d5423cbee5b");
    }
}

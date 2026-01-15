package com.example.courses.music.service;

import com.example.courses.music.BaseTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 订单服务测试
 */
public class OrderServiceTests extends BaseTests {
    @Autowired
    private OrderService service;

    /**
     * 测试关闭超时订单
     */
    @Test
    public void testCloseTimeoutOrder() {
        service.closeTimeoutOrder();
    }
}
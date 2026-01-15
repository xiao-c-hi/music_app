package com.example.courses.music.service;

import com.example.courses.music.BaseTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 邮件服务测试
 */
public class MailServiceTests extends BaseTests {
    /**
     * 邮件服务
     */
    @Autowired
    private MailService mailService;

    /**
     * 测试发送简单邮件
     */
    @Test
    public void testSimpleMail() {
        mailService.sendSimpleMail("ixueadev@163.com");
    }
}
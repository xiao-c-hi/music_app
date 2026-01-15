package com.example.courses.music.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.example.courses.music.BaseTests;
import com.example.courses.music.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * redis服务测试
 */
public class RedisStringServiceTests extends BaseTests {
    /**
     * 服务
     */
    @Autowired
    private RedisStringService service;

    /**
     * 测试字符串key
     */
    private String cacheString = "cache_string";

    /**
     * 测试 查找字符串
     */
    @Test
    public void testFindString() {
        String data = service.findString(cacheString);
        System.out.println(data);
    }

    /**
     * 测试 更新字符串
     */
    @Test
    public void testUpdateString() {
        String value = "new string";

        service.update(cacheString, value);

        String data = service.findString(cacheString);
//        System.out.println(data);

        //确认设置的值，和获取处理的值一样
        Assertions.assertEquals(data, value);
    }

    /**
     * 测试 更新字符串，同时设置超时时间
     */
    @Test
    public void updateStringTimeout() {
        service.update(cacheString, "new string, auto timeout 30s.", 30, TimeUnit.SECONDS);
    }

    /**
     * 测试 删除字符串
     */
    @Test
    public void testDelete() {
        service.delete(cacheString);
    }

    /**
     * 测试 对象更新，查找，删除
     */
    @Test
    public void testObject() {
        //创建测试对象
        User data = new User();
        data.setId("123345234");
        data.setEmail("ixueaedu@163.com");
        data.setPhone("13141111222");
        data.setCity("这是通过redisService创建的对象");

        //更新对象
        service.updateObject("cacheUser", data);

        //查找
        User newData = service.findObject("cacheUser", new TypeReference<User>() {});
        System.out.println(newData);

        //删除
        service.deleteObject("cacheUser");
    }

    /**
     * 测试 更新对象，同时设置超时时间
     */
    @Test
    public void updateObjectTimeout() {
        //创建测试对象
        User data = new User();
        data.setId("123345234");
        data.setEmail("ixueaedu@163.com");
        data.setPhone("13141111222");
        data.setCity("这是通过redisService创建的对象");

        service.updateObject(cacheString, data, 30, TimeUnit.SECONDS);
    }
}

package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.courses.music.BaseTests;
import com.example.courses.music.model.User;
import com.example.courses.music.model.response.PageResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 用户Elastic服务 测试
 */
public class ElasticUserServiceTests extends BaseTests {
    private static Logger log = LoggerFactory.getLogger(ElasticUserServiceTests.class);

    @Autowired
    private ElasticUserService service;

    @Autowired
    UserService userService;

    /**
     * 测试 搜索
     */
    @Test
    public void testFindAll() {
        PageResponse result = service.findAll("爱学啊", 1, 2);
        log.info("testFindAll {}", result);
    }

    /**
     * 测试 更新
     */
    @Test
    public void testUpdate() {
        User param = new User();
        param.setId("1228");
        param.setNickname("爱学啊6");
        param.setIcon("http://thirdqq.qlogo.cn/qqopen/vYLjenVtwibnEbh6glpMFmRRN8MTCibicnh7LKyW5uKHBLdkicGtQ9u857tvibutVYozv/100?v=564a");
        param.setDetail("我们是一家专注于IT职业教育的在线教育企业。目的是通过对课程质量的苛刻要求，以达到学习完我们课程的同学能真真正正的学习到知识和经验，能让他们成为行业的高端人才，同时拥有更好的人生规划和职业发展前景。");
        param.setEmail("ixueaeduady@163.com");
        param.setPhone("13141111222");
        param.setPassword("$2a$10$DgbUsMA.cgvusCcO3oxATu0fxffvLcGB9gAnl6Ie.TUv.gLWUE.2i");
        param.setRole("admin,anchor");
        param.setCreatedAt(new Date());
        param.setUpdatedAt(new Date());

        service.update(param);
    }

    /**
     * 测试 批量更新
     *
     * 将数据前100条歌单导入到搜索引擎
     * 真实项目中，如果数据比较多，那也要分页查询，然后导入，例如：1次导入100条
     * 至于每次导入多少条，看每条数据了
     */
    @Test
    public void testBulkUpdate() {
        IPage<User> result = userService.findAll(new User(), 1, 100, "user.created_at", "desc");

        service.update(result.getRecords());
    }

    /**
     * 测试 删除
     */
    @Test
    public void testDelete() {
        service.delete("1228");
    }
}

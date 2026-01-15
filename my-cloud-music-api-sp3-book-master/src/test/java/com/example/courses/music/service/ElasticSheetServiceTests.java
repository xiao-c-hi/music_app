package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.courses.music.BaseTests;
import com.example.courses.music.model.Sheet;
import com.example.courses.music.model.response.PageResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 歌单Elastic服务 测试
 */
public class ElasticSheetServiceTests extends BaseTests {
    private static Logger log = LoggerFactory.getLogger(ElasticSheetServiceTests.class);

    @Autowired
    private ElasticSheetService service;

    @Autowired
    SheetService sheetService;

    /**
     * 测试 搜索
     */
    @Test
    public void testFindAll() {
        PageResponse result = service.findAll("ios", 1, 2);
        log.info("testFindAll {}", result);
    }

    /**
     * 测试 更新
     */
    @Test
    public void testUpdate() {
        Sheet param = new Sheet();
        param.setId("1228");
        param.setTitle("这世上所有的歌zheshishangtest ios开发 爱学啊");
        param.setIcon("assets/list1.jpg");
        param.setDetail("这是因为iOS9引入了新特性App Transport Security (ATS)，他要求App内网络请求必须使用HTTPS协议。解决方法是要么改为HTTPS，要么声明可以使用HTTP，可以声明部分使用HTTP，也可以所有；但需要说明的是如果APP内所有请求都是HTTP，那么如果要上架App Store的时候基本都会被拒。");
        param.setClicksCount(4253);
        param.setCollectsCount(13);
        param.setCommentsCount(5);
        param.setSongsCount(765);
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
        IPage<Sheet> result = sheetService.findAll(1, 100);

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

package com.example.courses.music.service;

import com.example.courses.music.BaseTests;
import com.example.courses.music.model.Address;
import com.example.courses.music.model.request.NLPAddressRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 百度NLP服务测试
 */
public class BaiduNLPServiceTest extends BaseTests {
    private static Logger log = LoggerFactory.getLogger(BaiduNLPServiceTest.class);

    @Autowired
    private BaiduNLPService service;

    /**
     * 测试 收货地址识别
     */
    @Test
    public void testAddress() {
        NLPAddressRequest request = new NLPAddressRequest();
        request.setData("四川省成都市天府新区牧华路远大中央公园9栋1单元801 李微 13141111222");
        Address address = service.address(request);

        //断言判断，正确的，实际的
        Assert.assertEquals("四川省",address.getProvince());
        Assert.assertEquals("成都市",address.getCity());
        Assert.assertEquals("双流区",address.getArea());

        Assert.assertEquals("李薇", address.getName());
        Assert.assertEquals("13141111222", address.getTelephone());
    }
}
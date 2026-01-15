package com.example.courses.music;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * double计算测试
 *
 * 具体的大家可以搜索了解出现的原因
 */
public class DoubleTest{
    private static Logger log = LoggerFactory.getLogger(DoubleTest.class);

    /**
     * 测试浮点数相加出现的精度损失
     *
     * 解决方法是，浮点数计算时，如果要绝对精确（例如价格计算时），用BigDecimal
     */
    @Test
    public void testAdd(){
        //期望登录0.3，但实际却不是
        double result = 0.1 + 0.2;
        log.info("{}", result);
        Assert.assertNotEquals(0.3, result);
    }
}

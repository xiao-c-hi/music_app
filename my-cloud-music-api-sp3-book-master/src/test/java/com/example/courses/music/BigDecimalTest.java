package com.example.courses.music;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * BigDecimal计算测试
 *
 */
public class BigDecimalTest {
    private static Logger log = LoggerFactory.getLogger(BigDecimalTest.class);

    /**
     * 测试使用BigDecimal浮点数不会出现精度损失
     *
     * 解决方法是，浮点数计算时，如果要绝对精确（例如价格计算时），用BigDecimal
     */
    @Test
    public void testAdd(){
        //初始化的时候，直接传递浮点数也会有问题，要转为字符串在传递
        BigDecimal o1 = new BigDecimal("0.1");
        BigDecimal o2 = new BigDecimal("0.2");

        //期望结果
        BigDecimal expectResult = new BigDecimal("0.3");

        //相加
        BigDecimal result = o1.add(o2);
        log.info("{}", result);

        //浮点数判断相对推荐这样判断，而不是用等号
        boolean equals = expectResult.equals(result);

        //比较大小
//        o1.compareTo(o2)

        Assert.assertTrue(equals);
    }
}

package com.example.courses.music.util;

import com.example.courses.music.model.Order;

/**
 * 订单工具类
 */
public class OrderUtil {
    /**
     * 获取支付标题
     * <p>
     * 可以返回商品标题，真实项目中商品标题可能很长，所以需要截取
     *
     * @param data
     * @return 爱学啊-订单编号
     */
    public static String getPayTitle(Order data) {
        StringBuilder builder = new StringBuilder();
        builder.append("爱学啊-");
        builder.append(data.getNumber());

        return builder.toString();
    }
}

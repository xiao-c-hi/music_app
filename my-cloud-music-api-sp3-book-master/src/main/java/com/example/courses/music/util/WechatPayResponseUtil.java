package com.example.courses.music.util;

/**
 * 微信支付响应工具类
 */
public class WechatPayResponseUtil {
    /**
     * 错误响应
     *
     * @param data
     * @return
     */
    public static Object failed(String data) {
        return String.format(Constant.WECHAT_PAY_RESPONSE, data);
    }

    public static Object success() {
        return Constant.WECHAT_PAY_SUCCESS;
    }
}

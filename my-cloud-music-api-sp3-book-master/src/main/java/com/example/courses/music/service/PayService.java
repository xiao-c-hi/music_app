package com.example.courses.music.service;

import com.example.courses.music.exception.SaveDataException;
import com.example.courses.music.model.Order;
import com.example.courses.music.model.response.PayResponse;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.IPUtil;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付服务
 */
@Service
public class PayService {
    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    /**
     * 支付宝服务
     */
    @Autowired
    private AlipayService alipayService;

    /**
     * 微信支付服务
     */
    @Autowired
    private WechatPayService wechatPayService;

    public Object pay(Order data, HttpServletRequest request) {
        //更新到数据库
        if (orderService.update(data) < Constant.RESULT_OK) {
            throw new SaveDataException();
        }

        //根据不同渠道生成支付参数
        PayResponse payResponse;
        if (data.getChannel() == Constant.PAY_CHANNEL_ALIPAY) {
            //支付宝支付

            //如果写到这里
            //该方法的逻辑会很多
            payResponse = alipayService.pay(data);
        } else {
            //微信支付

            //获取ip
            String ip = IPUtil.getIP(request);

            payResponse = wechatPayService.pay(data, ip);
        }

        return R.wrap(payResponse);
    }
}
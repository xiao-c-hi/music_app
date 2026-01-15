package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.Order;
import com.example.courses.music.service.OrderService;
import com.example.courses.music.service.PayService;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付控制器
 */
@RestController
@RequestMapping("/v1")
public class PayController {

    @Autowired
    PayService service;

    @Autowired
    OrderService orderService;

    /**
     * 获取支付参数
     *
     * @param data
     * @param id
     * @return
     */
    @PostMapping("/orders/{id}/pay")
    public Object pay(@RequestBody Order data,
                      @PathVariable String id,
                      HttpServletRequest request) {
        StpUtil.checkLogin();

        //查询数据
        Order order = orderService.findByIdAndUserId(id, StpUtil.getLoginIdAsString());

        ValidatorUtil.checkExist(order);

        //判断订单状态
        //只有待支付状态才能生成支付参数
        if (!order.isWaitPay()) {
            //订单状态错误
            throw new CommonException(Constant.ERROR_ORDER_STATUS, Constant.ERROR_ORDER_STATUS_MESSAGE);
        }

        //判断支付渠道
        if (data.getChannel() != Constant.PAY_CHANNEL_ALIPAY &&
                data.getChannel() != Constant.PAY_CHANNEL_WECHAT) {
            throw new CommonException(Constant.ERROR_ORDER_PAY_CHANNEL, Constant.ERROR_ORDER_PAY_CHANNEL_MESSAGE);
        }

        //判断支付来源
        if (data.getOrigin() != Constant.WEB &&
                data.getOrigin() != Constant.ANDROID &&
                data.getOrigin() != Constant.IOS &&
                data.getOrigin() != Constant.WAP) {
            throw new CommonException(Constant.ERROR_ORDER_PAY_ORIGIN, Constant.ERROR_ORDER_PAY_ORIGIN_MESSAGE);
        }

        //参数正确

        //保存到订单
        order.setChannel(data.getChannel());
        order.setOrigin(data.getOrigin());

        return service.pay(order, request);
    }
}

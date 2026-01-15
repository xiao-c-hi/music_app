package com.example.courses.music.service;

import com.github.wxpay.sdk.WXPay;
import com.example.courses.music.model.Order;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.WechatPayResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 回调服务
 */
@Service
public class CallbackService {
    /**
     * 日志api
     */
    private static final Logger log = LoggerFactory.getLogger(CallbackService.class);

    /**
     * 支付宝服务
     */
    @Autowired
    private AlipayService alipayService;

    @Autowired
    private OrderService orderService;

    /**
     * 微信支付api
     */
    @Autowired
    private WXPay wxPay;

    /**
     * 支付宝支付回调
     *
     * @param requestParameter
     * @return
     */
    public Object alipayCallback(Map<String, String[]> requestParameter) {
        //验证签名
        if (alipayService.verify(requestParameter)) {
//        if (true) {
            //签名验证成功

            //订单id
            String orderId = requestParameter.get("passback_params")[0];

            log.info("alipay callback verify success {}", orderId);

            //向支付宝，支付了多少钱
            String totalAmount = requestParameter.get("total_amount")[0];

            //商户订单号
            //就是我们的订单号
            String outTradeNo = requestParameter.get("out_trade_no")[0];

            //根据order_id，out_trade_no，total_amount，状态为0(待支付的订单)的订单
            //查找订单，这样是防止支付宝多次回调导致业务逻辑不对
            //如果回调失败，就会多次回调，有一个频率
            //根据金额查找的好处是，假设商品99元，如果有人破解了客户端
            //将金额更改为1元钱，付款成功了
            //支付宝就会回调我们
            //如果在这里，我们不判断金额，直接将订单更改为已经支付
            //就相当于花了1元钱，买了99元的商品
            //所以一定要校验金额
            Order order = orderService.findByIdAndNumberAndPriceAndStatus(orderId, outTradeNo, totalAmount, Constant.WAIT_PAY);
            if (order == null) {
                //没找到商品
                log.error("alipay callback order not found {}", orderId);

                return "alipay callback order not found";
            }

            //订单号，商户订单号，金额，状态匹配成功
            log.info("alipay callback order found {}", orderId);

            //支付宝交易号(对应支付宝订单详细，订单号)
            String tradeNo = requestParameter.get("trade_no")[0];

            //更改订单状态
            if (orderService.updateStatusAndOther(order.getId(),
                    Constant.WAIT_SHIPPED,
                    tradeNo) == Constant.RESULT_OK) {
                //更改订单成功
                log.info("alipay callback change status success {}", orderId);

                //如果没出问题，就返回成功
                //如果不返回成功
                //支付宝就认为失败了
                //会按照一定频率持续回调
                return Constant.PAY_SUCCESS;
            } else {
                //更新订单失败
                log.error("alipay callback change status error");

                return "alipay callback change status error";
            }
        } else {
            //参数验证签名失败
            log.error("alipay callback verify failed");
            return Constant.SIGN_VERIFY_FAILED;
        }
    }

    /**
     * 微信支付回调
     *
     * @param requestParameter
     * @return
     */
    public Object wechatPayCallback(Map<String, String> requestParameter) throws Exception {
        //验证签名
        if (!wxPay.isPayResultNotifySignatureValid(requestParameter)) {
            //参数验证签名失败
            log.error("wechat pay callback verify failed");
            return WechatPayResponseUtil.failed(Constant.SIGN_VERIFY_FAILED);
        }

        //签名验证成功

        //订单id
        String orderId = requestParameter.get("attach");

        log.info("wechat pay callback verify success {}", orderId);

        //支付了多少钱
        //订单总金额，单位为分
        Integer totalAmountInt = Integer.valueOf(requestParameter.get("total_fee"));

        //金额转为double，保留两位小数
        String totalAmount = String.format("%.2f", totalAmountInt / 100.0);
//        Double totalAmount = Double.valueOf();

        //商户订单号
        //就是我们的订单号
        String outTradeNo = requestParameter.get("out_trade_no");

        //根据order_id，out_trade_no，total_amount，状态为0(待支付的订单)的订单
        Order order = orderService.findByIdAndNumberAndPriceAndStatus(orderId, outTradeNo, totalAmount, Constant.WAIT_PAY);
        if (order == null) {
            //没找到商品
            log.error("wechat pay callback order not found {}", orderId);

            return WechatPayResponseUtil.failed("wechat pay callback order not found");
        }

        //订单号，商户订单号，金额，状态匹配成功
        log.info("wechat pay callback order found {}", orderId);

        //微信支付订单号
        String tradeNo = requestParameter.get("transaction_id");

        //更改订单状为支付成功
//        order.setStatus(Constant.ORDER_STATUS_PAID);

        if (orderService.updateStatusAndOther(order.getId(), Constant.WAIT_SHIPPED, tradeNo) != Constant.RESULT_OK) {
            //更新订单失败
            log.error("wechat pay callback change status error");

            return WechatPayResponseUtil.failed("wechat pay callback change status error");
        }

        //更改订单成功
        log.info("wechat pay callback change status success {}", orderId);

        //如果没出问题，就返回成功
        return WechatPayResponseUtil.success();
    }
}
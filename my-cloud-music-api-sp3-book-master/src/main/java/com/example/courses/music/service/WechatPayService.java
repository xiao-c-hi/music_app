package com.example.courses.music.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.example.courses.music.config.Config;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.Order;
import com.example.courses.music.model.response.PayResponse;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.OrderUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 微信支付服务
 */
@Service
public class WechatPayService {
    /**
     * 日志api
     */
    private static final Logger log = LoggerFactory.getLogger(WechatPayService.class);

    /**
     * 当前应用地址
     */
    @Value("${info.host}")
    private String host;

    /**
     * 微信支付api
     */
    @Autowired
    private WXPay wxPay;

    /**
     * 获取app支付参数
     * <p>
     * 统一下单接口文档：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
     * 但因为我们用了官方提供的sdk，所以只需要调用统一下单api
     *
     * @param data
     * @param ip
     * @return
     */
    public PayResponse pay(Order data, String ip) {
        Map<String, String> param = getWechatPayParam(data, ip);

        //添加类型
        param.put("trade_type", "APP");

        //调用统一下单接口
        Map<String, String> resp = null;
        try {
            resp = wxPay.unifiedOrder(param);
        } catch (Exception e) {
            log.error("getPayParam failed unifiedOrder error {}", e);
            throw new CommonException(Constant.ERROR_GENERATE_WECHAT_PAY, Constant.ERROR_GENERATE_WECHAT_PAY_MESSAGE);
        }
        //判断是否成功
        //第一种情况，参数有问题，例如：标题太长了，会返回如下错误
        //"return_msg" -> "输入源“/body/xml/body”映射到值字段“商品描述”字符串规则校验失败，字节数 200，大于最大值 128"
        //"return_code" -> "FAIL"
        if (resp.containsKey("return_code") && resp.get("return_code").equals("FAIL")) {
            String message = resp.get("return_msg");

            if (StringUtils.isEmpty(message)) {
                message = Constant.ERROR_GENERATE_WECHAT_PAY_MESSAGE;
            }

            log.error("getPayParam failed unifiedOrder error {}", resp);
            throw CommonException.createExtra(resp.get("return_code"), message);
        }

        //第二种情况，例如商户订单号重复
        //"err_code" -> "INVALID_REQUEST"
        //"return_msg" -> "OK"
        //"result_code" -> "FAIL"
        //"err_code_des" -> "201 商户订单号重复"
        //"return_code" -> "SUCCESS"
        if (resp.containsKey("result_code") && resp.get("result_code").equals("FAIL")) {
            String message = resp.get("err_code_des");

            if (StringUtils.isEmpty(message)) {
                message = Constant.ERROR_GENERATE_WECHAT_PAY_MESSAGE;
            }

            log.error("getPayParam failed unifiedOrder error {}", resp);
            throw CommonException.createExtra(resp.get("return_code"), message);
        }

        //对于app支付，统一下单成功后信息客户端还不能直接使用
        //还要添加一些参数，并重新计算签名
        //官方文档：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12&index=2
        //个人认为没必要设计这一部分
        //最好像支付宝那边接口返回的客户端直接可以用
        HashMap<String, String> result = new HashMap<>();

        //appid
        result.put("appid", resp.get("appid"));

        //商户id
        result.put("partnerid", resp.get("mch_id"));

        //与支付订单id
        result.put("prepayid", resp.get("prepay_id"));

        //扩展字段,Sign=WXPay
        result.put("package", "Sign=WXPay");

        //随机字符串
        result.put("noncestr", String.valueOf(new Random().nextInt()));

        //添加时间戳，要转为东八区
        result.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));

        try {
            result.put("sign", WXPayUtil.generateSignature(result, Config.WECHAT_PAY_API_AS, WXPayConstants.SignType.HMACSHA256));
        } catch (Exception e) {
            log.error("getPayParam failed generateSignature error {}", e);
            throw new CommonException(Constant.ERROR_WECHAT_PAY_SIGN, Constant.ERROR_WECHAT_PAY_SIGN_MESSAGE);
        }

        PayResponse pay = new PayResponse(data.getChannel(), result);

        return pay;
    }

    /**
     * 获取微信支付参数
     *
     * @param data
     * @param ip
     * @return
     */
    private Map<String, String> getWechatPayParam(Order data, String ip) {
        HashMap<String, String> param = new HashMap<>();

        //商品描述
        param.put("body", OrderUtil.getPayTitle(data));

        //商户订单号
        param.put("out_trade_no", data.getNumber());

        //设备号
//        param.put("device_info", "");

//标价币种
//        param.put("fee_type", "CNY");

        //标价金额，单位为分，整形
        param.put("total_fee", String.valueOf(data.getPrice()));

        //终端IP
        param.put("spbill_create_ip", ip);

        //异步通知地址
        param.put("notify_url", String.format(Config.WECHAT_PAY_NOTIFY_URL, host));

        //商品ID
//        param.put("product_id", data.getBook().getId());

        //附加数据
        //这里传订单id
        param.put("attach", data.getId());

        return param;
    }
}

package com.example.courses.music.controller.v1;

import com.github.wxpay.sdk.WXPayUtil;
import com.example.courses.music.service.CallbackService;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.WechatPayResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 回调控制器
 */
@RestController
@RequestMapping("/v1")
public class CallbackController {
    /**
     * 日志api
     */
    private static final Logger log = LoggerFactory.getLogger(CallbackController.class);

    @Autowired
    private CallbackService service;


    /**
     * 支付宝支付回调
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/callbacks/alipay", produces = "text/plain;charset=UTF-8")
    public Object alipayCallback(HttpServletRequest request) {
        //获取请求参数
        Map<String, String[]> requestParameter = request.getParameterMap();

        //打印到日志，方便调试
        log.info("alipay callback param {}", requestParameter);

        return service.alipayCallback(requestParameter);
    }

    /**
     * 微信支付回调
     * 官方文档：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_7&index=3
     * @param data
     * @return
     */
    @PostMapping(value = "/callbacks/wechat-pay",produces = "text/xml;charset=UTF-8")
    public @ResponseBody
    Object wechatPayCallback(@RequestBody String data) {
        try {
            //获取请求参数
            // 支付结果通知的xml格式数据
            log.info("wechat pay callback param {}",data);

            //转为map
            Map<String, String> requestParameter = WXPayUtil.xmlToMap(data);

            return service.wechatPayCallback(requestParameter);
        } catch (Exception e) {
            log.error("wechat pay callback unknown error {}", e);
            return WechatPayResponseUtil.failed(Constant.ERROR_UNKNOWN_MESSAGE);
        }
    }
}

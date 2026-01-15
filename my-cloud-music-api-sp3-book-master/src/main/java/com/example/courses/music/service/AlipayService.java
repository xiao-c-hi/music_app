package com.example.courses.music.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.example.courses.music.config.Config;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.Order;
import com.example.courses.music.model.response.PayResponse;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.OrderUtil;
import com.example.courses.music.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class AlipayService {
    private static Logger log = LoggerFactory.getLogger(AlipayService.class);

    /**
     * 当前应用地址
     */
    @Value("${info.host}")
    private String host;

    /**
     * 支付宝应用id
     */
    @Value("${alipay.id}")
    private String alipayAppId;

    /**
     * app私钥
     */
    @Value("${alipay.private}")
    private String alipayAppPrivateKey;

    @Value("${alipay.appCertPath}")
    private String appCertPath;

    @Value("${alipay.alipayCertPath}")
    private String alipayCertPath;

    @Value("${alipay.alipayRootCertPath}")
    private String alipayRootCertPath;

    /**
     * 支付宝公钥
     */
    @Value("${alipay.public}")
    private String alipayPublicKey;

    /**
     * 获取支付宝支付参数
     *
     * @param data
     * @return
     */
    public PayResponse pay(Order data) {
//        //创建测试支付参数
//        Pay pay = new Pay(data.getChannel(), "这是测试支付参数");
//
//        return pay;

        //可以再区分具体的平台
        //因为有些平台有些特殊的参数
        //还有就是不同的平台可能表现形式不一样
        //例如：app支付由客户端完成
        //而网页支付，一般是一个网页地址
        if (Constant.WEB == data.getOrigin()) {
            //web支付
        } else if (Constant.ANDROID == data.getOrigin() ||
                Constant.IOS == data.getOrigin()) {
            //app支付
            return appPay(data);
        } else if (Constant.WAP == data.getOrigin()) {
            //wap支付
        }

        throw new ArgumentException();
    }

    /**
     * 获取app支付参数
     *
     * @param data
     * @return
     */
//    private PayResponse appPay(Order data) {
//        //实例化客户端
//        DefaultAlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
//                alipayAppId,
//                alipayAppPrivateKey,
//                "json",
//                "utf-8",
//                alipayPublicKey,
//                "RSA2");
//
//        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
//        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//
//        //SDK已经封装掉了公共参数
//        //这里只需要传入业务参数
//        //以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)
//        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//
//        //标题
//        //这里传递订单号
//        //支付宝官网参数解释：https://docs.open.alipay.com/204/105465/
//        model.setSubject(OrderUtil.getPayTitle(data));
//
//        //是你作为商户提供给支付宝的订单号。建议引用你应用内相应模型的 ID，并不能重复。
//        model.setOutTradeNo(data.getNumber());
//
//        //支付超时时间
////        model.setTimeoutExpress("30m");
//
//        //是支付订单的金额，精确到小数点后两位。例如金额 5,123.99 元的参数值应为 '5123.99'。
//        model.setTotalAmount(StringUtil.formatFloat2(data.getPrice()));
//
//        //公用回传参数，如果请求的时候传递了，支付宝异步通知的时候会带上，这样回调的时候我们就可以校验Id和单号是否一样，其实回调的时候根据订单号查询也是可以的
//        model.setPassbackParams(data.getId());
//
//        //app快捷支付
//        model.setProductCode("QUICK_MSECURITY_PAY");
//
//        //设置业务模型
//        request.setBizModel(model);
//
//        //设置异步通知
//        request.setNotifyUrl(String.format(Config.ALIPAY_NOTIFY_URL, host));
//
//        try {
//            //这里和普通的接口调用不同
//            //使用的是sdkExecute
//            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
//
//            //获取支付字符串
//            //直接返回给客户端
//            PayResponse pay = new PayResponse(data.getChannel(), response.getBody());
//
//            return pay;
//        } catch (AlipayApiException e) {
//            log.error("getAppPayParam failed {} {}", data.getId(), e);
//        }
//        throw new ArgumentException();
//    }

    /**
     * Java 服务端 SDK 生成 APP 支付订单信息示例（证书）
     * https://opendocs.alipay.com/open/54/106370
     * @param data
     * @return
     */
    private PayResponse appPay(Order data){
        try  {
        //构造client
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest ();
        //设置网关地址
        certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
        //设置应用Id
        certAlipayRequest.setAppId(alipayAppId);
        //设置应用私钥
        certAlipayRequest.setPrivateKey(alipayAppPrivateKey);
        //设置请求格式，固定值json
        certAlipayRequest.setFormat("json");
        //设置字符集
        certAlipayRequest.setCharset("utf-8");
        //设置签名类型
        certAlipayRequest.setSignType("RSA2");

        //获取jar目录
            ApplicationHome h = new ApplicationHome(getClass());
        File jarPathFile = h.getSource().getParentFile();

        //设置应用公钥证书路径
//            ClassPathResource classPathResource = new ClassPathResource(appCertPath);
        certAlipayRequest.setCertPath(new File(jarPathFile,appCertPath).getAbsolutePath());
        //设置支付宝公钥证书路径
        certAlipayRequest.setAlipayPublicCertPath(new File(jarPathFile,alipayCertPath).getAbsolutePath());
        //设置支付宝根证书路径
        certAlipayRequest.setRootCertPath(new File(jarPathFile,alipayRootCertPath).getAbsolutePath());

        //实例化客户端
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

        //SDK已经封装掉了公共参数
        //这里只需要传入业务参数
        //以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        //标题
        //这里传递订单号
        //支付宝官网参数解释：https://docs.open.alipay.com/204/105465/
        model.setSubject(OrderUtil.getPayTitle(data));

        //是你作为商户提供给支付宝的订单号。建议引用你应用内相应模型的 ID，并不能重复。
        model.setOutTradeNo(data.getNumber());

        //支付超时时间
//        model.setTimeoutExpress("30m");

        //是支付订单的金额，精确到小数点后两位。例如金额 5,123.99 元的参数值应为 '5123.99'。
        model.setTotalAmount(StringUtil.formatFloat2(data.getPrice()/100.0));

        //公用回传参数，如果请求的时候传递了，支付宝异步通知的时候会带上，这样回调的时候我们就可以校验Id和单号是否一样，其实回调的时候根据订单号查询也是可以的
        model.setPassbackParams(data.getId());

        //app快捷支付
        model.setProductCode("QUICK_MSECURITY_PAY");

        //设置业务模型
        request.setBizModel(model);

        //设置异步通知
        request.setNotifyUrl(String.format(Config.ALIPAY_NOTIFY_URL, host));

            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute( request );
            //获取支付字符串
            //直接返回给客户端
            PayResponse pay = new PayResponse(data.getChannel(), response.getBody());

            return pay;
        }  catch (Exception e ) {
            log.error("getAppPayParam failed {} {}", data.getId(), e);
            throw new CommonException(Constant.ERROR_THIRD_PARTY_SERVICE,Constant.ERROR_THIRD_PARTY_SERVICE_MESSAGE,e);
        }
    }

    /**
     * 支付宝回调签名验证
     * 代码来自支付宝官方文档：https://docs.open.alipay.com/54/106370
     *
     * @param requestParameter
     * @return
     */
    public boolean verify(Map<String, String[]> requestParameter) {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();

        for (Iterator iter = requestParameter.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();

            String[] values = (String[]) requestParameter.get(name);

            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }

            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //切记alipaypublickey是支付宝的公钥
        //请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        try {
            return AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", "RSA2");
        } catch (AlipayApiException e) {
            log.error("verify failed:", e);
            return false;
        }
    }
}

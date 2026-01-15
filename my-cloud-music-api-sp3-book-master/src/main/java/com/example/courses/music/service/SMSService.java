package com.example.courses.music.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 短信服务
 */
@Service
public class SMSService {
    private static Logger log = LoggerFactory.getLogger(SMSService.class);

    /**
     * 阿里云ak
     */
    @Value("${aliyun.key}")
    private String key;

    /**
     * 阿里云sk
     */
    @Value("${aliyun.secret}")
    private String secret;

    /**
     * 阿里云短信地域id
     */
    @Value("${aliyun.sms.region}")
    private String regionId;

    /**
     * 发送短信验证码
     *
     * @param to
     * @param code
     */
    public void sendSMSCode(String to, String code) {
        //创建配置
        DefaultProfile profile = DefaultProfile.getProfile(regionId, key, secret);

        //创建客户端
        DefaultAcsClient client = new DefaultAcsClient(profile);

        //创建通用请求
        CommonRequest request = new CommonRequest();

        //以下是固定写法
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", regionId);

        //手机号
        request.putQueryParameter("PhoneNumbers", to);

        //设置短信模板
        //在阿里云后台查看
        request.putQueryParameter("TemplateCode", "SMS_156345375");

        //短信签名
        //大家要替换成自己的
        request.putQueryParameter("SignName", "爱学啊");

        //设置验证码
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", code);
        request.putQueryParameter("TemplateParam", jsonObject.toString());

        try {
            //发送验证码
            CommonResponse response = client.getCommonResponse(request);

            //这里就不处理错误了
            //真实项目中，可能会把状态保存到数据库
            log.info("requestSMSCode success {}", to);
        } catch (ClientException e) {
            log.error("requestSMSCode failed {} {}", to, e);
        }
    }
}
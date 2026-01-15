package com.example.courses.music.service;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.alibaba.fastjson.JSONObject;
import com.example.courses.music.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 推送服务
 */
@Service
public class PushService {
    private static final Logger log = LoggerFactory.getLogger(PushService.class);

    /**
     * 推送客户端
     */
    private JPushClient pushClient;

    @Autowired
    public void setPushClient(JPushClient jPushClient) {
        this.pushClient = jPushClient;
    }

    /**
     * 发送强制退出推送通知
     * <p>
     * 这里发送的是自定义推送，如果客户端不在线就收不到
     * 所以如果要做的更好，可以判断用户在线发送自定义通知
     * 发送自定义通知目的是，如果客户端在前台直接弹窗对话框
     * 当然也可以就发送普通通知
     * <p>
     * 不在线发送普通通知（通过厂商通道）显示到通知栏
     * <p>
     * 所以从这一点就可以看出任何功能如果要实现的更完善，都可能很复杂
     *
     * @param id
     */
    public void sendLogout(String id) {
        //推送需求：根据设备id推送

        //创建推送的消息
        //退出通知是一个json:
        //{
        //  "style": 0
        //}
        //com.alibaba.fastjson.JSONObject
        JSONObject contentJson = new JSONObject();
        contentJson.put("style", Constant.PUSH_STYLE_LOGOUT);

        //创建推送内容对象
        Message message = Message.newBuilder()
                .setMsgContent(contentJson.toJSONString())
                .build();

        //创建推送对象
        PushPayload pushPayload = PushPayload.newBuilder()
                //所有平台
                .setPlatform(Platform.all())

                //通过设备id推送
                .setAudience(Audience.registrationId(id))

                //设置推送消息
                .setMessage(message)
                .build();

        try {
            //开始推送
            //这里不需要知道推送结果
            PushResult result = pushClient.sendPush(pushPayload);
            log.info("sendLogout success {}", id);
        } catch (Exception e) {
            log.error("sendLogout failed {} {}", id, e);
        }
    }
}
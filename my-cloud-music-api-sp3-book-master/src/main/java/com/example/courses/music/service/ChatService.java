package com.example.courses.music.service;

import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.User;
import com.example.courses.music.util.Constant;
import io.rong.RongCloud;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 聊天服务
 */
@Service
public class ChatService {
    @Autowired
    RongCloud rongCloud;

    /**
     * 注册或者更新聊天用户，主要目的是获取聊天token
     * http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#register
     *
     * @param data
     * @return
     */
    public String createOrUpdateChatUser(User data) {
        try {
            String name = data.getNickname();
            if (name==null) {
                name = data.getPhone();
            }

            if (name == null) {
                name = data.getEmail();
            }

            UserModel userModel = new UserModel()
                    .setId(data.getId())
                    .setName(name)
                    .setPortrait(data.getIcon());
            TokenResult result = rongCloud.user.register(userModel);
            if (result.getCode() == 200) {
                //正常
                return result.getToken();
            }
            throw new CommonException(Constant.ERROR_CREATE_CHAT_TOKEN, Constant.ERROR_CREATE_CHAT_TOKEN_MESSAGE);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(Constant.ERROR_CREATE_CHAT_TOKEN, Constant.ERROR_CREATE_CHAT_TOKEN_MESSAGE);
        }
    }
}
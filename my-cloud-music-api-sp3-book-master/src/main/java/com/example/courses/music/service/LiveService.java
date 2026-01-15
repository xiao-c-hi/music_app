package com.example.courses.music.service;

import com.aliyun.imp20210630.Client;
import com.aliyun.imp20210630.models.*;
import com.example.courses.music.config.properties.AliyunProperties;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.Room;
import com.example.courses.music.model.request.LiveTokenRequest;
import com.example.courses.music.model.response.LiveTokenResponse;
import com.example.courses.music.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 直播服务
 */
@Service
public class LiveService {
    @Autowired
    Client client;

    @Autowired
    AliyunProperties properties;

    /**
     * 创建直播sdk token
     *
     * @param data
     * @return
     */
    public LiveTokenResponse create(LiveTokenRequest data) {
        try {
            GetAuthTokenRequest param = new GetAuthTokenRequest();
            param.setAppId(properties.getLive().getKey());
            param.setAppKey(data.getSecret());
            param.setUserId(data.getUserId());
            param.setDeviceId(data.getDeviceId());
            GetAuthTokenResponse response = client.getAuthToken(param);

            GetAuthTokenResponseBody.GetAuthTokenResponseBodyResult result = Optional.ofNullable(response)
                    .map(GetAuthTokenResponse::getBody)
                    .map(GetAuthTokenResponseBody::getResult)
                    .orElse(null);

            return new LiveTokenResponse(result.accessToken, result.refreshToken, result.accessTokenExpiredTime);
        } catch (Exception e) {
            throw new CommonException(Constant.ERROR_LIVE_TOKEN, Constant.ERROR_LIVE_TOKEN_MESSAGE, e);
        }
    }

    /**
     * 创建直播间
     * <p>
     * 只有申请成为主播时，才创建一次
     * 以后都是使用这一个直播间id，就行直播
     * 官方文档：https://help.aliyun.com/document_detail/273152.html
     *
     * @param data
     * @param userId
     * @return
     */
    public Room create(Room data, String userId) {
        try {
            CreateRoomRequest param = new CreateRoomRequest();
            param.appId = properties.getLive().getKey();

            //直播间id
            param.roomId = data.getId();

            //标题
            param.setTitle(data.getTitle());

            //房主
            param.roomOwnerId = userId;

            //创建直播间
            CreateRoomResponse response = client.createRoom(param);

            CreateRoomResponseBody.CreateRoomResponseBodyResult responseBodyResult = Optional.ofNullable(response)
                    .map(CreateRoomResponse::getBody)
                    .map(CreateRoomResponseBody::getResult)
                    .orElse(null);

            return new Room(responseBodyResult.getRoomId());
        } catch (Exception e) {
            throw new CommonException(Constant.ERROR_LIVE_CREATE_ROOM, Constant.ERROR_LIVE_CREATE_ROOM_MESSAGE, e);
        }
    }

    /**
     * 删除直播间
     * <p>
     * 官方文档：https://help.aliyun.com/document_detail/273153.html
     *
     * @param data
     */
    public void destroyRoom(String data) {
        try {
            DeleteRoomRequest param = new DeleteRoomRequest();
            param.appId = properties.getLive().getKey();

            //直播间id
            param.roomId = data;

            DeleteRoomResponse response = client.deleteRoom(param);

            DeleteRoomResponseBody responseBodyResult = Optional.ofNullable(response)
                    .map(DeleteRoomResponse::getBody)
                    .orElse(null);
        } catch (Exception e) {
            throw new CommonException(Constant.ERROR_LIVE_DELETE_ROOM, Constant.ERROR_LIVE_DELETE_ROOM_MESSAGE, e);
        }
    }
}
package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.Room;
import com.example.courses.music.model.User;
import com.example.courses.music.service.LiveService;
import com.example.courses.music.service.UserService;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 直播间控制器
 */
@RestController
@RequestMapping("/v1/rooms")
public class RoomController {
    @Autowired
    private LiveService service;

    @Autowired
    private UserService userService;

    /**
     * 创建
     *
     * @param data
     * @return
     */
    @PostMapping
    public @ResponseBody
    Object create(@RequestBody Room data) {
        StpUtil.checkLogin();

        String userId = StpUtil.getLoginIdAsString();

        User currentUser = userService.find(userId);
        if (currentUser.getRoomId() != null) {
            //已经创建了直播间

            //提示已经存在
            throw new CommonException(Constant.ERROR_LIVE_ALREADY_ROOM, Constant.ERROR_LIVE_ALREADY_ROOM_MESSAGE);
        }

        //生成直播间id
        //生成规则，查询用户roomId，按照从大大小排序，取最后一个用户，获取值，然后加1
        //当然生成后，也可以判断如果生成的直播间id符合靓号规则，可以跳过
        //当然也可以用一个张表，自动生成直播间id
        Long roomId = null;
        User oldUser = userService.findByRoomIdNotNullOrderByRoomIdDesc();
        if (oldUser == null) {
            roomId = Constant.DEFAULT_ROOM_ID;
        } else {
            roomId = oldUser.getRoomId() + 1;
        }

        data.setId(roomId.toString());
        Room result = service.create(data, userId);

        //保存直播间id到用户
        currentUser.setRoomId(roomId);
        userService.update(currentUser);

        return R.wrap(result);
    }
}

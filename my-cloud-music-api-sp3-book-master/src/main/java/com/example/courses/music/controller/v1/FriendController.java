package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.model.Friend;
import com.example.courses.music.model.User;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.service.FriendService;
import com.example.courses.music.service.UserService;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.R;
import com.example.courses.music.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/v1/friends")
public class FriendController {
    /**
     * 用户服务
     */
    @Autowired
    private UserService userService;

    /**
     * 好友mapper
     */
    @Autowired
    private FriendService service;

    /**
     * 关注用户
     *
     * @param data
     * @return
     */
    @PostMapping
    public Object create(@RequestBody User data) {
        StpUtil.checkLogin();

        //查找要关注的用户
        //如果不需要更详细的提示
        //就不用再查询了
        //这样效率更高
        User otherUser = userService.find(data.getId());

        ValidatorUtil.checkExist(otherUser);

        //不能关注自己
        if (StpUtil.getLoginIdAsString().equals(data.getId())) {
            throw new ArgumentException();
        }

        //创建好友对象
        Friend friend = new Friend();

        //设置当前用户id
        friend.setFollowerId(StpUtil.getLoginIdAsString());

        //设置要关注用户id
        friend.setFollowedId(otherUser.getId());

        service.create(friend);

        //返回数据
        return R.wrap(friend.getId());
    }

    /**
     * 取消关注
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public Object destroy(@PathVariable String userId) {
        StpUtil.checkLogin();

        //删除这条关系
        if (service.deleteByFollowerIdAndFollowedId(StpUtil.getLoginIdAsString(), userId)
                != Constant.RESULT_OK) {
            //取消关注失败
            throw new CommonException(Constant.ERROR_CANCEL_FOLLOW, Constant.ERROR_CANCEL_FOLLOW_MESSAGE);
        }

        return R.wrap();
    }
}

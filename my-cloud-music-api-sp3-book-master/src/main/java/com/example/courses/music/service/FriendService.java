package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.courses.music.mapper.FriendMapper;
import com.example.courses.music.model.Friend;
import com.example.courses.music.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 好友服务
 */
@Service
public class FriendService {
    @Autowired
    private FriendMapper mapper;

    @Autowired
    private CommonService commonService;

    @Transactional
    public void create(Friend data) {
        //保存关系

        //因为使用了约束
        //所以保存失败
        //直接回抛出异常
        mapper.insert(data);

        //好友数加+1
        //缓存好友数的目的是
        //像这样的数据在我们项目中不需要那么精确
        //但还需要这样一个数据
        //所以就缓存到用户表
        //好处是不用每次统计好友表

        //如果是要求非常精确
        //例如：订单数
        //就不要缓存数量
        commonService.incrementCount("user", data.getFollowerId(), "followings_count");

        //对方粉丝数+1
        commonService.incrementCount("user", data.getFollowedId(), "followers_count");
    }

    public int deleteByFollowerIdAndFollowedId(String followerId, String followedId) {
        LambdaQueryWrapper<Friend> query = new LambdaQueryWrapper<>();
        query.eq(Friend::getFollowerId, followerId).eq(Friend::getFollowedId, followedId);

        int result = mapper.delete(query);
        if (result != Constant.RESULT_OK) {
            return result;
        }

        //好友数-1
        commonService.decrementCount("user", followerId, "followings_count");

        //对方粉丝数-1
        commonService.decrementCount("user", followedId, "followers_count");

        return result;
    }

    public Friend findByFollowerIdAndFollowedId(String followerId, String followedId) {
        LambdaQueryWrapper<Friend> query = new LambdaQueryWrapper<>();
        query.eq(Friend::getFollowerId, followerId).eq(Friend::getFollowedId, followedId);
        return mapper.selectOne(query);
    }
}
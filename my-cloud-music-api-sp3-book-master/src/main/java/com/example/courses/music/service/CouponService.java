package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.courses.music.mapper.CouponMapper;
import com.example.courses.music.model.Coupon;
import com.example.courses.music.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 优惠券服务
 */
@Service
public class CouponService {
    @Autowired
    private CouponMapper mapper;

    /**
     * 显示该用户所有优惠券，包括已经使用的，过期了
     * <p>
     * 真实项目中，可以需要用不同的列表显示不同状态的优惠券，但那就是传递不同的状态了
     * 这里就不再实现这么复杂了
     *
     * @param userId
     * @return
     */
    public List<Coupon> findAll(String userId) {
        return mapper.findAll(userId);
    }

    /**
     * 返回该金额下可用的优惠券列表
     *
     * @param userId
     * @return
     */
    public List<Coupon> findAllByAvailable(String userId, double price) {
        return mapper.findAllByAvailable(userId, price);
    }

    /**
     * 查询该用户是否有该活动的有效优惠券
     *
     * @return
     */
    public Coupon findByUserIdAndValid(String userId, String couponActivityId) {
        LambdaQueryWrapper<Coupon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Coupon::getUserId, userId)
                .eq(Coupon::getValid, Constant.VALUE0)
                .gt(Coupon::getExpire, new Date())
                .eq(Coupon::getCouponActivityId, couponActivityId);
        return mapper.selectOne(queryWrapper);
    }

    /**
     * 查询该用户的优惠券是否可用
     *
     * @return
     */
    public Coupon findByIdAndUserIdAndValid(String id, String userId) {
        return mapper.findByIdAndUserIdAndValid(id, userId);
    }

    /**
     * 创建
     *
     * @param data
     * @return
     */
    public int create(Coupon data) {
        return mapper.insert(data);
    }

    /**
     * 更改使用状态
     *
     * @param data
     */
    public void changeUsedStatus(String data, int status) {
        mapper.changeUsed(data, status);
    }
}
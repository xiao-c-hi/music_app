package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Coupon;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 优惠券映射
 */
@Repository
public interface CouponMapper extends BaseMapper<Coupon> {
    /**
     * 返回该金额下可用的优惠券列表
     *
     * @param userId
     * @param price
     * @return
     */
    List<Coupon> findAllByAvailable(String userId, double price);

    /**
     * 查询该用户的优惠券是否可用
     *
     * @param id
     * @param userId
     * @return
     */
    Coupon findByIdAndUserIdAndValid(String id, String userId);

    /**
     * 显示该用户所有优惠券，包括已经使用的，过期了
     *
     * @param userId
     * @return
     */
    List<Coupon> findAll(String userId);

    /**
     * 更改使用状态
     *
     * @param data
     * @param status
     */
    void changeUsed(String data, int status);
}

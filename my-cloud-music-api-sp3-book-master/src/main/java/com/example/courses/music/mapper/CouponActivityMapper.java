package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.CouponActivity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 优惠券活动映射
 */
@Repository
public interface CouponActivityMapper extends BaseMapper<CouponActivity> {
    /**
     * 查询所有没有失效活动
     * <p>
     * 还会查询该用户是否参加该活动了
     * <p>
     * 对于优惠券，如果还要查询该优惠券是否有效，只有有效才算参加了
     * 同一个优惠券活动，同时只能领取一张优惠券，使用了，或者失效了才能再次领取
     *
     * @param data
     * @return
     */
    List<CouponActivity> findAllByValid(String data);
}

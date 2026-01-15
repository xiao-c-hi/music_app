package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.courses.music.mapper.CouponActivityMapper;
import com.example.courses.music.model.CouponActivity;
import com.example.courses.music.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 优惠券活动服务
 */
@Service
public class CouponActivityService {
    @Autowired
    private CouponActivityMapper mapper;

    /**
     * 查询所有没有失效活动
     * 如果需要查询该商品的，就传递商品，我们这里就简单实现，所有商品可用
     *
     * @param userId 如果有用户id，就查询该用户是否参加了该活动（是否领取了该优惠券）
     * @return
     */
    public List<CouponActivity> findAllByValid(String userId) {
        if (StringUtils.isBlank(userId)) {
            //代码查询

            LambdaQueryWrapper<CouponActivity> queryWrapper = new LambdaQueryWrapper<>();

            //没有手动失效，并且结束日期大于当前日期
            queryWrapper.eq(CouponActivity::getValid, Constant.VALUE0)
                    .gt(CouponActivity::getEnd, new Date());

            queryWrapper.orderByDesc(CouponActivity::getCreatedAt);

            return mapper.selectList(queryWrapper);
        } else {
            //用xml查询，因为要多表关联查询，代码中也可以多表，但没有xml方便
            return mapper.findAllByValid(userId);
        }
    }

    /**
     * 添加
     *
     * @param data
     * @return
     */
    public int create(CouponActivity data) {
        return mapper.insert(data);
    }

    /**
     * 删除
     *
     * @return
     */
    public int delete(String data) {
        return mapper.deleteById(data);
    }
}
package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.model.*;
import com.example.courses.music.service.CouponActivityService;
import com.example.courses.music.service.CouponService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 优惠券活动控制器
 */
@RestController
@RequestMapping("/v1/activities/coupon")
public class CouponActivityController {
    @Autowired
    CouponActivityService service;

    @Autowired
    CouponService couponService;

    /**
     * 列表
     *
     * 目前主要是显示到商品详情
     * @return
     */
    @GetMapping
    public Object index() {
        StpUtil.checkLogin();

        return R.wrap(service.findAllByValid(StpUtil.getLoginIdAsString()));
    }
}

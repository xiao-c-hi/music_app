package com.example.courses.music.controller.v1.admin;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.model.CouponActivity;
import com.example.courses.music.service.CouponActivityService;
import com.example.courses.music.util.R;
import com.example.courses.music.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 管理员 优惠券活动控制器
 */
@RestController
@RequestMapping("/v1/admins/activities/coupon")
public class CouponActivityAdminController {

    @Autowired
    CouponActivityService service;

    /**
     * 创建
     * <p>
     * 我们这里实现的是，创建完成后，如果开始日期大于现在，就自动开始了，主要是为了简单
     * 比较我们这个课程不是专门讲解电商的
     * 真实项目中，需要有完整的审核流程，不然很有可能发现类似网上出现的5元买微波炉的情况
     * 可能直接干到破产😄
     *
     * @param data
     * @param bindingResult
     * @return
     */
    @PostMapping
    public Object create(@Valid @RequestBody CouponActivity data,
                         BindingResult bindingResult) {
        ValidatorUtil.checkParam(bindingResult);

        StpUtil.checkLogin();

        //设置用户id
        data.setUserId(StpUtil.getLoginIdAsString());

        service.create(data);

        return R.wrap(data.getId());
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Object destroy(@PathVariable String id) {
        StpUtil.checkLogin();

        //这里是根据id删除，所以该方法要限制，有删除活动权限的人才能访问
        service.delete(id);

        return R.wrap();
    }
}

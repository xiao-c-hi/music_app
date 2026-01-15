package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.Coupon;
import com.example.courses.music.service.CouponService;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.R;
import com.example.courses.music.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * 优惠券控制器
 */
@RestController
@RequestMapping("/v1/coupons")
public class CouponController {

    @Autowired
    CouponService service;

    /**
     * 显示优惠有效，并且未使用的优惠券
     *
     * 真实项目中，还会有一个列表显示已经使用的，失效的，这里就不再实现了
     * 因为就是根据不同字段查询而已
     * @return
     */
    @GetMapping
    public Object index() {
        StpUtil.checkLogin();

        return R.wrap(service.findAll(StpUtil.getLoginIdAsString()));
    }



    /**
     * 参加优惠券活动（领取优惠券）
     * @param data
     * @param bindingResult
     * @return
     */
    @PostMapping
    public Object create(@Valid
                       @RequestBody Coupon data,
                       BindingResult bindingResult){
        ValidatorUtil.checkParam(bindingResult);

        StpUtil.checkLogin();

        //先判断该用户是否参加了该活动，并且优惠券还在有效期
        Coupon old = service.findByUserIdAndValid(StpUtil.getLoginIdAsString(),data.getCouponActivityId());
        if (old!=null) {
            throw new CommonException(Constant.ERROR_HAVE_RECEIVED_COUPON,Constant.ERROR_HAVE_RECEIVED_COUPON_MESSAGE);
        }

        //过期时间，这里就写死领取后7天后过期
        DateTime expireDate = DateUtil.offsetDay(new Date(), 7);

        Coupon param = new Coupon(data.getCouponActivityId(), StpUtil.getLoginIdAsString(),expireDate.toJdkDate());

        service.create(param);

        return R.wrap(param.getId());
    }

}

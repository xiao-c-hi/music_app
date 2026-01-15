package com.example.courses.music.model;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.courses.music.util.Constant;

import java.util.Date;
import java.util.TimeZone;

/**
 * 优惠券模型
 * <p>
 * 这里默认优惠券领取后，7天内有效；真实项目中，可以指定失效日期，或者多少天后
 * 但这里复杂了，这里就不再实现了
 */
public class Coupon extends Common {
    /**
     * 是否有效
     * <p>
     * 0：有效
     * 10：失效，一般是后台手动失效（例如：优惠券被恶意领取了，就可以标记为失效），活动过期后不能变更
     * <p>
     * 提示：是否有效期内，根据开始，结束时间判断，不单独设计字段的目的是，逻辑上好实现
     */
    private byte valid;

    /**
     * 所属活动id
     */
    private String couponActivityId;

    /**
     * 关联的活动
     */
    @TableField(exist = false)
    private CouponActivity couponActivity;

    /**
     * 是否使用了
     * <p>
     * 0：没有使用
     * 10：使用了，在订单支付成功后，判断订单如果有关联优惠券，就设置已经使用了
     */
    private byte used;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 过期时间
     * <p>
     * 这里默认为领取7天后过期，真实项目更复杂，因为有领取多少天后过期，还有固定日期过期
     * 但不论哪种，根据活动上的规则，计算出过期日期，然后设置到这个字段就行了
     */
    private Date expire;

    public Coupon() {
    }

    public Coupon(String couponActivityId, String userId, Date expire) {
        this.couponActivityId = couponActivityId;
        this.userId = userId;
        this.expire = expire;
    }

    public byte getValid() {
        return valid;
    }

    public void setValid(byte valid) {
        this.valid = valid;
    }

    public String getCouponActivityId() {
        return couponActivityId;
    }

    public void setCouponActivityId(String couponActivityId) {
        this.couponActivityId = couponActivityId;
    }

    public byte getUsed() {
        return used;
    }

    public void setUsed(byte used) {
        this.used = used;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CouponActivity getCouponActivity() {
        return couponActivity;
    }

    public void setCouponActivity(CouponActivity couponActivity) {
        this.couponActivity = couponActivity;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    /**
     * 是否有效
     *
     * @return
     */
    @JsonIgnore
    public boolean isAvailable() {
        DateTime now = DateTime.now();

        DateTime dateTime = new DateTime(getExpire());

        //一定要设置时区，才会转为当前时区的时间
        dateTime.setTimeZone(TimeZone.getDefault());

        //没有手动标记为失效，并且没有过期
        return valid != Constant.VALUE10 && dateTime.isAfter(now);
    }
}

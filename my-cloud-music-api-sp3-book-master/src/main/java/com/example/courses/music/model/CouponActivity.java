package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * 优惠券活动模型
 * <p>
 * 这里就简单实现了，真实项目中活动可以说是电商中最复杂的逻辑之一了
 * 因为：活动又可以分很多，例如：
 * 优惠券活动
 * 有很多类型，例如：无门槛，满减，部分商品等
 * 如果是热门优惠券，那就类似秒杀，要高并发等
 * 还有优惠券叠加，有些又不能叠加
 * 还有打折类优惠券
 * 拼团
 * 邀请好友
 * 砍价
 * ...
 * <p>
 * 等等，总之电商中，活动要多复杂有多复杂
 */
public class CouponActivity extends Common {
    /**
     * 是否有效
     * <p>
     * 0：有效
     * 10：失效，一般是后台手动终止活动，活动正常结束后不能变更
     * <p>
     * 提示：活动状态，根据开始，结束时间判断，不单独设计字段的目的是，逻辑上好实现
     * 例如：如果添加一个字段表示活动状态（未开始，进行中，已结束），那到点了还需更改该字段
     * 很难做到准确；解决方法就是客户端判断（如有必要，客户端可以设计为倒计时），调用服务端参加活动时服务端也判断
     */
    private int valid;

    /**
     * 活动标题
     * <p>
     * 例如：满100减10元
     */
    @NotBlank(message = "标题不能为空")
    @Length(min = 1, max = 30, message = "标题长度必须为1~30位")
    private String title;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private Timestamp start;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private Timestamp end;

    /**
     * 满多少元才能使用
     * <p>
     * 0：表示无门槛
     */
    @TableField("`condition`")
    private double condition;

    /**
     * 优惠多少
     */
    @DecimalMin(value = "0.01", message = "优惠金额必须大于0.01元")
    private double price;

    /**
     * 描述
     * <p>
     * 真实项目中，可能还需要些一些使用限制等
     */
    private String detail;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 该用户参加了活动，并且还有有效的优惠券id
     * 可以关键到Coupon对象
     */
    @TableField(exist = false)
    private String couponId;

    @TableField(exist = false)
    private String couponCreateAt;

    @TableField(exist = false)
    private Coupon coupon;

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public double getCondition() {
        return condition;
    }

    public void setCondition(double condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponCreateAt() {
        return couponCreateAt;
    }

    public void setCouponCreateAt(String couponCreateAt) {
        this.couponCreateAt = couponCreateAt;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}

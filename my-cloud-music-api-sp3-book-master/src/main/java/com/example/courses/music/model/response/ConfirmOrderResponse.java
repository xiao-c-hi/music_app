package com.example.courses.music.model.response;

import com.example.courses.music.model.Address;
import com.example.courses.music.model.Base;
import com.example.courses.music.model.Cart;
import com.example.courses.music.model.Coupon;

import java.util.List;

/**
 * 确认订单响应模型
 */
public class ConfirmOrderResponse extends Base {
    /**
     * 购物车item
     * <p>
     * 如果是在订单详情购买一个商品，那列表只要一个，并且cart没有id
     */
    private List<Cart> carts;

    /**
     * 商品总价
     */
    private int totalPrice;

    /**
     * 还需支付价格
     * <p>
     * 减去优惠券+邮费 后的价格
     */
    private int price;

    /**
     * 用户传递地址id后，返回的地址对象
     */
    private Address address;

    /**
     * 返回当前用户可用的优惠券，这里就简单判断，只要金额满足就返回
     * 真实项目中复杂多了，前面有说过，有不限金额，有限制金额，限制商品，限制日期等等
     * <p>
     * 同时真实项目中，可能还会返回当前商品不能使用的优惠券，只是在客户端显示灰色，不用用户选择
     */
    private List<Coupon> coupons;

    /**
     * 使用的优惠券
     */
    private Coupon coupon;

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}

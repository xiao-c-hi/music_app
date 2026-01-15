package com.example.courses.music.model.request;


import com.example.courses.music.model.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 创建订单参数
 * 可以复用Order
 */
public class OrderRequest extends Base {
    /**
     * 商品id
     */
    private String productId;

    /**
     * 从购物车，选中item购买
     */
    private List<String> carts;

    /**
     * 服务端缓存当前商品对象
     */
    private Product product;

    /**
     * 订单来源
     * <p>
     * 取值参见常量文件
     */
    @NotNull(message = "订单来源不能为空")
    private Integer source;

    /**
     * 服务端缓存数据用户
     * 值是根据carts列表的id查询到的对象
     */
    private List<Cart> cartList;

    /**
     * 地址id
     * <p>
     * 用来客户端传递地址id
     */
    private String addressId;

    /**
     * 地址
     * <p>
     * 服务端缓存地址对象
     */
    private Address address;

    /**
     * 优惠券id
     * <p>
     * 用来客户端传递id
     */
    private String couponId;

    /**
     * 优惠券对象
     * <p>
     * 服务端缓存地址对象
     */
    private Coupon coupon;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<String> getCarts() {
        return carts;
    }

    public void setCarts(List<String> carts) {
        this.carts = carts;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}

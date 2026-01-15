package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.JSONUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 订单模型
 */
@TableName(value = "`order`")
public class Order extends Common {
    /**
     * 订单状态，取值为Constant中 订单状态 内的值
     */
    private int status;

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
     * 订单来源
     * <p>
     * 取值参见常量文件
     */
    @NotNull(message = "订单来源不能为空")
    private Integer source;

    /**
     * 支付来源
     * <p>
     * 取值参见常量文件
     */
    private Integer origin;

    /**
     * 支付渠道
     * <p>
     * 取值参见常量文件
     */
    private Integer channel;

    /**
     * 订单号
     */
    private String number;

    /**
     * 第三方订单号
     * <p>
     * 如果是支付宝支付，就是支付宝那边的订单号
     * 如果是微信支付，就是微信那个的订单号
     */
    private String other;

    /**
     * 商品信息
     * <p>
     * 购物车模型，就算是直接购买也是购物车模型
     * <p>
     * 真实项目中要支持一个订单购买多个商品，可以用单独的表保存，就是多对多关系
     * 在前面歌单实现了，这里就用这种方法实现
     * 同时大部分商品应该保存商品快照，而不是引用商品，防止后面出现纠纷无法维权
     * 更完整的功能在商业级商城项目中讲解
     */
    @JsonIgnore
    private String product;

    /**
     * 商品信息列表，购物车列表模型
     */
    @TableField(exist = false)
    private List<Cart> products;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户对象
     */
    @TableField(exist = false)
    private User user;

    /**
     * 地址，json字符串，单独保存一份目的是
     * 真实项目中，买了一个商品后，所关联的地址可以删除，如果删除了原来买的商品还有地址
     * 所以要单独保存
     */
    private String addr;

    /**
     * 优惠券，json字符串
     */
    private String coup;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public List<Cart> getProducts() {
        return products;
    }

    public void setProducts(List<Cart> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @JsonIgnore
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Address getAddress() {
        if (StringUtils.isNotBlank(getAddr())) {
            return JSONUtil.parse(getAddr(), Address.class);
        }
        return null;
    }

    @JsonIgnore
    public String getCoup() {
        return coup;
    }

    public void setCoup(String coup) {
        this.coup = coup;
    }

    public Coupon getCoupon() {
        if (StringUtils.isNotBlank(getCoup())) {
            return JSONUtil.parse(getCoup(), Coupon.class);
        }
        return null;
    }

    /**
     * 是否已经支付成功
     *
     * @return
     */
    @JsonIgnore
    public boolean isPaid() {
        return status >= Constant.WAIT_SHIPPED;
    }

    /**
     * 是否待支付
     *
     * @return
     */
    @JsonIgnore
    public boolean isWaitPay() {
        return status == Constant.WAIT_PAY;
    }

    /**
     * 将cart字段的值，解析为对象
     */
    public void parseCart() {
        List<Cart> carts = JSONUtil.parse(getProduct(), new TypeReference<List<Cart>>() {
        });
        setProducts(carts);
    }
}

package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 商品
 */
public class Product extends Common {
    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    @Length(min = 1, max = 100, message = "标题长度必须为1~100位")
    private String title;

    /**
     * 封面
     * <p>
     * 多张图片地址以英文逗号分割存储
     * 也可以像动态那样单独存储，这里之所以讲解这种方式
     * 是因为单独存储动态那边讲解了，同时像以逗号分割这种方式
     * 在真实项目中也挺常用
     */
    @NotBlank(message = "封面不能为空")
    private String icon;

    /**
     * 价格
     */
    @NotNull(message = "价格不能为空")
    private int price;

    /**
     * 亮点
     * <p>
     * 真实项目中，如果有特别需求，也可以实现为列表
     * 我们这里主要讲解商城核心逻辑，所以这里就简单实现了
     */
    @NotBlank(message = "亮点不能为空")
    private String highlight;

    /**
     * 详情
     * <p>
     * HTML格式化富文本
     */
    @NotBlank(message = "亮点不能为空")
    private String detail;

    /**
     * 用户id
     */
    private String userId;

    @TableField(exist = false)
    private User user;

    /**
     * 已经付款的购买人数
     * <p>
     * 也可以像点赞数那样缓存数量
     * 但这里我们希望数据更加真实，所以就查询数据库
     * 当然在真实项目中，大部分时候还是要缓存数量，直接查看太耗性能了
     */
    @TableField(exist = false)
    private long ordersCount;

    /**
     * 优惠券活动
     * <p>
     * 如果还有活动，推荐放到单独的字段，方便不管理，而不是和优惠券活动混到一张表
     * 因为不同的活动直接差不多
     * <p>
     * 这里返回的活动是，所有有效的优惠券活动
     */
    @TableField(exist = false)
    private List<CouponActivity> couponActivities;

    /**
     * 评论列表
     */
    private transient List<Comment> comments;

    /**
     * 推荐商品
     */
    private transient List<Product> recommends;

    private String sku;
    private String spec;

    @TableField(exist = false)
    private List<Stock> skus;

    @TableField(exist = false)
    private List<Spec> specs;


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(long ordersCount) {
        this.ordersCount = ordersCount;
    }

    public List<CouponActivity> getCouponActivities() {
        return couponActivities;
    }

    public void setCouponActivities(List<CouponActivity> couponActivities) {
        this.couponActivities = couponActivities;
    }

    public String[] getIcons() {
        return icon.split(",");
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getRecommends() {
        return recommends;
    }

    public void setRecommends(List<Product> recommends) {
        this.recommends = recommends;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public List<Stock> getSkus() {
        return skus;
    }

    public void setSkus(List<Stock> skus) {
        this.skus = skus;
    }

    public List<Spec> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Spec> specs) {
        this.specs = specs;
    }
}

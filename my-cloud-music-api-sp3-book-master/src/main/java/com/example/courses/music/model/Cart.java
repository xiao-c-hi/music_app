package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;

import javax.validation.constraints.NotNull;

/**
 * 购物车item
 */
public class Cart extends Common {
    /**
     * 商品
     */
    @TableField(exist = false)
    private Product product;

    /**
     * 数量
     */
    private int count = 1;

    /**
     * 添加到购物车时，使用
     */
    @NotNull(message = "商品不能为空")
    private String productId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 根据商品创建，数量为1
     *
     * @param data
     * @return
     */
    public static Cart create(Product data) {
        Cart cart = new Cart();
        cart.setProduct(data);
        return cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

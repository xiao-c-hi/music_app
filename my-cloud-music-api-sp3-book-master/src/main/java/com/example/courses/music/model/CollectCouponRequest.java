package com.example.courses.music.model;

import javax.validation.constraints.NotNull;

/**
 * 领取优惠券请求参数
 */
public class CollectCouponRequest extends Base {
    /**
     * 活动id
     */
    @NotNull(message = "活动不能为空")
    private String id;

    /**
     * 来源
     * <p>
     * 和订单来源取值一样
     * 真实项目中，可能需要统计，所以传递来源
     */
    @NotNull(message = "来源不能为空")
    private Integer source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}

package com.example.courses.music.model.response;

import com.example.courses.music.model.Base;

import java.util.Map;

/**
 * 客户端支付参数
 */
public class PayResponse extends Base {
    /**
     * 微信支付参数
     */
    private Map<String, String> wechatPay;

    /**
     * 支付渠道
     */
    private int channel;

    /**
     * 支付宝支付
     */
    private String pay;

    /**
     * 构造方法
     *
     * @param channel
     * @param pay
     */
    public PayResponse(int channel, String pay) {
        this.channel = channel;
        this.pay = pay;
    }

    /**
     * 构造方法
     *
     * @param channel
     * @param data
     */
    public PayResponse(Integer channel, Map<String, String> data) {
        this.channel = channel;
        this.wechatPay = data;
    }

    public Map<String, String> getWechatPay() {
        return wechatPay;
    }

    public void setWechatPay(Map<String, String> wechatPay) {
        this.wechatPay = wechatPay;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
}

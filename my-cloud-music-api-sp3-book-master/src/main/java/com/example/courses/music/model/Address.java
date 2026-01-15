package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.courses.music.util.Constant;

/**
 * 收货地址
 */
public class Address extends Common {
    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 省
     */
    private String province;

    /**
     * 省编码
     * <p>
     * 例如：四川省为510000
     */
    private String provinceCode;

    /**
     * 市
     */
    private String city;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区
     */
    private String area;

    /**
     * 区编码
     */
    private String areaCode;

    /**
     * 剩余的地址部分
     */
    private String detail;

    /**
     * 是否是默认地址
     * <p>
     * 0：不是默认
     * 10：默认
     */
    @JsonProperty("default")
    @TableField("`default`")
    private byte defaultAddress;

    /**
     * 用户id
     */
    private String userId;

    public Address() {
    }

    public Address(String name, String telephone, String province, String provinceCode, String city, String cityCode, String area, String areaCode, String detail) {
        this.name = name;
        this.telephone = telephone;
        this.province = province;
        this.provinceCode = provinceCode;
        this.city = city;
        this.cityCode = cityCode;
        this.area = area;
        this.areaCode = areaCode;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public byte getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(byte defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 是否是默认地址
     *
     * @return
     */
    public boolean isDefault() {
        return defaultAddress == Constant.VALUE10;
    }
}

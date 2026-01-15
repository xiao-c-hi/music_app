package com.example.courses.music.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.courses.music.util.Constant;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class User extends Common {
    /**
     * 昵称，不能为空
     * <p>
     * 长度为1~30
     *
     * @NotEmpty :不能为null，且Size>0
     * @NotNull:不能为null，但可以为empty,没有Size的约束
     * @NotBlank:只用于String,不能为null且trim()之后size>0
     */
    @NotBlank(message = "昵称不能为空")
    @Length(min = 1, max = 30, message = "昵称长度必须为1~30位")
    private String nickname;

    /**
     * 头像
     */
    private String icon;

    /**
     * 描述
     */
    private String detail;

    /**
     * 性别
     * <p>
     * 不能为空
     * 默认为0：保密；1：男；2：女
     */
    private Integer gender;

    /**
     * 出生日志
     * <p>
     * 格式为yyyy-MM-dd格式
     * 真实项目中建议使用日期格式
     */
    private String birthday;

    /**
     * 邮箱
     */
    @Pattern(regexp = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$", message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号
     * <p>
     * 不可以为空
     * 手机号正则表达式
     * 移动：134 135 136 137 138 139 147 150 151 152 157 158 159 178 182 183 184 187 188 198
     * 联通：130 131 132 145 155 156 166 171 175 176 185 186
     * 电信：133 149 153 173 177 180 181 189 199
     * 虚拟运营商: 170
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 省
     */
    private String province;

    /**
     * 省编码
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
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 15, message = "密码长度必须为6~15位")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 邮箱确认token
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String emailConfirm;

    /**
     * 邮箱确认时间
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp emailConfirmAt;

    /**
     * 邮箱确认链接发送时间
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Timestamp emailConfirmSendAt;

    /**
     * 验证码字段
     * <p>
     * md5签名
     * <p>
     * TableField:为空也更新
     * 实现的效果就是，如果原来数据有值，在代码中设置为null，更新后数据库该字段就为空
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @TableField(exist = false)
    private String code;

    /**
     * 第三方登录QQid加密后值，aes算法加密
     */
    @TableField(fill = FieldFill.UPDATE)
    private String qqId;

    /**
     * 第三方登录微信id加密后值，aes算法加密
     */
    @TableField(fill = FieldFill.UPDATE)
    private String wechatId;

    /**
     * 角色
     * 如果没有值，就是user，代表普通用户
     */
    private String role;

    /**
     * 角色列表，例如：[admin,analysis]，主要是接收前端输入
     */
    private transient List<String> roleList;

    /**
     * 角色列表，客户端显示，会返回角色名称
     */
    private transient List<Role> roles;

    /**
     * 状态
     * 1：正常；0：禁用
     */
    private Boolean status;

    /**
     * 内部备注，后台使用
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String note;

    /**
     * 直播间id
     * 该自动会在注册时，自动生成，当然也可以在代码中查询最近注册用户的room_id，然后手动加1，在设置到新用户字段
     */
    private Long roomId;

    /**
     * 我的关注的人（好友）
     */
    private long followingsCount;

    /**
     * 关注我的人（粉丝）
     */
    private long followersCount;

    /**
     * 有值就表示关注了
     */
    @TableField(exist = false)
    private String following;

    /**
     * 标签，这里只是返回模拟数据方便前端使用
     * 因为在歌单实现了更复杂的标签模型，这里就不在实现了
     */
    @TableField(exist = false)
    private List<String> tags;

    /**
     * 是否禁用了
     *
     * @return
     */
    @JsonIgnore
    public boolean isDisable() {
        return !status;
    }

    /**
     * 邮箱是否已经验证了
     * <p>
     * 邮箱确认时间存在表示验证了
     *
     * @return
     */
    @JsonIgnore
    public boolean isEmailVerification() {
        return emailConfirmAt != null;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailConfirm() {
        return emailConfirm;
    }

    public void setEmailConfirm(String emailConfirm) {
        this.emailConfirm = emailConfirm;
    }

    public Timestamp getEmailConfirmAt() {
        return emailConfirmAt;
    }

    public void setEmailConfirmAt(Timestamp emailConfirmAt) {
        this.emailConfirmAt = emailConfirmAt;
    }

    public Timestamp getEmailConfirmSendAt() {
        return emailConfirmSendAt;
    }

    public void setEmailConfirmSendAt(Timestamp emailConfirmSendAt) {
        this.emailConfirmSendAt = emailConfirmSendAt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public long getFollowingsCount() {
        return followingsCount;
    }

    public void setFollowingsCount(long followingsCount) {
        this.followingsCount = followingsCount;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(long followersCount) {
        this.followersCount = followersCount;
    }

    public List<String> getTags() {
        if (tags == null) {
            tags=Arrays.asList(
                    "很有想法的",
                    "Spring Boot",
                    "川妹子",
                    "iOS",
                    "大长腿",
                    "Android",
                    "爱学啊",
                    "vue3"
            );
        }
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getRoleList() {
        if (roleList == null) {
            if (StringUtils.isBlank(role)) {
//                //默认用户角色
//                return Arrays.asList("user");
                return null;
            }

            return Arrays.asList(StringUtils.splitByWholeSeparator(role, Constant.SEPARATOR_COMMA));
        }
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * 将角色列表字符串拼接为逗号分隔的字符串
     * @return
     */
    public String getRoleListString() {
        if (CollectionUtils.isNotEmpty(roleList)) {
            return StringUtils.join(roleList, Constant.SEPARATOR_COMMA);
        }
        return null;
    }
}

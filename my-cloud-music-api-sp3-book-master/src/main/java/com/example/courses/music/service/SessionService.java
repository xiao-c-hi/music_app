package com.example.courses.music.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.mapper.SessionMapper;
import com.example.courses.music.model.LoginRequest;
import com.example.courses.music.model.Session;
import com.example.courses.music.model.User;
import com.example.courses.music.util.AESUtil;
import com.example.courses.music.util.BCryptUtil;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.ValidatorUtil;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.service.WxOAuth2Service;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * session服务
 */
@Service
public class SessionService {
    private static Logger log = LoggerFactory.getLogger(SessionService.class);

    private UserService userService;

    private ChatService chatService;

    private CodeService codeService;

    private SessionMapper mapper;

    private PushService pushService;

    @Autowired
    WxOAuth2Service wxOAuth2Service;

    //spring boot中推荐使用构造器（字段强制有值），还有set方法注入（字段可选）
    @Autowired
    public SessionService(UserService userService, ChatService chatService, CodeService codeService, SessionMapper mapper, PushService pushService) {
        this.userService = userService;
        this.chatService = chatService;
        this.codeService = codeService;
        this.mapper = mapper;
        this.pushService = pushService;
    }

    /**
     * 登录
     * <p>
     * 登录顺序：用户名（邮箱，手机号）和密码，用户名（邮箱，手机号）和验证码，qq，微信
     *
     * @param data
     * @param ip
     * @return
     */
    public Session create(LoginRequest data, String ip) {
        //查找用户
        User user = null;

        if ((StringUtils.isNotBlank(data.getEmail()) || StringUtils.isNotBlank(data.getPhone()))
                && StringUtils.isNotBlank(data.getPassword())) {
            //用户名（邮箱，手机号）和密码

            user = userService.findByEmailOrPhone(data.getEmail(), data.getPhone());

            //检查用户
            ValidatorUtil.checkUser(user);

            //判断密码
            if (!BCryptUtil.matchEncode(data.getPassword(), user.getPassword())) {
                throw new CommonException(Constant.ERROR_USERNAME_OR_PASSWORD, Constant.ERROR_USERNAME_OR_PASSWORD_MESSAGE);
            }
        } else if ((StringUtils.isNotBlank(data.getEmail()) || StringUtils.isNotBlank(data.getPhone()))
                && StringUtils.isNotBlank(data.getCode())) {
            //用户名（邮箱，手机号）和验证码

            String target;
            if (StringUtils.isNotBlank(data.getEmail())) {
                target = data.getEmail();
            } else {
                target = data.getPhone();
            }

            //检查验证码
            codeService.checkAndDelete(target, data.getCode());

            user = userService.findByEmailOrPhone(data.getEmail(), data.getPhone());
            if (user == null) {
                //用户还没有注册

                //注册
                user = new User();
                if (StringUtils.isNotBlank(data.getEmail())) {
                    user.setEmail(data.getEmail());
                } else {
                    user.setPhone(data.getPhone());
                }

                userService.create(user);
            }
        } else if (StringUtils.isNotBlank(data.getQqId())
                || StringUtils.isNotBlank(data.getWechatId())) {
            //第三方登录，qq，微信

            String qqIdDigest = null;
            String wechatIdDigest = null;
            if (StringUtils.isNotBlank(data.getQqId())) {
                qqIdDigest = AESUtil.encrypt(data.getQqId());
            }

            if (StringUtils.isNotBlank(data.getWechatId())) {
                wechatIdDigest = AESUtil.encrypt(data.getWechatId());
            }

            //查找用户
            user = userService.findByQQIdOrWechatId(qqIdDigest, wechatIdDigest);

            if (user == null) {
                user = new User();
                //注册
                user.setNickname(data.getNickname());
                user.setIcon(data.getIcon());
                user.setQqId(qqIdDigest);
                user.setWechatId(wechatIdDigest);

                userService.create(user);
            }else{
                if (StringUtils.isNotBlank(data.getNickname())) {
                    user.setNickname(data.getNickname());
                }

                if (StringUtils.isNotBlank(data.getIcon())) {
                    user.setIcon(data.getIcon());
                }
                userService.update(user);
            }
        }else {
            //参数错误
            throw new ArgumentException();
        }

        return saveLoginStatus(user, data, ip);
    }

    private Session saveLoginStatus(User user, LoginRequest loginRequest, String ip) {
        //标记已经登录
        StpUtil.login(user.getId(), String.valueOf(loginRequest.getPlatform()));

        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        log.info("create token info {}", tokenInfo);

        //生成聊天token，并返回给客户端，客户端就可以登录聊天sdk了
        String chatToken = chatService.createOrUpdateChatUser(user);
        Session session = new Session(user.getId(),
                tokenInfo.getTokenValue(),
                loginRequest.getPlatform(),
                loginRequest.getDevice(),
                loginRequest.getPush(),
                chatToken,
                ip,
                user.getRole());

        //查询原来有没有该类型设备的登录信息
        Session oldSession = findByUserIdAndPlatform(user.getId(), loginRequest.getPlatform());
        if (oldSession != null && StringUtils.isNotBlank(oldSession.getPush()) && !oldSession.getPush().equals(loginRequest.getPush())) {
            //这次登陆的push不等于上一次push
            //表示上一个设备要强制退出
            //发送一条客户端退出通知
            pushService.sendLogout(oldSession.getPush());
        }

        //保存或更新
        createOrUpdate(session);

        return session;
    }

    /**
     * @param userId
     * @param platform
     * @return
     */
    private Session findByUserIdAndPlatform(String userId, byte platform) {
        LambdaQueryWrapper<Session> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Session::getUserId, userId).eq(Session::getPlatform, platform);
        return mapper.selectOne(queryWrapper);
    }

    /**
     * 保存或更新
     *
     * @param data
     */
    private void createOrUpdate(Session data) {
        //因为可能是替换，所以必须其他计算id
        //id=md5(userId+platform).hashCode()
        String id = Session.generateId(data.getUserId(), data.getPlatform());
        data.setId(id);
        mapper.replace(data);
    }

    /**
     * 删除
     *
     * @param userId
     * @param platform
     */
    public void delete(String userId, String platform) {
        mapper.deleteById(Session.generateId(userId, Byte.parseByte(platform)));
    }

    /**
     * 微信登录
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317853&lang=zh_CN
     *
     * @param data
     * @param ip
     * @return
     */
    public Session wechatLogin(LoginRequest data, String ip) {
        try {
            WxOAuth2AccessToken r = wxOAuth2Service.getAccessToken(data.getWechatId());

            //获取用户信息
            //https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317853&lang=zh_CN
            WxOAuth2UserInfo userInfo = wxOAuth2Service.getUserInfo(r, "zh_CN");
//            log.info("testOauth2getAccessToken {}", r.getAccessToken());
//            log.info("userInfo {} {} {} {}", userInfo.getOpenid(),userInfo.getUnionId(),userInfo.getNickname(),userInfo.getHeadImgUrl());

            //查找用户
            User user = userService.findByWechatId(userInfo.getUnionId());

            if (user == null) {
                user = new User();

                //注册
                user.setNickname(userInfo.getNickname());
                user.setIcon(userInfo.getHeadImgUrl());
                user.setWechatId(userInfo.getUnionId());

                userService.create(user);
            }else{
                user.setNickname(userInfo.getNickname());
                user.setIcon(userInfo.getHeadImgUrl());

                //更新
                //这样就能再次获取用户最新昵称，头像
                userService.update(user);
            }

            return saveLoginStatus(user, data, ip);
        } catch (WxErrorException e) {
            throw new ArgumentException();
        }
    }
}
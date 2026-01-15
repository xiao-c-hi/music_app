package com.example.courses.music.service;

import com.example.courses.music.BaseTests;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.service.WxOAuth2Service;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 微信开放平台服务测试
 */
public class WechatOpenServiceTests extends BaseTests {
    private static Logger log = LoggerFactory.getLogger(WechatOpenServiceTests.class);

    @Autowired
    WxOAuth2Service wxOAuth2Service;

    /**
     * 获取微信开放平台AccessToken
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317851&token=&lang=zh_CN
     * @throws WxErrorException
     */
    @Test
    public void testOauth2getAccessToken() throws WxErrorException {
        WxOAuth2AccessToken r = wxOAuth2Service.getAccessToken("0417Oj0w3WjwBZ2wnz1w3Jw9zc07Oj0R");

        //获取用户信息
        //https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317853&lang=zh_CN
        WxOAuth2UserInfo userInfo = wxOAuth2Service.getUserInfo(r, "zh_CN");
        log.info("testOauth2getAccessToken {}", r.getAccessToken());
        log.info("userInfo {} {} {} {}", userInfo.getOpenid(),userInfo.getUnionId(),userInfo.getNickname(),userInfo.getHeadImgUrl());
    }
}

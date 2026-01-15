package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.model.LoginRequest;
import com.example.courses.music.service.SessionService;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.IPUtil;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信登录，退出控制器
 */
@RestController
@RequestMapping("/v1/sessions/wechat")
public class SessionWechatController {
    @Autowired
    private SessionService service;


    /**
     * 用户名和密码，第三方登录登录，手机验证码登录
     *
     * 可以用不同的接口实现，但放一起好处就是只有一个接口
     * 坏处就是需要手动验证参数
     *
     * @param data
     * @return
     */
    @PostMapping
    public @ResponseBody
    Object create(@RequestBody LoginRequest data,
                  HttpServletRequest request) {
        //校验参数
        if (data.getPlatform() != Constant.ANDROID &&
                data.getPlatform() != Constant.IOS &&
                data.getPlatform() != Constant.WEB
        ) {
            throw new ArgumentException();
        }

        //必须有设备名称
        if (StringUtils.isBlank(data.getDevice())) {
            throw new ArgumentException();
        }

        //必须有微信id
        if (StringUtils.isBlank(data.getWechatId())) {
            throw new ArgumentException();
        }

        return R.wrap(service.wechatLogin(data, IPUtil.getIP(request)));
    }

    /**
     * 退出
     * 退出之所以好调用服务端的目的是清除token，防止泄漏
     *
     * @return
     */
    @DeleteMapping("/{id}")
    public @ResponseBody
    Object destroy(@PathVariable String id) {
        StpUtil.checkLogin();

        service.delete(StpUtil.getLoginIdAsString(), StpUtil.getLoginType());

        StpUtil.logout();

        return R.wrap();
    }
}

package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.User;
import com.example.courses.music.service.MailService;
import com.example.courses.music.service.UserService;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.R;
import com.example.courses.music.util.SHAUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 邮箱确认控制器
 */
@Controller
public class EmailConfirmController {
    private UserService userService;
    private MailService mailService;

    public EmailConfirmController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping("/v1/mails/request_verification")
    @ResponseBody
    public Object requestEmailVerification() {
        StpUtil.checkLogin();

        User data = userService.find(StpUtil.getLoginIdAsString());
        if (data.isEmailVerification()) {
            //邮箱已经验证了
            throw new CommonException(Constant.ERROR_EMAIL_ALREADY_CONFIRM, Constant.ERROR_EMAIL_ALREADY_CONFIRM_MESSAGE);
        }

        //发送邮件
        String r = mailService.requestEmailVerification(data);
        data.setEmailConfirm(r);
        userService.update(data);

        return R.wrap();
    }

    /**
     * 验证结果界面
     *
     * @param id
     * @return
     */
    @GetMapping("/mails/{id}/confirm_verification")
    public Object confirmVerification(@PathVariable("id") String id, ModelMap map) {
        String r = SHAUtil.sha256(id);

        User data = userService.findByEmailConfirm(r);

        String message = null;
        if (data == null) {
            //没有找到用户

            //表示链接地址无效
            message = "链接错误，请重新发送";
        } else if ("1".equals(data.getEmailConfirm())) {
            //已经验证了
            message = "已经验证了，请勿重复操作";
        } else {
            //验证成功
            message = "验证成功";
            data.setEmailConfirm("1");
            userService.update(data);
        }

        //设置属性
        //在模板中使用
        map.addAttribute("message", message);

        return "confirm_verification";
    }
}

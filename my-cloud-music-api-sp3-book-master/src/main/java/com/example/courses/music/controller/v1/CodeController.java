package com.example.courses.music.controller.v1;

import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.model.CodeRequest;
import com.example.courses.music.service.CodeService;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码控制器
 */
@RestController
@RequestMapping("/v1/codes")
public class CodeController {
    private CodeService service;

    @Autowired
    public CodeController(CodeService service) {
        this.service = service;
    }

    /**
     * 发送验证码
     *
     * @param param
     * @return
     */
    @PostMapping
    public Object create(@RequestBody CodeRequest param, HttpServletRequest request) {
        String target = null;
        int style=Constant.VALUE0;
        if (org.apache.commons.lang3.StringUtils.isNoneBlank(param.getEmail())) {
            //邮件验证码
            target = param.getEmail();
            style =Constant.VALUE0;
        }else if (StringUtils.isNoneBlank(param.getPhone())) {
            //短信验证码
            target = param.getPhone();
            style =Constant.VALUE10;
        }else {
            throw new ArgumentException();
        }

        service.checkIfSend(target);

        return R.wrap(service.create(style, target));
    }

    /**
     * 校验验证码
     * <p>
     * 只是校验是否正确，不会清除验证码
     * 一般用在客户端校验是否正确，如果正确在做下一步操作
     * <p>
     * 例如：通过验证码找回密码，输入验证码，调用本接口校验，通过后跳转到设置密码界面
     * 输入新密码后，还需要带着验证码，再次校验并清除验证码
     *
     * @return
     */
    @PostMapping("/check")
    public Object check(@RequestBody CodeRequest param) {
        String target = null;
        if (StringUtils.isNoneBlank(param.getEmail())) {
            //邮件验证码
            target = param.getEmail();
        }else if (StringUtils.isNoneBlank(param.getPhone())) {
            //短信验证码
            target = param.getPhone();
        }else {
            throw new ArgumentException();
        }

        service.check(target, param.getCode());

        return R.wrap();
    }
}


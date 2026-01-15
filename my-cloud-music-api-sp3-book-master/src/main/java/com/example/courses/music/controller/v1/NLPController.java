package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.model.request.NLPAddressRequest;
import com.example.courses.music.service.BaiduNLPService;
import com.example.courses.music.util.R;
import com.example.courses.music.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 自然语言处理控制器
 * <p>
 * 目前主要是使用百度NLP服务，实现收货地址文本解析
 */
@RestController
@RequestMapping("/v1/nlp")
public class NLPController {

    @Autowired
    BaiduNLPService service;

    /**
     * 收货地址文本解析
     *
     * @param data
     * @return
     */
    @PostMapping("/address")
    public Object address(@Valid @RequestBody NLPAddressRequest data, BindingResult bindingResult) {
        ValidatorUtil.checkParam(bindingResult);

        StpUtil.checkLogin();

        return R.wrap(service.address(data));
    }
}

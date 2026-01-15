package com.example.courses.music.controller.v3;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.annotation.DecryptRequestBody;
import com.example.courses.music.model.Address;
import com.example.courses.music.service.AddressService;
import com.example.courses.music.util.R;
import com.example.courses.music.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 收货地址控制器
 */
@RestController
@RequestMapping("/v3/addresses")
public class AddressControllerV3 {

    @Autowired
    AddressService service;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping
    public Object index() {
        StpUtil.checkLogin();

        return R.wrap(service.findAllByUserId(StpUtil.getLoginIdAsString()),true);
    }


    /**
     * 创建
     *
     * @param data
     * @param bindingResult
     * @return
     */
    @DecryptRequestBody
    @PostMapping
    public Object create(@Valid @RequestBody Address data,
                         BindingResult bindingResult) {
        ValidatorUtil.checkParam(bindingResult);

        StpUtil.checkLogin();

        //设置用户id
        data.setUserId(StpUtil.getLoginIdAsString());

        service.create(data);

        return R.wrap(data.getId());
    }

}

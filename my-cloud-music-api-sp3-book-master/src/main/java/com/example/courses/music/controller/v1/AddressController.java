package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
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
@RequestMapping("/v1/addresses")
public class AddressController {

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

        return R.wrap(service.findAllByUserId(StpUtil.getLoginIdAsString()));
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    public Object show(@PathVariable("id") String id) {
        StpUtil.checkLogin();

        Address data = service.findByIdAndUserId(id,StpUtil.getLoginIdAsString());

        return R.wrap(data);
    }

    /**
     * 创建
     *
     * @param data
     * @param bindingResult
     * @return
     */
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

    /**
     * 更新
     *
     * @param id
     * @param data
     * @return
     */
    @PatchMapping("/{id}")
    public Object update(@PathVariable String id, @RequestBody Address data) {
        StpUtil.checkLogin();

        //设置id
        data.setId(id);

        //设置用户id
        data.setUserId(StpUtil.getLoginIdAsString());

        //更新
        service.update(data);

        return R.wrap(data.getId());
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Object destroy(@PathVariable String id) {
        StpUtil.checkLogin();

        service.deleteByIdAndUserId(id,StpUtil.getLoginIdAsString());

        return R.wrap();
    }

}

package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.courses.music.model.Sheet;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.mapper.CommonMapper;
import com.example.courses.music.service.SheetService;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.R;
import com.example.courses.music.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 歌单控制器
 */
@RestController
@RequestMapping("/v1/sheets")
public class SheetController {

    @Autowired
    SheetService service;

    @Autowired
    private CommonMapper commonMapper;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping
    public Object index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size
    ) {

        IPage<Sheet> result = service.findAll(page, size);

        return R.wrap(result);
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    public Object show(@PathVariable("id") String id) {
        //点击数+1
        //也可以保存到redis中，隔一段事件将点击数同步到数据库
        commonMapper.incrementCount("sheet", id, "clicks_count");

        Sheet data = service.findDetail(id);

        return R.wrap(data);
    }

    /**
     * 创建歌单
     *
     * @param data
     * @param bindingResult
     * @return
     */
    @PostMapping
    public Object create(@Valid @RequestBody Sheet data,
                         BindingResult bindingResult) {
        ValidatorUtil.checkParam(bindingResult);

        StpUtil.checkLogin();

        //设置用户id
        data.setUserId(StpUtil.getLoginIdAsString());

        service.create(data);

        return R.wrap(data.getId());
    }

    /**
     * 更新歌单
     *
     * @param id
     * @param data
     * @return
     */
    @PatchMapping("/{id}")
    public Object update(@PathVariable String id, @RequestBody Sheet data) {
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
     * 删除歌单
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Object destroy(@PathVariable String id) {
        StpUtil.checkLogin();

        //删除数据
        if (service.deleteByIdAndUserId(id,StpUtil.getLoginIdAsString()) != Constant.RESULT_OK) {
            throw new ArgumentException();
        }

        return R.wrap();
    }

}

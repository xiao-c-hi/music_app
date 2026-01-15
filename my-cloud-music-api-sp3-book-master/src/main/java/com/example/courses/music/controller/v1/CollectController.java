package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.model.Collect;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.mapper.CommonMapper;
import com.example.courses.music.service.CollectService;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 歌单收藏控制器
 */
@RestController
@RequestMapping("/v1")
public class CollectController {
    @Autowired
    private CollectService service;

    /**
     * 通用映射
     */
    @Autowired
    private CommonMapper commonMapper;

    /**
     * 收藏歌单
     *
     * @param data
     * @return
     */
    @PostMapping("/collects")
    public Object create(@RequestBody Collect data) {
        StpUtil.checkLogin();

        //查找这条关系
        Collect collect = service.findBySheetIdAndUserId(data.getSheetId(),StpUtil.getLoginIdAsString());

        if (collect != null) {
            //已经收藏了

            //提示已经收藏了
            throw new CommonException(Constant.ERROR_ALREADY_COLLECT, Constant.ERROR_ALREADY_COLLECT_MESSAGE);
        }

        //创建收藏对象
        collect = new Collect();

        //歌单id
        collect.setSheetId(data.getSheetId());

        //用户id
        collect.setUserId(StpUtil.getLoginIdAsString());

        //保存关系
        if (service.create(collect) != Constant.RESULT_OK) {
            throw new ArgumentException();
        }

        //收藏数+1
        commonMapper.incrementCount("sheet", data.getSheetId(), "collects_count");

        return R.wrap();
    }

    /**
     * 取消收藏歌单
     *
     * @param id          歌单id
     * @return
     */
    @DeleteMapping("/collects/{id}")
    public Object destroy(@PathVariable String id) {
        StpUtil.checkLogin();

        //查找关系
        Collect data = service.findBySheetIdAndUserId(id, StpUtil.getLoginIdAsString());

        if (data == null) {
            //没有找到
            throw new ArgumentException();
        }

        //删除关系
        if (service.deleteBySheetIdAndUserId(id, StpUtil.getLoginIdAsString()) != Constant.RESULT_OK) {
            throw new ArgumentException();
        }

        //收藏数-1
        commonMapper.decrementCount("sheet", id, "collects_count");

        return R.wrap();
    }
}

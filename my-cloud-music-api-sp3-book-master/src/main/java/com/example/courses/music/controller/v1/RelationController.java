package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.model.Common;
import com.example.courses.music.model.Relation;
import com.example.courses.music.model.Sheet;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.exception.SaveDataException;
import com.example.courses.music.mapper.CommonMapper;
import com.example.courses.music.service.RelationService;
import com.example.courses.music.service.SheetService;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.R;
import com.example.courses.music.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 歌单和音乐关系控制器
 */
@RestController
@RequestMapping("/v1")
public class RelationController {

    @Autowired
    private RelationService service;

    @Autowired
    private SheetService sheetService;

    /**
     * 通用映射
     */
    @Autowired
    private CommonMapper commonMapper;

    /**
     * 添加音乐到歌单
     *
     * @param id 要将音乐添加到那个歌单id
     * @param data 音乐id通过这个模型传递
     * @return
     */
    @PostMapping("/sheets/{id}/relations")
    public Object create(@PathVariable String id,
                         @RequestBody Common data) {
        StpUtil.checkLogin();

        //根据歌单id，用户id查询
        //目的是控制只能操作自己歌单
        Sheet sheet = sheetService.findByIdAndUserId(id, StpUtil.getLoginIdAsString());

        ValidatorUtil.checkExist(sheet);

        //创建关系对象
        Relation relation = new Relation();

        //歌单id
        relation.setSheetId(id);

        //音乐id
        relation.setSongId(data.getId());

        //用户id
        relation.setUserId(StpUtil.getLoginIdAsString());

        //保存数据
        if (service.create(relation) != Constant.RESULT_OK) {
            throw new SaveDataException();
        }

        //音乐数+1
        commonMapper.incrementCount("sheet", id, "songs_count");

        return R.wrap(relation.getId());
    }

    /**
     * 从歌单删除音乐
     *
     * @param id          歌单id
     * @param songId      音乐id，不是relationId
     * @return
     */
    @DeleteMapping("/sheets/{id}/relations/{songId}")
    public Object destroy(@PathVariable("id") String id,
                          @PathVariable("songId") String songId) {
        StpUtil.checkLogin();

        //删除关系
        if (service.deleteBySheetIdAndSongIdAndUserId(id, songId, StpUtil.getLoginIdAsString()) != Constant.RESULT_OK) {
            throw new ArgumentException();
        }

        //音乐数-1
        commonMapper.decrementCount("sheet", id, "songs_count");

        return R.wrap();
    }
}

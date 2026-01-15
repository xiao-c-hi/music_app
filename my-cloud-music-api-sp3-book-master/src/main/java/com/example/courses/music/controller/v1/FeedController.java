package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.PageInfo;
import com.example.courses.music.model.Feed;
import com.example.courses.music.service.FeedService;
import com.example.courses.music.service.LikeService;
import com.example.courses.music.util.IDUtil;
import com.example.courses.music.util.R;
import com.example.courses.music.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 动态控制器
 */
@RestController
@RequestMapping("/v1")
public class FeedController {
    /**
     * 动态服务
     */
    @Autowired
    private FeedService service;


    @Autowired
    private LikeService likeService;

    /**
     * 动态列表
     *
     * @return
     */
    @GetMapping("/feeds")
    public Object index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "30") int size) {
        PageInfo<Feed> result = service.findAll(page, size);

        likeService.findLikesByFeedIds(result.getList());

        return R.wrap(result);
    }

    /**
     * 创建动态
     *
     * @param data
     * @param bindingResult
     * @return
     */
    @PostMapping("/feeds")
    public Object create(@Valid @RequestBody Feed data,
                         BindingResult bindingResult) {

        StpUtil.checkLogin();

        ValidatorUtil.checkParam(bindingResult);

        //设置用户id
        data.setUserId(StpUtil.getLoginIdAsString());

        //获取uuid，然后获取hashCode，在转为正数
        data.setId(String.valueOf(IDUtil.getUUID().hashCode() & Integer.MAX_VALUE));

        //保存数据
        service.create(data);

        //返回数据
        return R.wrap(data.getId());
    }

    /**
     * 删除
     *
     * @return
     */
    @DeleteMapping("/feeds/{id}")
    public @ResponseBody
    Object destroy(@PathVariable String id) {
        StpUtil.checkLogin();

        service.deleteByIdAndUserId(id, StpUtil.getLoginIdAsString());

        return R.wrap();
    }
}

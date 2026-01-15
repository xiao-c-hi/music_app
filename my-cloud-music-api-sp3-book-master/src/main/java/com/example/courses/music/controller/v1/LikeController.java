package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.model.Like;
import com.example.courses.music.service.LikeService;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.R;
import com.example.courses.music.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 点赞控制器
 */
@RestController
@RequestMapping("/v1")
public class LikeController {
    @Autowired
    private LikeService service;

//    @Autowired
//    private CommonService commonMapper;

    /**
     * 点赞
     *
     * @param data
     * @return
     */
    @PostMapping("/likes")
    public Object create(@RequestBody Like data) {
        if (!(StringUtils.isBlank(data.getCommentId()) || StringUtils.isBlank(data.getFeedId()))) {
            //评论id，动态id，必须有一个有值
            throw new ArgumentException();
        }

        StpUtil.checkLogin();

        //设置用户id
        data.setUserId(StpUtil.getLoginIdAsString());

        //保存关系
        service.create(data);

        //返回关系id
        return R.wrap(data.getId());
    }

    /**
     * 取消点赞
     *
     * @param id    关系id
     * @param style 类型：0：评论；10：动态
     * @return
     */
    @DeleteMapping("/likes/{id}")
    public Object destroy(@PathVariable String id, @RequestParam(defaultValue = "0") int style) {
        StpUtil.checkLogin();

        //查询这条关系
        Like data = null;
        if (Constant.VALUE0 == style) {
            //评论
            data = service.findByCommentIdAndUserId(id, StpUtil.getLoginIdAsString());
        } else {
            //动态
            data = service.findByFeedIdAndUserId(id, StpUtil.getLoginIdAsString());
        }

        ValidatorUtil.checkExist(data);

        service.destroy(data);

        return R.wrap();
    }
}

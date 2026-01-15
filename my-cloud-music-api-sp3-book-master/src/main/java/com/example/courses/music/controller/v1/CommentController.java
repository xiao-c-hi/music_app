package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.courses.music.model.Comment;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.service.CommentService;
import com.example.courses.music.service.LikeService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/v1")
public class CommentController {
    /**
     * 评论服务
     */
    @Autowired
    private CommentService service;

    @Autowired
    private LikeService likeService;

    /**
     * 评论列表
     *
     * @param page    第几页
     * @param size    每页显示多少条
     * @param order   排序；0：最新，默认；10：最热
     * @param userId  用户id
     * @param sheetId 歌单id
     * @return
     */
    @GetMapping("/comments")
    public Object index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "0") int order,
                        @RequestParam(name = "user_id", required = false) String userId,
                        @RequestParam(name = "sheet_id", required = false) String sheetId) {
        //查询评论
        IPage<Comment> result = service.findAll(page, size, order, userId, sheetId,null);

        if (StpUtil.isLogin()) {
            //只有用户登录了才查询评论状态
            likeService.findAllByCommentIdAndUserId(result.getRecords());
        }

        return R.wrap(result);
    }

    /**
     * 创建评论
     *
     * @param data
     * @param bindingResult
     * @return
     */
    @PostMapping("/comments")
    public Object create(@Valid @RequestBody Comment data,
                         BindingResult bindingResult) {
        StpUtil.checkLogin();

        //判断参数
        if (bindingResult.hasErrors()) {
            throw new ArgumentException();
        }

        //设置用户id
        data.setUserId(StpUtil.getLoginIdAsString());

        //保存数据
        service.create(data);

        return R.wrap(data);
    }
}
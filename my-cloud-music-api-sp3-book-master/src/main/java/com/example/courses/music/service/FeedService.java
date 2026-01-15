package com.example.courses.music.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.mapper.FeedMapper;
import com.example.courses.music.model.Feed;
import com.example.courses.music.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 动态服务
 */
@Service
public class FeedService {
    /**
     * 动态mapper
     */
    @Autowired
    private FeedMapper mapper;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private CommentService commentService;

    /**
     * 创建动态
     *
     * @param data Transactional:启用事务
     *             当发生运行时异常这个方法里面的数据库操作会回滚
     */
    @Transactional
    public boolean create(Feed data) {
        //保存动态
        if (mapper.create(data) != Constant.RESULT_OK) {
            throw new ArgumentException();
        }

        //保存媒体
        if (data.getMedias() != null && data.getMedias().size() > 0) {
            resourceService.create(data.getMedias(), data.getId(), data.getUserId());
        }

        return true;
    }

    /**
     * 动态列表
     *
     * @param page
     * @param size
     * @return
     */
    public PageInfo<Feed> findAll(int page, int size) {
        //开始分页
        PageHelper.startPage(page, size);

        //查询数据
        List<Feed> datum = mapper.findAll();

        //查询动态下的评论，也可以像点赞那样多表关联查询，只是会让sql变得非常复杂，同时可能有性能问题
        commentService.findByFeeds(datum);

        //获取分页信息
        PageInfo<Feed> pageInfo = new PageInfo<>(datum);

        //返回数据
        return pageInfo;
    }

    public void deleteByIdAndUserId(String id, String userId) {
        mapper.deleteByIdAndUserId(id, userId);
    }
}

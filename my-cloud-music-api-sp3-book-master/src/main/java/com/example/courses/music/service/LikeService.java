package com.example.courses.music.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.courses.music.mapper.CommonMapper;
import com.example.courses.music.mapper.LikeMapper;
import com.example.courses.music.model.Comment;
import com.example.courses.music.model.Feed;
import com.example.courses.music.model.Like;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 点赞服务
 */
@Service
public class LikeService {
    @Autowired
    private LikeMapper mapper;

    @Autowired
    private CommonMapper commonMapper;

    public void create(Like data) {
        mapper.insert(data);

        if (StringUtils.isNotBlank(data.getCommentId())) {
            //评论点赞数+1
            commonMapper.incrementCount("comment", data.getCommentId(), "likes_count");
        }
    }

    public Like find(String data) {
        return mapper.selectById(data);
    }

    public Like findByIdAndUserId(String id, String userId) {
        LambdaQueryWrapper<Like> query = new LambdaQueryWrapper<>();
        query.eq(Like::getId, id).eq(Like::getUserId, userId);

        return mapper.selectOne(query);
    }

    public void destroy(Like data) {
        //删除关系
        mapper.deleteById(data.getId());

        //评论点赞数-1
        commonMapper.decrementCount("comment", data.getCommentId(), "likes_count");
    }

    public void findAllByCommentIdAndUserId(List<Comment> data) {
        Like like;
        for (Comment comment : data) {
            like = findByCommentIdAndUserId(comment.getId(), StpUtil.getLoginIdAsString());

            if (like != null) {
                //点赞了

                //设置点赞关系id
                comment.setLikeId(like.getId());
            }
        }
    }

    /**
     * 根据评论id，用户id查询
     *
     * @param commentId
     * @param userId
     * @return
     */
    public Like findByCommentIdAndUserId(String commentId, String userId) {
        LambdaQueryWrapper<Like> query = new LambdaQueryWrapper<>();
        query.eq(Like::getCommentId, commentId).eq(Like::getUserId, userId);

        return mapper.selectOne(query);
    }

    /**
     * 根据动态id，用户id查询
     *
     * @param id
     * @param userId
     * @return
     */
    public Like findByFeedIdAndUserId(String id, String userId) {
        LambdaQueryWrapper<Like> query = new LambdaQueryWrapper<>();
        query.eq(Like::getFeedId, id).eq(Like::getUserId, userId);

        return mapper.selectOne(query);
    }

    public void findLikesByFeedIds(List<Feed> datum) {
        for (Feed it : datum) {
            it.setLikes(mapper.findLikeUserByFeedId(it.getId()));
        }
    }
}
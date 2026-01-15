package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.courses.music.mapper.CommentMapper;
import com.example.courses.music.model.Comment;
import com.example.courses.music.model.Feed;
import com.example.courses.music.util.Constant;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论服务
 */
@Service
public class CommentService {
    /**
     * 评论映射
     */
    @Autowired
    private CommentMapper mapper;

    /**
     * 通用映射
     */
//    @Autowired
//    private CommonMapper commonMapper;

//    /**
//     * 创建评论
//     *
//     * @param data
//     * @return
//     */
//    public boolean create(Comment data) {
//        //创建评论
//        if (mapper.create(data) != Constant.RESULT_OK) {
//            throw new SaveDataException();
//        }
//
//        //更新相应缓存数量
//        if (StringUtils.isNotBlank(data.getSheetId())) {
//            //歌单下的评论
//
//            //歌单评论数+1
//            commonMapper.incrementCount("sheet", data.getSheetId(), "comments_count");
//        } else if (StringUtils.isNotBlank(data.getVideoId())) {
//            //视频下的评论
//
//            //视频评论数+1
//            commonMapper.incrementCount("video", data.getVideoId(), "comments_count");
//        }
//
//        //很明显，对于mybatis这种框架
//        //如果不封装，这样缓存数据比较麻烦
//
//        return true;
//    }
    public List<Comment> findAllByGoodsId(String goodsId){
        return findAll(1,2,0,null,null,goodsId).getRecords();
    }

    /**
     * 评论列表
     *
     * @param page
     * @param size
     * @param order
     * @param userId
     * @param sheetId
     * @return
     */
    public IPage<Comment> findAll(int page, int size, int order, String userId, String sheetId,String goodsId) {
        //查询条件
        Map<String, String> conditions = new HashMap<>();
        if (StringUtils.isNotBlank(userId)) {
            conditions.put("c.user_id", String.format("'%s'", userId));
        }

        if (StringUtils.isNotBlank(sheetId)) {
            conditions.put("c.sheet_id", String.format("'%s'", sheetId));
        }

        if (StringUtils.isNotBlank(goodsId)) {
            conditions.put("c.goods_id", String.format("'%s'", goodsId));
        }

        //排序
        String orderField;
        if (order == Constant.ORDER_HOT) {
            //最热

            //按照点赞数倒序
            orderField = "c.likes_count";
        } else {
            //最新

            //按照创建时间倒序
            orderField = "c.created_at";
        }

        //查询数据
        IPage<Comment> datum = mapper.findAll(new Page<>(page, size), conditions, orderField, Constant.DESC);
//        List<Comment> datum = mapper.findAllBySQL(conditions, orderField);

        //返回数据
        return datum;
    }

    public void create(Comment data) {
        mapper.insert(data);
    }

    /**
     * 查询每条动态的评论
     *
     * @param data
     */
    public void findByFeeds(List<Feed> data) {
        //查询条件
        Map<String, String> conditions = new HashMap<>();

        IPage<Comment> result = null;
        for (Feed d : data) {
            conditions.clear();
            conditions.put("c.feed_id", String.format("'%s'", d.getId()));

            //查询
            result = mapper.findAll(new Page<>(1, 100), conditions, "c.created_at", Constant.ASC);

            if (CollectionUtils.isNotEmpty(result.getRecords())) {
                d.setComments(result.getRecords());
            }
        }
    }
}
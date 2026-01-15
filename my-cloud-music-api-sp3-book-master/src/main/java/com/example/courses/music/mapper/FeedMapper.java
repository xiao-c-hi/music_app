package com.example.courses.music.mapper;

import com.example.courses.music.model.Feed;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 动态映射
 * 这个是MyBatis实现的
 */
@Repository
public interface FeedMapper {

    /**
     * 创建动态
     *
     * @param data
     * @return
     */
    int create(Feed data);

    /**
     * 动态列表
     *
     * @return
     */
    List<Feed> findAll();

    /**
     * 根据id和用户id删除
     *
     * @param id
     * @param userId
     */
    void deleteByIdAndUserId(String id, String userId);
}

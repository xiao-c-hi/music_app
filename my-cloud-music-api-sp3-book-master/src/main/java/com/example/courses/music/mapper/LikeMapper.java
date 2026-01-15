package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Like;
import com.example.courses.music.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 点赞映射
 */
@Repository
public interface LikeMapper extends BaseMapper<Like> {
    List<User> findLikeUserByFeedId(String feedId);
}

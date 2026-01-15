package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Topic;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 话题映射
 */
@Repository
public interface TopicMapper extends BaseMapper<Topic> {
    /**
     * 查询数据
     *
     * @param since
     * @param size
     * @return
     */
    List<Topic> findAll(Long since, int size);
}

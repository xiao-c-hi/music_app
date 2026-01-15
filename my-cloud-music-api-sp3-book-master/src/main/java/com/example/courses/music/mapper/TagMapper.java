package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Tag;
import org.springframework.stereotype.Repository;

/**
 * 标签映射
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {
}

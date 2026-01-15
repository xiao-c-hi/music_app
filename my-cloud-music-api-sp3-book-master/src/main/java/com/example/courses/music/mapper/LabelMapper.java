package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Label;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标签关系映射
 */
@Repository
public interface LabelMapper extends BaseMapper<Label> {
    /**
     * 创建/替换标签关系
     *
     * @param labels
     */
    void replace(@Param("labels") List<Label> labels);
}

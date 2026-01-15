package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Session;
import org.springframework.stereotype.Repository;

/**
 * 会话映射
 */
@Repository
public interface SessionMapper extends BaseMapper<Session> {
    /**
     * 创建或者替换
     * <p>
     * 根据id判断，如果数据中有该id数据，那就是替换
     * 否则就是新增
     *
     * @param data
     */
    void replace(Session data);
}

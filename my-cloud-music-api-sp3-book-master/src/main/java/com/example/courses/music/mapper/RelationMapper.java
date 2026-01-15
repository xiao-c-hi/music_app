package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Relation;
import org.springframework.stereotype.Repository;

/**
 * 歌单，音乐关系mapper
 */
@Repository
public interface RelationMapper extends BaseMapper<Relation> {
}

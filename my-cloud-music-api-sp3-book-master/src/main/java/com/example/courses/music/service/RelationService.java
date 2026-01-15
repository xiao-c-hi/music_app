package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.courses.music.mapper.RelationMapper;
import com.example.courses.music.model.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 歌单，音乐关系服务
 */
@Service
public class RelationService {
    @Autowired
    private RelationMapper mapper;

    public int create(Relation data) {
        return mapper.insert(data);
    }

    /**
     * 删除关系
     *
     * @param sheetId
     * @param songId
     * @param userId
     * @return
     */
    public int deleteBySheetIdAndSongIdAndUserId(String sheetId, String songId, String userId) {
        LambdaQueryWrapper<Relation> query = new LambdaQueryWrapper<>();
        query.eq(Relation::getSheetId, sheetId).eq(Relation::getSongId, songId).eq(Relation::getUserId, userId);
        return mapper.delete(query);
    }
}
package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.courses.music.mapper.CollectMapper;
import com.example.courses.music.model.Collect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 收藏服务
 */
@Service
public class CollectService {
    @Autowired
    private CollectMapper mapper;

    /**
     * 查询收藏关系
     *
     * @param sheetId
     * @param userId
     * @return
     */
    public Collect findBySheetIdAndUserId(String sheetId, String userId) {
        LambdaQueryWrapper<Collect> query = new LambdaQueryWrapper<>();
        query.eq(Collect::getSheetId, sheetId).eq(Collect::getUserId, userId);
        return mapper.selectOne(query);
    }

    public int create(Collect data) {
        return mapper.insert(data);
    }

    /**
     * 删除收藏关系
     *
     * @param sheetId
     * @param userId
     * @return
     */
    public int deleteBySheetIdAndUserId(String sheetId, String userId) {
        LambdaQueryWrapper<Collect> query = new LambdaQueryWrapper<>();
        query.eq(Collect::getSheetId, sheetId).eq(Collect::getUserId, userId);
        return mapper.delete(query);
    }
}
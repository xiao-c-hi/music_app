package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.courses.music.mapper.AdMapper;
import com.example.courses.music.model.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 广告服务
 */
@Service
public class AdService {
    @Autowired
    private AdMapper mapper;

    public List<Ad> findAll(int style) {
        LambdaQueryWrapper<Ad> query = new LambdaQueryWrapper<>();
        query.eq(Ad::getPosition, style);

        query.orderByDesc(Ad::getSort);
        return mapper.selectList(query);
    }
}
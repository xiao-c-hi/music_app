package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.courses.music.mapper.VersionMapper;
import com.example.courses.music.model.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 版本服务
 */
@Service
public class VersionService {
    @Autowired
    private VersionMapper mapper;

    /**
     * 检查是否有新版本
     *
     * @param data
     * @return
     */
    public Version check(byte platform, int data) {
        LambdaQueryWrapper<Version> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Version::getPlatform, platform).gt(Version::getCode, data);
        return mapper.selectOne(queryWrapper);
    }
}
package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.courses.music.mapper.VideoMapper;
import com.example.courses.music.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 广告服务
 */
@Service
public class VideoService {
    @Autowired
    private VideoMapper mapper;

    public IPage<Video> findAllDetail(int page, int size) {
        Page<Video> pageData = new Page<>(page, size);
        return mapper.findAllDetail(pageData);
    }

    public Video findDetail(String data) {
        return mapper.findDetail(data);
    }
}
package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.courses.music.model.Video;
import org.springframework.stereotype.Repository;

/**
 * 视频映射
 */
@Repository
public interface VideoMapper extends BaseMapper<Video> {
    IPage<Video> findAllDetail(Page<Video> data);

    Video findDetail(String data);
}

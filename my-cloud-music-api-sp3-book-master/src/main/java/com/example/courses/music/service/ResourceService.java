package com.example.courses.music.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.courses.music.mapper.ResourceMapper;
import com.example.courses.music.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
 * 资源服务
 * 继承ServiceImpl，就有批量操作方法，例如：批量保存saveBatch方法
 */
@Service
public class ResourceService extends ServiceImpl<ResourceMapper, Resource> {
    @Autowired
    private ResourceMapper mapper;

    public void create(List<Resource> medias, String feedId, String userId) {
        medias.forEach(new Consumer<Resource>() {
            @Override
            public void accept(Resource resource) {
                resource.setFeedId(feedId);
                resource.setUserId(userId);
            }
        });
        saveBatch(medias);
    }
}

package com.example.courses.music.service;

import com.example.courses.music.mapper.TopicMapper;
import com.example.courses.music.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 话题服务
 */
@Service
public class TopicService {
    @Autowired
    private TopicMapper mapper;

    public List<Topic> findAll(Long since, int size) {
        return mapper.findAll(since, size);
    }
}
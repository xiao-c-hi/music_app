package com.example.courses.music.service;

import com.example.courses.music.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通用服务
 */
@Service
public class CommonService {
    @Autowired
    private CommonMapper mapper;


    /**
     * 对指定表，指定数据，指定列，+1
     *
     * @param table
     * @param id
     * @param field
     */
    public void incrementCount(String table, String id, String field) {
        mapper.incrementCount(table, id, field);
    }

    /**
     * 对指定表，指定数据，指定列，-1
     *
     * @param table
     * @param id
     * @param field
     */
    public void decrementCount(String table, String id, String field) {
        mapper.decrementCount(table, id, field);
    }
}
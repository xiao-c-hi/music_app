package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.courses.music.mapper.CategoryMapper;
import com.example.courses.music.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private static Logger log = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryMapper mapper;

    public List<Category> findAll(String parent) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (parent == null) {
            queryWrapper.isNull(Category::getParentId);
        }else{
            queryWrapper.eq(Category::getParentId,parent);
        }
        return mapper.selectList(queryWrapper);
    }
}

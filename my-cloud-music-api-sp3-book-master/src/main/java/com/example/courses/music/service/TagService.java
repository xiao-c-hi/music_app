package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.courses.music.mapper.TagMapper;
import com.example.courses.music.model.Tag;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签服务
 */
@Service
public class TagService {
    private static Logger log = LoggerFactory.getLogger(TagService.class);

    @Autowired
    TagMapper mapper;

    /**
     * 查询子标签
     *
     * @param datum
     */
    public void findSubTag(List<Tag> datum) {
        //查询第二层标签
        //如果项目中是无限层级
        //那么一般都会通过parentId一层一层获取
        List<Tag> tags;
        for (Tag tag : datum) {
            tags = findAll(tag.getId());
            if (tags != null && tags.size() > 0) {
                tag.setData(tags);
            }
        }
    }

    /**
     * 查询所有标签
     *
     * @param parentId
     * @return
     */
    public List<Tag> findAll(String parentId) {
        LambdaQueryWrapper<Tag> query = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(parentId)) {
            query.eq(Tag::getParentId, parentId);
        } else {
            //为空，就是第一级
            query.isNull(Tag::getParentId);
        }

        //创建时间倒序
        query.orderByDesc(Tag::getCreatedAt);

        List<Tag> result = mapper.selectList(query);

        return result;
    }
}
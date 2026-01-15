package com.example.courses.music.service;

import com.example.courses.music.mapper.LabelMapper;
import com.example.courses.music.model.Label;
import com.example.courses.music.model.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 标签关系服务
 */
@Service
public class LabelService {
    private static Logger log = LoggerFactory.getLogger(LabelService.class);

    @Autowired
    private LabelMapper mapper;

    /**
     * 保存标签
     *
     * @param tags
     * @param sheetId
     * @param userId
     */
    public void saveTag(List<Tag> tags, String sheetId, String userId) {
        //判断是否有标签
        if (tags != null && tags.size() > 0) {
            //将tag转为label
            List<Label> labels = tags.stream().map(new Function<Tag, Label>() {
                @Override
                public Label apply(Tag tag) {
                    Label result = new Label(tag.getTagId(), sheetId, userId);
                    return result;
                }
            }).collect(Collectors.toList());

            log.info("saveTags {}", tags.size());

            //保存标签
            mapper.replace(labels);
        }
    }
}
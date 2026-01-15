package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Sheet;
import com.example.courses.music.model.SuggestItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌单映射
 */
@Repository
public interface SheetMapper extends BaseMapper<Sheet> {
    /**
     * 详情，会查询关联数据，例如：歌单创建人，歌曲，第一级标签（第二级单独查询）
     *
     * @param data
     * @return
     */
    Sheet findDetail(String data);

    /**
     * 查询用户收藏的歌单
     *
     * @param data
     * @return
     */
    List<Sheet> findCollectByUserId(String data);

    /**
     * 搜索建议
     *
     * @param query
     * @return
     */
    List<SuggestItem> suggest(String query);
}

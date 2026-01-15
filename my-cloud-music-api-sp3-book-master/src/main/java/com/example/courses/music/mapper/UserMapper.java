package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.courses.music.model.SuggestItem;
import com.example.courses.music.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户映射
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 用户关注的人（好友）
     *
     * @param data
     * @return
     */
    List<User> following(String data);

    /**
     * 关注用户（我）的人（粉丝）
     *
     * @param data
     * @return
     */
    List<User> followers(String data);

    /**
     * 用户搜索
     *
     * @param page
     * @param query
     * @return
     */
    IPage<User> search(Page<User> page, String query);

    /**
     * 搜索建议
     *
     * @param query
     * @return
     */
    List<SuggestItem> suggest(String query);
}

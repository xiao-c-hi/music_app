package com.example.courses.music.service;


import com.example.courses.music.model.Suggest;
import com.example.courses.music.model.response.PageResponse;

/**
 * 搜索服务
 */
public interface SearchService {
    /**
     * 歌单搜索
     *
     * @param page
     * @param size
     * @return 这里返回自定义分页对象目的是，后面还要讲解使用ES实现搜索，而他没法直接返回IPage
     * 所以这里就直接返回自定义的分页对象
     */
    PageResponse sheets(String query, int page, int size);

    /**
     * 用户搜索
     *
     * @param query
     * @param page
     * @param size
     * @return
     */
    PageResponse users(String query, int page, int size);

    /**
     * 搜索建议
     *
     * @param query
     * @return
     */
    Suggest suggest(String query);
}

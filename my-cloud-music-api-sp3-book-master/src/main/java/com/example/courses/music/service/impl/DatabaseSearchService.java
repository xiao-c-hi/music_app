package com.example.courses.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.courses.music.mapper.SheetMapper;
import com.example.courses.music.model.Sheet;
import com.example.courses.music.model.Suggest;
import com.example.courses.music.model.User;
import com.example.courses.music.model.response.PageResponse;
import com.example.courses.music.service.SearchService;
import com.example.courses.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 搜索服务 数据库实现
 */
public class DatabaseSearchService implements SearchService {
    @Autowired
    private SheetMapper sheetMapper;

    @Autowired
    private UserService userService;

    /**
     * 歌单搜索
     *
     * @param query
     * @param page
     * @param size
     * @return
     */
    public PageResponse sheets(String query, int page, int size) {
        LambdaQueryWrapper<Sheet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Sheet::getTitle, query).or().like(Sheet::getDetail, query);
        queryWrapper.orderByDesc(Sheet::getCreatedAt);

        Page<Sheet> sheetPage = sheetMapper.selectPage(new Page<Sheet>(page, size), queryWrapper);

        return PageResponse.create((Page) sheetPage);
    }

    /**
     * 用户搜索
     *
     * @param query
     * @param page
     * @param size
     * @return
     */
    public PageResponse users(String query, int page, int size) {
        //创建分页请求
        Page<User> pageRequest = new Page<>(page, size);

        //搜索数据
        IPage<User> userPage = userService.search(pageRequest, query);

        return PageResponse.create((Page) userPage);
    }

    /**
     * 搜索建议
     *
     * @param query
     * @return
     */
    public Suggest suggest(String query) {
        Suggest data = new Suggest();

        //查询歌单搜索建议
        data.setSheets(sheetMapper.suggest(query));

        //查询用户搜索建议
        data.setUsers(userService.suggest(query));

        return data;
    }
}

package com.example.courses.music.controller.v1.admin;

import com.example.courses.music.service.SearchService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 搜索控制器
 * 我们这里实现为
 * 不登录就能搜索
 */
@RequestMapping("/v1")
@RestController
public class SearchController {
    /**
     * 搜索服务
     */
    @Autowired
    private SearchService service;

    /**
     * 歌单搜索
     *
     * @param query
     * @return
     */
    @GetMapping("/searches/sheets")
    public Object sheets(@RequestParam("query") String query,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "10") int size) {
        return R.wrap(service.sheets(query, page, size));
    }

    /**
     * 用户搜索
     *
     * @param query
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/searches/users")
    public Object users(@RequestParam("query") String query,
                        @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size) {
        return R.wrap(service.users(query, page, size));
    }

    /**
     * 搜索建议
     *
     * @param query
     * @return
     */
    @GetMapping("/searches/suggests")
    public Object suggest(@RequestParam("query") String query) {
        return R.wrap(service.suggest(query));
    }
}

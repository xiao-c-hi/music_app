package com.example.courses.music.service.impl;

import com.example.courses.music.model.Sheet;
import com.example.courses.music.model.Suggest;
import com.example.courses.music.model.SuggestItem;
import com.example.courses.music.model.User;
import com.example.courses.music.model.response.PageResponse;
import com.example.courses.music.service.ElasticSheetService;
import com.example.courses.music.service.ElasticUserService;
import com.example.courses.music.service.SearchService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 搜索服务 Elasticsearch实现
 */
public class ElasticSearchService implements SearchService {
    /**
     * 歌单Elastic服务
     */
    @Autowired
    ElasticSheetService elasticSheetService;

    /**
     * 用户Elastic服务
     */
    @Autowired
    ElasticUserService elasticUserService;

    @Override
    public PageResponse sheets(String query, int page, int size) {
        return elasticSheetService.findAll(query, page, size);
    }

    @Override
    public PageResponse users(String query, int page, int size) {
        return elasticUserService.findAll(query, page, size);
    }

    /**
     * 搜索建议
     * <p>
     * 因为ES搜索建议用起来很麻烦，效果也不是很好
     * <p>
     * 所以就调用搜索，搜索标题（不搜索其他字段，真实项目中看需求），实现搜索建议
     *
     * @param query
     * @return
     */
    @Override
    public Suggest suggest(String query) {
        Suggest data = new Suggest();

        //查询歌单建议
        PageResponse sheetPageData = elasticSheetService.findAll(query, 1, 5, true);
        List<Sheet> sheets = (List<Sheet>) sheetPageData.getData();

        //判断是否有值
        if (CollectionUtils.isNotEmpty(sheets)) {
            //将List<Sheet>转换为List<SuggestItem>
            List<SuggestItem> suggestItems = sheets
                    .stream()
                    .map(new Function<Sheet, SuggestItem>() {
                        @Override
                        public SuggestItem apply(Sheet it) {
                            SuggestItem data = new SuggestItem(it.getId(), it.getTitle());
                            return data;
                        }
                    }).collect(Collectors.toList());

            //设置数据
            data.setSheets(suggestItems);
        }

        //查询用户搜索建议
        PageResponse userPageData = elasticUserService.findAll(query, 1, 5, true);
        List<User> users = (List<User>) userPageData.getData();

        if (users != null && users.size() > 0) {
            //将List<User>转换为List<SuggestItem>

            List<SuggestItem> suggestItems = users
                    .stream()
                    .map(it -> new SuggestItem(it.getId(), it.getNickname()))
                    .collect(Collectors.toList());

            //设置数据
            data.setUsers(suggestItems);
        }

        return data;
    }
}

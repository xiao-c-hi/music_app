package com.example.courses.music.service;

import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.User;
import com.example.courses.music.model.response.PageResponse;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.JSONUtil;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户Elastic服务
 */
@Service
public class ElasticUserService {
    private static final Logger log = LoggerFactory.getLogger(ElasticUserService.class);

    @Autowired
    ElasticService elasticService;

    /**
     * 分页搜索
     *
     * @param query
     * @param page
     * @param size
     * @return
     */
    public PageResponse findAll(String query, int page, int size) {
        return findAll(query, size, size, false);
    }

    /**
     * 分页搜索
     *
     * @param query
     * @param page
     * @param size
     * @return
     */
    public PageResponse findAll(String query, int page, int size, boolean onlySearchTitle) {
        SearchRequest request = new SearchRequest("users");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //创建bool查询构建器
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //添加查询条件
        //must:AND
        //mustNot:NOT
        //should:OR
        //标题
        boolQueryBuilder.should(QueryBuilders.matchQuery("nickname", query).fuzziness("3"));

        if (!onlySearchTitle) {
            //描述
            boolQueryBuilder.should(QueryBuilders.matchQuery("detail", query));
        }

        sourceBuilder.query(boolQueryBuilder);

        request.source(sourceBuilder);

        try {
            SearchResponse response = elasticService.search(request, sourceBuilder, page, size);

            SearchHits hits = response.getHits();

            TotalHits totalHits = hits.getTotalHits();

            // 匹配的数量
            long numHits = totalHits.value;

            //搜索结果数量是否准确 (EQUAL_TO)，或者 大于或等于(GREATER_THAN_OR_EQUAL_TO)
            TotalHits.Relation relation = totalHits.relation;

            //最匹配的数据得分，0~1
            float maxScore = hits.getMaxScore();

            //结果列表
            List<User> results = new ArrayList<>();

            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                String index = hit.getIndex();
                String id = hit.getId();

                //这条数据得分
                float score = hit.getScore();

                String sourceAsString = hit.getSourceAsString();

                //转为json
                User result = JSONUtil.parse(sourceAsString, User.class);
                results.add(result);
            }

            //有多少页
            int pages = (int) Math.ceil(numHits * 1.0 / size);

            return new PageResponse(numHits, pages, page, size, results);
        } catch (IOException e) {
            throw new CommonException(Constant.ERROR_SEARCH, Constant.ERROR_SEARCH_MESSAGE);
        }
    }

    /**
     * 更新/新增
     *
     * @param data
     */
    public void update(User data) {
        IndexRequest request = new IndexRequest("users");

        request.id(data.getId());

        String jsonString = JSONUtil.toJSON(data);
        request.source(jsonString, XContentType.JSON);

        elasticService.update(request);
    }

    /**
     * 批量更新/新增
     *
     * @param data
     */
    public void update(List<User> data) {
        BulkRequest request = new BulkRequest();

        for (User it : data) {
            IndexRequest itemRequest = new IndexRequest("users").id(it.getId())
                    .source(JSONUtil.toJSON(it), XContentType.JSON);
            request.add(itemRequest);
        }

        elasticService.bulk(request);
    }

    /**
     * 删除
     *
     * @param data
     */
    public void delete(String data) {
        DeleteRequest request = new DeleteRequest(
                "users",
                data);

        elasticService.delete(request);
    }
}

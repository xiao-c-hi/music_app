package com.example.courses.music.service;

import com.example.courses.music.config.Config;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.util.Constant;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Elastic服务
 * <p>
 * 目的是对Java High Level REST Client SDK再次封装
 * 官方文档：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-supported-apis.html
 */
@Service
public class ElasticService {
    private static Logger log = LoggerFactory.getLogger(ElasticService.class);

    RestHighLevelClient client;

    public ElasticService() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(Config.ELASTIC_ENDPOINT, Config.ELASTIC_PORT, Config.ELASTIC_SCHEME)
                        //如果有多个，这样传入多个
//                        ,new HttpHost("localhost", 9201, "http")
                ));
        log.info("init");
    }

    /**
     * 执行搜索
     *
     * @param request
     * @param sourceBuilder
     * @param page
     * @param size
     * @return
     */
    public SearchResponse search(SearchRequest request, SearchSourceBuilder sourceBuilder, int page, int size) throws IOException {
        //从多少条开始，默认从0开始
        sourceBuilder.from((page - 1) * size);

        //每页多少条
        sourceBuilder.size(size);

        //排序，先按照得分倒序（默认），然后按照创建时间倒序
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        sourceBuilder.sort(new FieldSortBuilder("created_at").order(SortOrder.DESC));

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();

        //标题高亮
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
//        highlightTitle.highlighterType("unified");

        //更改开始标签和结束标签
//        highlightTitle.preTags("<strong>");
//        highlightTitle.postTags("</strong>");
        highlightBuilder.field(highlightTitle);

        //描述高亮
        HighlightBuilder.Field highlightDetail = new HighlightBuilder.Field("detail");
        highlightBuilder.field(highlightDetail);
        sourceBuilder.highlighter(highlightBuilder);

        return client.search(request, RequestOptions.DEFAULT);
    }

    /**
     * 更新或者新增
     *
     * @param request
     * @return
     */
    public void update(IndexRequest request) {
        try {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);

            if (response.getResult() == DocWriteResponse.Result.CREATED) {
                log.info("create success");
            } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
                log.info("update success");
            }
        } catch (ElasticsearchException e) {
            log.error("update ElasticsearchException {}", e);
            throw new CommonException(Constant.ERROR_UPDATE_SEARCH, Constant.ERROR_UPDATE_SEARCH_MESSAGE, e);
        } catch (IOException e) {
            log.error("update error {}", e);
            throw new CommonException(Constant.ERROR_UPDATE_SEARCH, Constant.ERROR_UPDATE_SEARCH_MESSAGE, e);
        }
    }

    /**
     * 删除
     *
     * @param request
     */
    public void delete(DeleteRequest request) {
        try {
            DeleteResponse deleteResponse = client.delete(request,
                    RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            log.error("delete ElasticsearchException {}", e);
            throw new CommonException(Constant.ERROR_DELETE_SEARCH, Constant.ERROR_DELETE_SEARCH_MESSAGE, e);
        } catch (IOException e) {
            log.error("update error {}", e);
            throw new CommonException(Constant.ERROR_DELETE_SEARCH, Constant.ERROR_DELETE_SEARCH_MESSAGE, e);
        }
    }

    /**
     * 批量操作
     *
     * @param request
     */
    public void bulk(BulkRequest request) {
        try {
            BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);

            boolean isError = false;
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();

                    //这里打印所有失败信息到日志，方便后面排查错误
                    log.error("bulk BulkItemResponse error {}", failure.getCause());
                    isError = true;
                }
            }

            if (isError) {
                //如果出错了，抛出异常
                throw new CommonException(Constant.ERROR_BULK_SEARCH, Constant.ERROR_BULK_SEARCH_MESSAGE);
            }
        } catch (IOException e) {
            log.error("bulk error {}", e);
            throw new CommonException(Constant.ERROR_BULK_SEARCH, Constant.ERROR_BULK_SEARCH_MESSAGE, e);
        }
    }
}

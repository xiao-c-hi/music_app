package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.courses.music.config.sql.CommentSQL;
import com.example.courses.music.model.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 评论映射
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 评论列表
     *
     * @param conditions
     * @param orderField 排序字段
     * @param order      排序方式，asc：正序，desc：倒序
     * @return
     */
    IPage<Comment> findAll(Page<?> data, @Param("conditions") Map<String, String> conditions,
                           @Param("orderField") String orderField,
                           @Param("order") String order);

    /**
     * 在java代码中动态sql
     * <p>
     * 表示调用CommentSQL类的findAll方法生成sql
     * <p>
     * 但这种方式就要在代码中映射结果了
     * 这里就不在讲解了，因为这不是MyBatis课程
     * 个人感觉还是XML映射结果更方便
     * 所以真实项目中这么复杂的sql推荐还是加载在xml中实现
     *
     * @param conditions
     * @param orderField
     * @return
     */
    @SelectProvider(type = CommentSQL.class, method = "findAll")
    List<Comment> findAllBySQL(@Param("conditions") Map<String, String> conditions,
                               @Param("orderField") String orderField);
}

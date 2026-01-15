package com.example.courses.music.config.sql;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * 评论相关SQL工具类
 */
public class CommentSQL {
    /**
     * 生成评论sql
     *
     * @return
     */
    public String findAll(Map<String, String> conditions, String orderField) {
        //如下写法等效于：
//        new SQL().SELECT("c.id,c.content").SELECT("c.likes_count")

        String sql = new SQL() {{
            SELECT("c.id,c.content");
            SELECT("c.likes_count");
            SELECT("c.created_at");
            SELECT("c.updated_at");

            SELECT("u.id as \"user.id\"");
            SELECT("u.nickname as \"user.nickname\"");
            SELECT("u.avatar as \"user.avatar\"");

            SELECT("parent.id as \"parent.id\"");
            SELECT("parent.content as \"parent.content\"");

            SELECT("parent_user.id as \"parent.user.id\"");
            SELECT("parent_user.nickname as \"parent.user.nickname\"");
            SELECT("parent_user.avatar as \"parent.user.avatar\"");

            SELECT("sheet.id as \"sheet.id\"");
            SELECT("sheet.title as \"sheet.title\"");

            SELECT("sheet_user.id as \"sheet.user.id\"");
            SELECT("sheet_user.nickname as \"sheet.user.nickname\"");
            SELECT("sheet_user.avatar as \"sheet.user.avatar\"");

            FROM("comment as c");
            INNER_JOIN("user as u on c.user_id = u.id");
            LEFT_OUTER_JOIN("comment as parent on parent.id = c.parent_id");
            LEFT_OUTER_JOIN("user as parent_user on parent.user_id = parent_user.id");
            LEFT_OUTER_JOIN("sheet on c.sheet_id = sheet.id");
            LEFT_OUTER_JOIN("user as sheet_user on sheet.user_id = sheet_user.id");

            //查询参数
            for (Map.Entry<String, String> entry : conditions.entrySet()) {
                WHERE(entry.getKey() + " = " + entry.getValue());
            }

            //排序
            ORDER_BY(orderField);

            //分页
//            LIMIT(size);
//            OFFSET(offset);
        }}.toString();
        return sql;
    }
}
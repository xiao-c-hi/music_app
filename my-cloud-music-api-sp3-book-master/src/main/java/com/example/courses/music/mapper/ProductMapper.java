package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品映射
 */
@Repository
public interface ProductMapper extends BaseMapper<Product> {
    /**
     * 查询数据
     *
     * @param field
     * @param order
     * @param query
     * @param brands
     * @return
     */
    List<Product> findAll(String field, String order, String query, List<String> brands);
}

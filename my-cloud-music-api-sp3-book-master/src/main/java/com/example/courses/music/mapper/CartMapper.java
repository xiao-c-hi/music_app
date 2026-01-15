package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Cart;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 购物车Item映射
 */
@Repository
public interface CartMapper extends BaseMapper<Cart> {
    /**
     * 根据用户id查询数据
     *
     * @param data
     * @return
     */
    List<Cart> findByUserId(String data);

    List<Cart> findByIds(List<String> data);
}

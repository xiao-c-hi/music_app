package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.courses.music.mapper.CartMapper;
import com.example.courses.music.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 购物车服务
 */
@Service
public class CartService {
    @Autowired
    private CartMapper mapper;

    /**
     * 根据id列表查询
     *
     * @param data
     * @return
     */
    public List<Cart> findByIds(List<String> data) {
        return mapper.findByIds(data);
    }

    public List<Cart> findByUserId(String data) {
        return mapper.findByUserId(data);
    }

    public int create(Cart data) {
        return mapper.insert(data);
    }

    public int update(Cart data) {
        return mapper.updateById(data);
    }

    /**
     * 根据用户id和商品id查询
     *
     * @param userId
     * @param productId
     * @return
     */
    public Cart findByUserIdAndProductId(String userId, String productId) {
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId).eq(Cart::getProductId, productId);

        return mapper.selectOne(queryWrapper);
    }

    /**
     * 只更新count
     *
     * @param data
     * @return
     */
    public int updateCount(Cart data) {
        LambdaUpdateWrapper<Cart> updateWrapper = new LambdaUpdateWrapper<Cart>();

        //限定id和当前用户id
        updateWrapper.eq(Cart::getId, data.getId()).eq(Cart::getUserId, data.getUserId());

        //只更新count，防止恶意更新
        updateWrapper.set(Cart::getCount, data.getCount());

        return mapper.update(null, updateWrapper);
    }

    public int deleteByIdAndUserId(String id, String userId) {
        LambdaQueryWrapper<Cart> query = new LambdaQueryWrapper<>();
        query.eq(Cart::getId, id).eq(Cart::getUserId, userId);

        return mapper.delete(query);
    }

    /**
     * 批量删除购物车
     *
     * @param data
     * @param userId
     */
    public void batchDelete(List<String> data, String userId) {
        LambdaQueryWrapper<Cart> query = new LambdaQueryWrapper<>();
        query.eq(Cart::getUserId, userId).in(Cart::getId, data);
        mapper.delete(query);

        //警告：不要直接调用deleteBatchIds方法，除非在服务端判断了列表每个id就是该用户的
        //否则会有可以删除任何人购物车item bug
    }
}
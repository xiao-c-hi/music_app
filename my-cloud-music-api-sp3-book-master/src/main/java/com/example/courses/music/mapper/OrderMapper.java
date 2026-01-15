package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单映射
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 根据用户id和状态查询数据
     *
     * @param data
     * @param status
     * @return
     */
    List<Order> findByUserIdAndStatus(String data, int status);

    /**
     * 更新订单状态和第三方订单号
     *
     * @param id
     * @param status
     * @param other
     * @return
     */
    int updateStatusAndOther(@Param("id") String id,
                             @Param("status") int status,
                             @Param("other") String other);

    /**
     * 查询所有超时的订单
     * <p>
     * 如果数据比较多，可以通过分页；如果数据还比较多，可以使用队列实现关闭订单
     *
     * @return
     */
    List<Order> findAllByTimeout();
}

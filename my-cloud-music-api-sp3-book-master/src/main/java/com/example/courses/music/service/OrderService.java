package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.courses.music.mapper.OrderMapper;
import com.example.courses.music.model.Cart;
import com.example.courses.music.model.Coupon;
import com.example.courses.music.model.Order;
import com.example.courses.music.model.request.OrderRequest;
import com.example.courses.music.model.response.ConfirmOrderResponse;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.IDUtil;
import com.example.courses.music.util.JSONUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 订单服务
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapper mapper;

    @Autowired
    private CouponService couponService;

    /**
     * 订单列表
     * <p>
     * 这里就不再实现分页了，因为歌单，评论，商品都实现了
     *
     * @param data
     * @param status
     * @return
     */
    public PageInfo<Order> findByUserIdAndStatus(int page,int size,String data, int status) {
        //使用MyBatis分页插件实现分页
        //表示从page页开始，每页size条数据
        PageHelper.startPage(page, size);

        List<Order> results = mapper.findByUserIdAndStatus(data, status);

        //PageInfo是框架提供的对象
        //这里面有分页数据
        //例如多少页
        PageInfo<Order> pageInfo = new PageInfo<>(results);

        return pageInfo;
    }

    public void create(Order data) {
        mapper.insert(data);
    }

    public Order findByIdAndUserId(String id, String userId) {
        LambdaQueryWrapper<Order> whereWrapper = new LambdaQueryWrapper<>();
        whereWrapper.eq(Order::getId, id).eq(Order::getUserId, userId);

        return mapper.selectOne(whereWrapper);
    }

    public int update(Order data) {
        return mapper.updateById(data);
    }

    /**
     * 查询订单
     *
     * @param id
     * @param number
     * @param price
     * @param status
     * @return
     */
    public Order findByIdAndNumberAndPriceAndStatus(String id, String number, String price, int status) {
        LambdaQueryWrapper<Order> whereWrapper = new LambdaQueryWrapper<>();
        whereWrapper.eq(Order::getId, id).eq(Order::getNumber, number).eq(Order::getStatus, status);

        return mapper.selectOne(whereWrapper);
    }

    public int updateStatusAndOther(String id, int status, String other) {
        return mapper.updateStatusAndOther(id, status, other);
    }

    /**
     * 购买一个商品确认订单
     *
     * @param data
     * @return
     */
    public ConfirmOrderResponse confirmOrderByProduct(OrderRequest data) {
        ConfirmOrderResponse result = new ConfirmOrderResponse();

        //将购买单个商品也转为通过购物车购买返回格式，这样客户端好统一处理
        result.setCarts(Arrays.asList(Cart.create(data.getProduct())));

        //总价，如果有运费，就加上
        result.setTotalPrice(data.getProduct().getPrice());

        return result;
    }

    /**
     * 通过购物车购买 确认订单
     *
     * @param data
     * @return
     */
    public ConfirmOrderResponse confirmOrderByCarts(OrderRequest data) {
        ConfirmOrderResponse result = new ConfirmOrderResponse();

        //总价
        BigDecimal total = new BigDecimal(0);
        BigDecimal productPrice = null;
        for (Cart it : data.getCartList()) {
            //商品单价
            productPrice = new BigDecimal(String.valueOf(it.getProduct().getPrice()));

            //单价乘以数量
            total = total.add(productPrice.multiply(new BigDecimal(it.getCount())));
        }
        result.setTotalPrice(total.intValue());

        result.setCarts(data.getCartList());

        return result;
    }

    /**
     * 根据确认订单信息，还有订单请求对象创建订单
     *
     * @param confirmOrderResponse 主要是获取商品，价格等信息
     * @param data                 主要是获取订单来源
     * @param userId
     * @return
     */
    public Order create(ConfirmOrderResponse confirmOrderResponse, OrderRequest data, String userId) {
        //直接创建订单
        //因为这里实现的是，类似淘宝，京东实物商品，可以购买多次
        //每次都是创建一个新订单
        //如果要实现类似腾讯课堂的课程，只能购买一次
        //就是先查询订单，如果有就不创建
        //创建订单
        Order order = new Order();

        //设置用户
        order.setUserId(userId);

        //设置商品信息
        order.setProduct(JSONUtil.toJSON(confirmOrderResponse.getCarts()));

        //订单号
        order.setNumber(IDUtil.getOrderNumber());

        //设置其他数据
        //价格，一定要从服务端获取
        //传递的数据不可信
        order.setTotalPrice(confirmOrderResponse.getTotalPrice());
        order.setPrice(confirmOrderResponse.getPrice());

        //订单来源
        order.setSource(data.getSource());

        //地址
        order.setAddr(JSONUtil.toJSON(data.getAddress()));

        if (data.getCoupon() != null) {
            //优惠券信息
            order.setCoup(JSONUtil.toJSON(data.getCoupon()));

            //将优惠券标记为已经使用
            //如果用户未支付手动取消了订单，或者超时后自动取消了，优惠券再变为未使用
            couponService.changeUsedStatus(data.getCoupon().getId(), Constant.VALUE10);
        }

        create(order);

        return order;
    }

    /**
     * 关闭超时订单
     */
    @Transactional
    public void closeTimeoutOrder() {
        List<Order> datum = mapper.findAllByTimeout();

        if (CollectionUtils.isNotEmpty(datum)) {
            Coupon coupon;
            for (Order data : datum) {
                //设置状态为关闭
                data.setStatus(Constant.CLOSE);
                update(data);

                //判断是否有优惠券
                coupon = data.getCoupon();
                if (coupon != null) {
                    couponService.changeUsedStatus(coupon.getId(), Constant.VALUE0);
                }
            }
        }
    }
}
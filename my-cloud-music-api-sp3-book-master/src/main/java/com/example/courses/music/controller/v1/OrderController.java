package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.service.*;
import com.github.pagehelper.PageInfo;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.Cart;
import com.example.courses.music.model.Order;
import com.example.courses.music.model.Product;
import com.example.courses.music.model.request.OrderRequest;
import com.example.courses.music.model.response.ConfirmOrderResponse;
import com.example.courses.music.service.*;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.R;
import com.example.courses.music.util.ValidatorUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    @Autowired
    private OrderService service;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CouponService couponService;

    /**
     * 订单列表
     *
     * @param status 状态，取值就是订单状态，-1表示所有
     * @return
     */
    @GetMapping
    public Object index(@RequestParam(defaultValue = "-1") int status,
                        @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size) {
        StpUtil.checkLogin();

        PageInfo<Order> r = service.findByUserIdAndStatus(page,size,StpUtil.getLoginIdAsString(), status);

        //将cart字段的值，解析为对象
        r.getList().stream().map(order -> {
            order.parseCart();
            return order;
        }).collect(Collectors.toList());

        return R.wrap(r);
    }

    /**
     * 订单确认
     * <p>
     * 这一步，不会创建订单，而是根据用户传递的商品id，收货地址，优惠券id，计算价格
     * 目的是让用户确认
     *
     * @param data
     * @return
     */
    @PostMapping("/confirm")
    public Object confirmOrder(@Valid @RequestBody OrderRequest data,
                               BindingResult bindingResult) {
        validOrderRequest(data, bindingResult);

        ConfirmOrderResponse result = realConfirmOrder(data);

        return R.wrap(result);
    }

    private ConfirmOrderResponse realConfirmOrder(OrderRequest data) {
        ConfirmOrderResponse result;
        if (StringUtils.isNotBlank(data.getProductId())) {
            //直接购买一个商品
            result = service.confirmOrderByProduct(data);
        } else {
            //通过购物车购买
            result = service.confirmOrderByCarts(data);
        }

        if (data.getAddress() != null) {
            result.setAddress(data.getAddress());
        }

        if (result.getAddress() == null) {
            //查询用户默认地址
            result.setAddress(addressService.findByUserIdAndDefault(StpUtil.getLoginIdAsString()));
        }

        //处理优惠券逻辑
        if (data.getCoupon() != null) {
            //有优惠券，走到这里，表示当前优惠券有效，但不一定满足金额条件
            //所以这里还要判断
            double conditionPrice = data.getCoupon().getCouponActivity().getCondition();
            if (conditionPrice == 0 || result.getTotalPrice() >= conditionPrice) {
                //0无门槛优惠券；或者订单总金额大于等于优惠券使用条件金额

                //还需要支付金额=总价-优惠券优惠金额
                BigDecimal totalPrice = new BigDecimal(String.valueOf(result.getTotalPrice()));
                BigDecimal price = totalPrice.subtract(new BigDecimal(String.valueOf(data.getCoupon().getCouponActivity().getPrice())));
                result.setPrice(price.intValue());

                result.setCoupon(data.getCoupon());
            } else {
                //不满足使用条件
                throw new CommonException(Constant.ERROR_COUPON_NOT_AVAILABLE, Constant.ERROR_COUPON_NOT_AVAILABLE_MESSAGE);
            }
        } else {
            //没有优惠券

            //实际付款就是总价
            result.setPrice(result.getTotalPrice());
        }

        //可用的优惠券
        result.setCoupons(couponService.findAllByAvailable(StpUtil.getLoginIdAsString(), result.getPrice()));

        return result;
    }

    /**
     * 创建
     *
     * @param data
     * @param bindingResult
     * @return
     */
    @PostMapping
    public Object create(@Valid @RequestBody OrderRequest data,
                         BindingResult bindingResult) {
        validOrderRequest(data, bindingResult);

        //调用确认订单的方法，完成相关计算（优惠券，邮费等）
        ConfirmOrderResponse result = realConfirmOrder(data);

        //必须有地址，当然真实项目中还需要判断该商品是否需要地址等情况
        if (result.getAddress() == null) {
            throw new ArgumentException();
        }

        return R.wrap(service.create(result, data, StpUtil.getLoginIdAsString()));
    }

    /**
     * 验证请求参数
     * <p>
     * 因为订单是关系到钱相关的，所以真实项目中必须严格判断
     * 虽然有些条件正常使用不会遇到，但严格判断就是防止非法攻击的人
     * <p>
     * 提示：不要觉得验证参数这些比较复杂，真实项目中可能还需要判断更多
     * 越重要的功能更应该验证多种情况
     *
     * @param data
     * @param bindingResult
     */
    private void validOrderRequest(OrderRequest data, BindingResult bindingResult) {
        StpUtil.checkLogin();

        ValidatorUtil.checkParam(bindingResult);

        //判断订单来源是否正确
        if (Constant.WEB != data.getSource() &&
                Constant.ANDROID != data.getSource() &&
                Constant.IOS != data.getSource() &&
                Constant.WAP != data.getSource()) {
            throw new ArgumentException();
        }

        //如果传递了地址id，查询地址
        if (StringUtils.isNotBlank(data.getAddressId())) {
            data.setAddress(addressService.findByIdAndUserId(data.getAddressId(), StpUtil.getLoginIdAsString()));
        }

        //如果传递了优惠券id，查询优惠券
        if (StringUtils.isNotBlank(data.getCouponId())) {
            data.setCoupon(couponService.findByIdAndUserIdAndValid(data.getCouponId(), StpUtil.getLoginIdAsString()));

            //如果没有查询到，提示优惠券错误
            if (data.getCoupon() == null) {
                throw new CommonException(Constant.ERROR_COUPON_NOT_EXIST, Constant.ERROR_COUPON_NOT_EXIST_MESSAGE);
            }
        }

        if (StringUtils.isNotBlank(data.getProductId())) {
            //直接购买一个商品

            //判断商品是否存在
            //目的是通过商品获取价格，防止客户端传递价格
            //当然服务端商品价格有问题，那又是另外一种情况了
            Product product = productService.find(data.getProductId());
            ValidatorUtil.checkExist(product);

            //把查询到的数据，设置到请求对象，后面处理直接使用
            data.setProduct(product);
        } else if (CollectionUtils.isNotEmpty(data.getCarts())) {
            //通过购物车购买

            //判断购物车id是否都存在
            //目的是防止客户端随便传递id，导致程序发生不可预期错误
            List<String> cartIds = data.getCarts();
            List<Cart> carts = cartService.findByIds(cartIds);

            if (cartIds.size() != carts.size()) {
                //不登录，可能是购物车id重复
                throw new ArgumentException();
            }

            data.setCartList(carts);
        } else {
            throw new ArgumentException();
        }
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Object show(@PathVariable String id) {
        StpUtil.checkLogin();

        Order data = service.findByIdAndUserId(id, StpUtil.getLoginIdAsString());

        ValidatorUtil.checkExist(data);

        data.parseCart();

        return R.wrap(data);
    }

    /**
     * 取消订单
     *
     * @return
     */
    @PatchMapping("/{id}/cancel")
    public @ResponseBody
    Object update(@PathVariable String id, @RequestBody Order data) {
        StpUtil.checkLogin();

        //查询订单
        Order old = service.findByIdAndUserId(id, StpUtil.getLoginIdAsString());
        ValidatorUtil.checkExist(old);

        //判断状态，只有待支付时才能取消
        if (!old.isWaitPay()) {
            throw new CommonException(Constant.ERROR_ORDER_STATUS, Constant.ERROR_ORDER_STATUS_MESSAGE);
        }

        old.setStatus(Constant.CLOSE);
        service.update(old);

        //如果该订单有优惠券，并且优惠券状态还有效，将状态改为未使用
        if (old.getCoupon() != null && old.getCoupon().isAvailable()) {
            couponService.changeUsedStatus(old.getCoupon().getId(), Constant.VALUE0);
        }

        return R.wrap();
    }
}

package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.model.Cart;
import com.example.courses.music.service.CartService;
import com.example.courses.music.util.R;
import com.example.courses.music.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 购物车控制器
 */
@RestController
@RequestMapping("/v1/carts")
public class CartController {
    @Autowired
    private CartService service;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping
    public Object index() {
        StpUtil.checkLogin();

        List<Cart> datum = service.findByUserId(StpUtil.getLoginIdAsString());

        return R.wrap(datum);
    }

    /**
     * 创建
     *
     * @param data
     * @param bindingResult
     * @return
     */
    @PostMapping
    public Object create(@Valid @RequestBody Cart data,
                         BindingResult bindingResult) {
        StpUtil.checkLogin();

        ValidatorUtil.checkParam(bindingResult);

        //设置用户id
        data.setUserId(StpUtil.getLoginIdAsString());

        //查询该商品的购物车项是否存在，如果存在就是增加数量
        Cart old = service.findByUserIdAndProductId(StpUtil.getLoginIdAsString(), data.getProductId());
        if (old == null) {
            data.setCount(1);
            service.create(data);
            return R.wrap(data.getId());
        } else {
            old.setCount(old.getCount() + 1);
            validaCount(old);
            service.update(old);
            return R.wrap(old.getId());
        }
    }

    /**
     * 数量不能大于100
     * <p>
     * 主要是防止恶意下单，导致没有库存了
     * 真实项目中，可能还需要进行多方位的风险判断
     *
     * @param data
     */
    private void validaCount(Cart data) {
        if (data.getCount() > 100 || data.getCount() <= 0) {
            throw new ArgumentException();
        }
    }

    /**
     * 更新
     *
     * @param id
     * @param data
     * @return
     */
    @PatchMapping("/{id}")
    public Object update(@PathVariable String id, @RequestBody Cart data) {
        StpUtil.checkLogin();

        //设置id
        data.setId(id);

        //设置用户id
        data.setUserId(StpUtil.getLoginIdAsString());

        validaCount(data);

        //更新
        service.updateCount(data);

        return R.wrap(data.getId());
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Object destroy(@PathVariable String id) {
        StpUtil.checkLogin();

        //就算不存在，也不会返回错误，如果需要返回错误，像删除歌单那样增加判断
        service.deleteByIdAndUserId(id, StpUtil.getLoginIdAsString());

        return R.wrap();
    }

    /**
     * 批量删除购物车
     *
     * @return
     */
    @PostMapping("/batch_delete")
    public Object batchDelete(@RequestBody List<String> data) {
        StpUtil.checkLogin();

        service.batchDelete(data, StpUtil.getLoginIdAsString());

        return R.wrap();
    }

}

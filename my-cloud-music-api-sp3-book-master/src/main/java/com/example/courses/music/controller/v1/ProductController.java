package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.model.Product;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.service.CouponActivityService;
import com.example.courses.music.service.ProductService;
import com.example.courses.music.util.R;
import com.example.courses.music.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 商品控制器
 */
@RestController
@RequestMapping("/v1/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @Autowired
    private CouponActivityService couponActivityService;

    /**
     * 列表
     *
     * @param page 最后一条数据id，如果为空，就是从最新数据开始，之所以要实现根据id分页
     * @param size    每页显示多少条
     * @param order   排序；0：综合（商品新增时间倒序），默认；10：价格降序，20：价格倒序，
     *                  30：根据id从小到大排序，主要是方便客户端测试加载逻辑是否有问题，判断是否重复数据
     * @param brand  品牌，多个品牌，用逗号分割
     * @return
     */
    @GetMapping
    public Object index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "0") int order,
                        @RequestParam(name = "query", required = false) String query,
                        @RequestParam(name = "brand", required = false) String brand) {

        if (order == 100) {
            //方便客户端测试，该接口出错了
            throw new ArgumentException();
        }

        return R.wrap(service.findAll(page, size, order,query, brand));
    }

    /**
     * 创建
     *
     * @param data
     * @param bindingResult
     * @return
     */
    @PostMapping
    public Object create(@Valid @RequestBody Product data,
                         BindingResult bindingResult) {
        StpUtil.checkLogin();

        ValidatorUtil.checkParam(bindingResult);

        //设置用户id
        data.setUserId(StpUtil.getLoginIdAsString());

        //保存数据
        service.create(data);

        return R.wrap(data);
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    public Object show(@PathVariable("id") String id) {
        Product data = service.find(id);

        //查询所有没有失效活动
        data.setCouponActivities(couponActivityService.findAllByValid(null));

        return R.wrap(data);
    }
}
package com.example.courses.music.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.courses.music.mapper.ProductMapper;
import com.example.courses.music.model.Product;
import com.example.courses.music.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 商品服务
 */
@Service
public class ProductService {
    @Autowired
    private ProductMapper mapper;

    public PageInfo<Product> findAll(int page, int size, int order, String query, String brand) {

        List<String> brands = null;
        //品牌
        if (StringUtils.isNotBlank(brand)) {
            //真实商城中，品牌要像歌单详情标签那样实现为多级
            //例如：
//                数码
//                    手机
//                        华为
//                        小米
//                    电脑
//                        Dell
//                        联想
//                食品

            //逗号拆分
            brands = Arrays.asList(brand.split(Constant.SEPARATOR_COMMA));
        }

        //排序

        //排序字段
        String orderField;

        //升序还是降序
        String orderRule;
        if (Constant.VALUE10 == order) {
            orderField = "price";
            orderRule = Constant.DESC;
        } else if (Constant.VALUE20 == order) {
            orderField = "price";
            orderRule = Constant.ASC;
        } else if (Constant.VALUE30 == order) {
            orderField = "id";
            orderRule = Constant.ASC;
        } else {
            orderField = "created_at";
            orderRule = Constant.DESC;
        }

        //使用MyBatis分页插件实现分页
        //表示从page页开始，每页size条数据
        PageHelper.startPage(page, size);

        List<Product> results = mapper.findAll(orderField, orderRule, query, brands);

        //PageInfo是框架提供的对象
        //这里面有分页数据
        //例如多少页
        PageInfo<Product> pageInfo = new PageInfo<>(results);

        return pageInfo;
    }

    public void create(Product data) {
        mapper.insert(data);
    }

    public Product find(String data) {
        return mapper.selectById(data);
    }

    public List<Product> findAll4() {
        return findAll(1,4,0,null,null).getList();
    }
}
package com.example.courses.music.controller.v1;

import com.example.courses.music.model.Ad;
import com.example.courses.music.model.Button;
import com.example.courses.music.model.response.IndexResponse;
import com.example.courses.music.service.AdService;
import com.example.courses.music.service.ProductService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页控制器
 */
@RestController
@RequestMapping("/v1/indexes")
public class IndexController {

    @Autowired
    AdService adService;

    @Autowired
    private ProductService productService;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping
    public Object index() {
        IndexResponse r = new IndexResponse();

        //查询轮播图
        List<Ad> result = adService.findAll(0);
        r.setBanners(result);

        //快捷按钮
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(
                new Button(
                        "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/b1749080cf5bbc4dfebff83013bbebaf.png",
                        "http://www.ixuea.com/courses"
                )
        );

        buttons.add(
                new Button(
                        "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/9ea68dee2bfa0e55a82236b0d968e975.png",
                        "http://www.ixuea.com/books"
                )
        );

        buttons.add(
                new Button(
                        "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/69c250436545049ccab81c3e32033cf2.png"
                )
        );

        buttons.add(
                new Button(
                        "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/9ea68dee2bfa0e55a82236b0d968e975.png"
                )
        );

        buttons.add(
                new Button(
                        "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/9ea68dee2bfa0e55a82236b0d968e975.png"
                )
        );

        buttons.add(
                new Button(
                        "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/69c250436545049ccab81c3e32033cf2.png"
                )
        );


        buttons.add(
                new Button(
                        "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/9ea68dee2bfa0e55a82236b0d968e975.png"
                )
        );

        buttons.add(
                new Button(
                        "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/b1749080cf5bbc4dfebff83013bbebaf.png"
                )
        );

        buttons.add(
                new Button(
                        "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/9425031cdd7af22d9a23a5ae16d1f57c.jpg"
                )
        );

        buttons.add(
                new Button(
                        "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/9425031cdd7af22d9a23a5ae16d1f57c.jpg"
                )
        );

        r.setButtons(buttons);

        //热门商品
        r.setHots(productService.findAll(1, 5, 0,null, null).getList());

        //最新商品
        r.setNews(productService.findAll(1, 5, 0,null, null).getList());

        return R.wrap(r);
    }

}

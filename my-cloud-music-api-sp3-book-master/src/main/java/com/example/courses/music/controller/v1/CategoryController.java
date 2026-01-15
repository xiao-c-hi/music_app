package com.example.courses.music.controller.v1;

import com.example.courses.music.model.Category;
import com.example.courses.music.service.CategoryService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping
    public Object index(@RequestParam(required = false) String parent) {
        List<Category> r = service.findAll(parent);
        if (parent != null) {
            for (Category it:r) {
                it.setData(service.findAll(it.getId()));
            }
        }

        return R.wrap(r);
    }
}

package com.example.courses.music.controller.v1;

import com.example.courses.music.model.Ad;
import com.example.courses.music.service.AdService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 广告控制器
 */
@RestController
@RequestMapping("/v1/ads")
public class AdController {

    @Autowired
    AdService service;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping
    public Object index(@RequestParam(defaultValue = "0") int position) {
        List<Ad> result = service.findAll(position);

        return R.wrap(result);
    }
}

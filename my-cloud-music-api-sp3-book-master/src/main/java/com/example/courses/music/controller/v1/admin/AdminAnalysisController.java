package com.example.courses.music.controller.v1.admin;

import com.example.courses.music.model.Analysis;
import com.example.courses.music.service.AnalysisService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 后台分析控制器
 */
@RestController
@RequestMapping("/v1/admins/analyses")
public class AdminAnalysisController {

    @Autowired
    AnalysisService service;

    /**
     * 详情
     *
     * @return
     */
    @RequestMapping
    public Object show() {
        Analysis result = service.show();

        //返回数据
        return R.wrap(result);
    }

}

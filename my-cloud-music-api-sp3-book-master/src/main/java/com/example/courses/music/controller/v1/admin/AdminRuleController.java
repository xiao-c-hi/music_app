package com.example.courses.music.controller.v1.admin;

import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.model.Rule;
import com.example.courses.music.service.RuleService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台权限规则（菜单）控制器
 */
@RestController
@RequestMapping("/v1/admins/rules")
public class AdminRuleController {

    @Autowired
    RuleService service;

    /**
     * 返回第一级，第二级数据
     *
     * @return
     */
    @GetMapping
    public Object index() {
        StpUtil.checkLogin();

        List<Rule> result = service.findAllByUserId(StpUtil.getLoginIdAsString());

        return R.wrapNoPage(result);
    }

}

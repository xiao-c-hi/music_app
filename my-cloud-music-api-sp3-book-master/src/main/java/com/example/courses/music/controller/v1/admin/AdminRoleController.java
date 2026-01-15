package com.example.courses.music.controller.v1.admin;

import com.example.courses.music.model.Role;
import com.example.courses.music.service.RoleService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台角色控制器
 */
@RestController
@RequestMapping("/v1/admins/roles")
public class AdminRoleController {

    @Autowired
    RoleService service;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping
    public Object index() {
        List<Role> result = service.findAll();

        return R.wrapNoPage(result);
    }

}

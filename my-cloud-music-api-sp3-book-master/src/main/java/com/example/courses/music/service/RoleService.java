package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.courses.music.mapper.RoleMapper;
import com.example.courses.music.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色服务
 */
@Service
public class RoleService {
    private static Logger log = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleMapper mapper;

    public List<Role> findRulesByName(List<String> data) {
        List<Role> list = mapper.selectList(new LambdaQueryWrapper<Role>().in(Role::getValue, data));

        return list;
    }

    public List<Role> findAll() {
        List<Role> list = mapper.selectList(null);

        return list;
    }
}
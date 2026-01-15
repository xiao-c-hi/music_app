package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.courses.music.mapper.RoleMapper;
import com.example.courses.music.mapper.RuleMapper;
import com.example.courses.music.model.Role;
import com.example.courses.music.model.Rule;
import com.example.courses.music.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 规则服务
 */
@Service
public class RuleService {
    private static Logger log = LoggerFactory.getLogger(RuleService.class);

    @Autowired
    private RuleMapper mapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RedisStringService redisStringService;

    public IPage<Rule> findAll(int page, int size) {
        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Rule::getSort);

        return mapper.selectPage(new Page<Rule>(page, size), queryWrapper);
    }

    public List<Rule> findAll(List<String> rules) {
        return mapper.findAll(rules);
    }

    /**
     * 获取所有最后一级权限规则
     * @return
     */
    public List<Rule> findAllByUriNotNull() {
        //先从redis获取，目的是提交效率，因为该方法每次请求都会调用
//        List<Rule> cacheData = redisStringService.findObject(Constant.KEY_RULES, new TypeReference<List<Rule>>() {});
//        if (!CollectionUtils.isEmpty(cacheData)) {
//            return cacheData;
//        }

        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNotNull(Rule::getUri);

        List<Rule> results = mapper.selectList(queryWrapper);

        //更新到缓存
        redisStringService.updateObject(Constant.KEY_RULES,results);

        return results;
    }

    public List<Rule> findAllByRoleNames(List<String> roleNames) {
        //查询角色
        List<Role> roles = roleMapper.selectList(new LambdaQueryWrapper<Role>().in(Role::getValue, roleNames));

        //获取所有规则
        HashSet<String> ruleNames = new HashSet<>();
        for (Role role : roles) {
            ruleNames.addAll(Arrays.asList(role.getRules().split(Constant.SEPARATOR_COMMA)));
        }

        return findAll(new ArrayList<>(ruleNames));
    }

    /**
     * 查询用户拥有的权限规则（菜单）
     * @param data
     * @return
     */
    public List<Rule> findAllByUserId(String data) {
        //先查询第一级
        List<Rule> results=findAllByParentIsNull(data);

        //查询第二级
        for (Rule it:results) {
            it.setChildren(findAllByParentId(it.getId()));
        }

        return results;
    }

    private List<Rule> findAllByParentId(String data) {
        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Rule::getParentId,data);

        List<Rule> results = mapper.selectList(queryWrapper);

        return results;
    }

    private List<Rule> findAllByParentIsNull(String data) {
        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNull(Rule::getParentId);

        List<Rule> results = mapper.selectList(queryWrapper);

        return results;
    }
}
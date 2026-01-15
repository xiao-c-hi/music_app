package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Rule;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 规则（权限，菜单）映射
 */
@Repository
public interface RuleMapper extends BaseMapper<Rule> {
    /**
     * 所有权限，根据最后一级规则名称查询权限，返回的三级结构
     *
     * @param datum
     * @return
     */
    List<Rule> findAll(List<String> datum);
}

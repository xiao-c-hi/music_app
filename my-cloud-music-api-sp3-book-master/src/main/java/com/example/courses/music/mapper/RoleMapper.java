package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色映射
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> findAll();
}

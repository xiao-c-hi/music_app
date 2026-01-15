package com.example.courses.music.config.token;

import cn.dev33.satoken.stp.StpInterface;
import com.example.courses.music.model.Role;
import com.example.courses.music.service.RoleService;
import com.example.courses.music.service.UserService;
import com.example.courses.music.util.Constant;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 */
// 打开此注解，保证此类被springboot扫描，即可完成sa-token的自定义权限验证扩展
@Configuration
public class MyStpInterface implements StpInterface {
    private static final Logger log = LoggerFactory.getLogger(MyStpInterface.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleService ruleService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        //先获取角色表示符号
        List<String> roleNames = getRoleList(loginId, loginType);
        if (CollectionUtils.isNotEmpty(roleNames)) {
            Set<String> rules = new HashSet<>();

            //查询拥有的规则（权限）
            List<String> rulesString = ruleService.findRulesByName(roleNames).stream().map(new Function<Role, String>() {
                @Override
                public String apply(Role role) {
                    return role.getRules();
                }
            }).collect(Collectors.toList());

            for (String rule : rulesString) {
                rules.addAll(Arrays.asList(rule.split(Constant.SEPARATOR_COMMA)));
            }

            log.info("getPermissionList {} {} {} {}", loginId, loginType, rulesString, rules);

            if (CollectionUtils.isNotEmpty(rules)) {
                return new ArrayList(rules);
            }
        }

        return null;
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return userService.findRoleNamesByUserId((String) loginId);
    }

}
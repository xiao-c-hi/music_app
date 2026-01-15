package com.example.courses.music.controller.v1.admin;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.User;
import com.example.courses.music.service.RoleService;
import com.example.courses.music.service.UserService;
import com.example.courses.music.util.BCryptUtil;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.R;
import com.example.courses.music.util.ValidatorUtil;
import com.example.courses.music.service.*;
import com.example.courses.music.util.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 后台用户控制器
 */
@RestController
@RequestMapping("/v1/admins/users")
public class AdminUserController {

    @Autowired
    UserService service;

    @Autowired
    RoleService roleService;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping
    public Object index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "user.created_at") String field,
                        @RequestParam(defaultValue = "desc") String order,
                        @RequestParam(required = false) String id,
                        @RequestParam(required = false) String nickname,
                        @RequestParam(required = false) String phone,
                        @RequestParam(required = false) String email
    ) {

//        StpUtil.checkRole("admin");
//        StpUtil.checkPermission("user:list");

        User param = new User();
        param.setId(id);
        param.setNickname(nickname);
        param.setPhone(phone);
        param.setEmail(email);

        IPage<User> result = service.findAll(param, page, size, field, order);

        //查询用户角色名称等信息
        if (CollectionUtils.isNotEmpty(result.getRecords())) {
            List<String> roleList;
            for (User it:result.getRecords()) {
               roleList = it.getRoleList();
                if (CollectionUtils.isNotEmpty(roleList)) {
                    it.setRoles(roleService.findRulesByName(roleList));
                }
            }
        }

        return R.wrap(result);
    }

    /**
     * 添加
     *
     * @param data
     * @return
     */
    @PostMapping
    public Object create(@Valid @RequestBody User data, BindingResult bindingResult) {
        //参数校验
        ValidatorUtil.checkParam(bindingResult);

        //判断用户是否已经存在
        if (service.findByEmailOrPhone(data.getEmail(),data.getPhone())!=null) {
            throw new CommonException(Constant.ERROR_DATA_EXIST, Constant.ERROR_DATA_EXIST_MESSAGE);
        }

        //加密密码
        data.setPassword(BCryptUtil.encrypt(data.getPassword()));

        //设置角色信息
        if (CollectionUtils.isNotEmpty(data.getRoleList())) {
            data.setRole(data.getRoleListString());
        }

        if (!service.create(data)) {
            throw new CommonException(Constant.ERROR_SAVE_DATA, Constant.ERROR_SAVE_DATA_MESSAGE);
        }

        //返回用户对象
        return R.wrap(data.getId());
    }

    /**
     * 用户详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    public Object show(@PathVariable("id") String id,
                       @RequestParam(value = "nickname", required = false) String nickname) {
        User data = service.findDetail(id, nickname);

        //返回数据
        return R.wrap(data);
    }

    /**
     * 更新用户资料
     * <p>
     * 如果有更新用户资料权限，就能更新所有用户，否则就是更新当前登录用户
     *
     * @return
     */
    @PatchMapping("/{id}")
    public @ResponseBody
    Object update(@PathVariable String id, @RequestBody User data) {
        //检验当前会话是否已经登录, 如果未登录，则抛出异常NotLoginException
        StpUtil.checkLogin();

        User user = service.find(id);

        if (StringUtils.isNotBlank(data.getIcon())) {
            user.setIcon(data.getIcon());
        }

        if (StringUtils.isNotBlank(data.getNickname())) {
            user.setNickname(data.getNickname());
        }

        if (data.getGender()!=null) {
            user.setGender(data.getGender());
        }

        if (StringUtils.isBlank(data.getPhone())) {
            user.setPhone(data.getPhone());
        }

        if (StringUtils.isBlank(user.getEmail())) {
            user.setEmail(data.getEmail());
        }

        if (StringUtils.isBlank(data.getPassword())) {
            user.setPassword(BCryptUtil.encrypt(data.getPassword()));
        }

        if (data.getStatus()!=null) {
            user.setStatus(data.getStatus());
        }

        if (CollectionUtils.isNotEmpty(data.getRoleList())) {
            user.setRole(data.getRoleListString());
        }

        service.update(user);

        return R.wrap();
    }



    /**
     * 检查用户是否存在
     *
     * @return
     */
    @PostMapping("/exist")
    public Object exist(@RequestBody User data) {
        User old=service.findByPhone(data.getPhone());
        if (old!=null) {
            return R.failed(Constant.ERROR_EXIST_PHONE,Constant.ERROR_EXIST_PHONE_MESSAGE);
        }

        old=service.findByEmail(data.getEmail());
        if (old!=null) {
            return R.failed(Constant.ERROR_EXIST_EMAIL,Constant.ERROR_EXIST_EMAIL_MESSAGE);
        }

        return R.wrap();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Object destroy(@PathVariable String id) {
        StpUtil.checkLogin();

        User data = service.find(id);
        ValidatorUtil.checkExist(data);

        if (data.getRole()!=null && data.getRoleList().contains(Constant.ROLE_ADMIN)) {
            //不能删除有管理员角色的人
            //当然真实项目中，可以还需要给角色设置等级
            //这样就可以实现只有高等级角色删除低等级角色的用户
            throw new NotPermissionException(Constant.ROLE_ADMIN);
        }

        service.delete(id);

        return R.wrap();
    }
}

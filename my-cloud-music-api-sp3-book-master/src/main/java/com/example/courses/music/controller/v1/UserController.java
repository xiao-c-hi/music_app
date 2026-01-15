package com.example.courses.music.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.Account;
import com.example.courses.music.model.User;
import com.example.courses.music.service.CodeImageService;
import com.example.courses.music.service.CodeService;
import com.example.courses.music.service.SheetService;
import com.example.courses.music.service.UserService;
import com.example.courses.music.util.*;
import com.example.courses.music.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    CodeService codeService;

    @Autowired
    SheetService sheetService;

    @Autowired
    CodeImageService codeImageService;

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
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false) String phone
    ) {

//        StpUtil.checkRole("admin");
//        StpUtil.checkPermission("user:list");

        User param = new User();
        param.setId(id);
        param.setNickname(name);
        param.setPhone(phone);

        IPage<User> result = service.findAll(param, page, size, field, order);

        return R.wrap(result);
    }

    /**
     * 注册
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

        //判断验证码是否相等
//        codeImageService.verify(data.getCode());

        //加密密码
        data.setPassword(BCryptUtil.encrypt(data.getPassword()));

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
     * 当前登陆用户详情
     *
     * @return
     */
    @RequestMapping("/current")
    public Object currentDetail() {
        StpUtil.checkLogin();

        User data = service.find(StpUtil.getLoginIdAsString());

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

        //获取登录用户id
        User loginUser = service.find(StpUtil.getLoginIdAsString());

        User user = null;
        if (false) {
            //有更新所有用户资料权限
            user = service.find(id);

            //防止客户端随便穿id
            ValidatorUtil.checkUser(user);
        } else {
            //更新自己
            user = loginUser;
        }

        if (StringUtils.isNotBlank(data.getNickname())) {
            user.setNickname(data.getNickname());
        }

        if (StringUtils.isNotBlank(data.getIcon())) {
            user.setIcon(data.getIcon());
        }

        if (StringUtils.isNotBlank(data.getDetail())) {
            user.setDetail(data.getDetail());
        }

        //如果被更新用户没有手机号，邮箱，密码，第一次可以更新，后续不能更新
        //当然这样实现有一些安全问题，真实项目中推荐新增/更新，手机号，邮箱用验证验证
        //类似手机号验证码登录那样实现，这里就不在实现了
//        if (StringUtils.isNotBlank(data.getPhone())) {
//            user.setPhone(data.getPhone());
//        }
//
//        if (StringUtils.isNotBlank(data.getEmail())) {
//            user.setEmail(data.getEmail());
//        }
//
//        if (StringUtils.isNotBlank(data.getPassword())) {
//            user.setPassword(BCryptUtil.encrypt(data.getPassword()));
//        }

        if (data.getGender()!=null) {
            user.setGender(data.getGender());
        }

        if (StringUtils.isNotBlank(data.getBirthday())) {
            user.setBirthday(data.getBirthday());
        }

        if (StringUtils.isNotBlank(data.getProvince())) {
            user.setProvince(data.getProvince());
            user.setProvinceCode(data.getProvinceCode());
            user.setCity(data.getCity());
            user.setCityCode(data.getCityCode());
            user.setArea(data.getArea());
            user.setAreaCode(data.getAreaCode());
        }

        user.setDetail(data.getDetail());

        service.update(user);

        return R.wrap();
    }

    /**
     * 重置密码
     *
     * @param data
     * @return
     */
    @PostMapping("/reset_password")
    public Object resetPassword(@RequestBody User data) {
        //判断参数
        if (StringUtils.isBlank(data.getPhone()) &&
                StringUtils.isBlank(data.getEmail())) {
            //手机号和邮件必须有一个有值

            //参数错误
            throw new ArgumentException();
        }

        //测试账号，禁止改密码
        if ("ixueaedu@163.com".equals(data.getEmail()) ||
        "13141111222".equals(data.getPhone()) ||
        "13141111333".equals(data.getPhone()) ||
        "13141111555".equals(data.getPhone())
        ) {
            throw new CommonException(Constant.ERROR_ARGUMENT, "测试账号，禁止更改密码，请自己注册账号测试");
        }

        if (StringUtils.isBlank(data.getCode()) ||
                StringUtils.isBlank(data.getPassword())) {
            //密码，或验证码为空
            throw new ArgumentException();
        }

        User user;
        String target;
        if (StringUtils.isNotBlank(data.getPhone())) {
            user = service.findByPhone(data.getPhone());
            target = data.getPhone();
        } else {
            user = service.findByEmail(data.getEmail());
            target = data.getEmail();
        }

        //判断是否找到了用户
        ValidatorUtil.checkUser(user);

        //不能更改为上次密码
        if (BCryptUtil.matchEncode(data.getPassword(), user.getPassword())) {
            throw new CommonException(Constant.ERROR_USE_LAST_PASSWORD, Constant.ERROR_USE_LAST_PASSWORD_MESSAGE);
        }

        //判断验证码
        codeService.checkAndDelete(target, data.getCode());

        //加密密码信息
        user.setPassword(BCryptUtil.encrypt(data.getPassword()));

        if (service.update(user) < Constant.RESULT_OK) {
            //更新数据库失败
            throw new ArgumentException();
        }

        return R.wrap();
    }

    /**
     * 绑定第三方账号
     *
     * @param data
     * @param bindingResult
     * @return
     */
    @PostMapping(path = "/bind")
    public Object bindAccount(@Valid @RequestBody Account data, BindingResult bindingResult) {
        StpUtil.checkLogin();

        //判断参数
        if (bindingResult.hasErrors()) {
            //参数错误
            throw new ArgumentException();
        }

        User user = service.find(StpUtil.getLoginIdAsString());

        //判断是否找到了用户
        ValidatorUtil.checkUser(user);

        //加密信息
        String accountDigest = AESUtil.encrypt(data.getAccount());

        //判断更新平台取值是否正确
        if (data.getPlatform() == Constant.PLATFORM_QQ) {
            user.setQqId(accountDigest);
        } else if (data.getPlatform() == Constant.PLATFORM_WECHAT) {
            user.setWechatId(accountDigest);
        } else {
            //参数错误
            throw new ArgumentException();
        }

        //更新用户
        service.update(user);

        return R.wrap(user.getId());
    }

    /**
     * 解绑第三方账号
     *
     * @param platform
     * @return
     */
    @DeleteMapping(path = "/{platform}/unbind")
    public Object unbindAccount(@PathVariable String platform) {
        StpUtil.checkLogin();

        //通过url传递过来都是字符串
        Integer platformInt = Integer.valueOf(platform);

        User user = service.find(StpUtil.getLoginIdAsString());

        //判断是否找到了用户
        ValidatorUtil.checkUser(user);

        //判断平台
        if (platformInt == Constant.PLATFORM_QQ) {
            checkBind(user.getQqId());
            user.setQqId(null);
        } else if (platformInt == Constant.PLATFORM_WECHAT) {
            checkBind(user.getWechatId());

            user.setWechatId(null);
        } else {
            //参数错误
            throw new ArgumentException();
        }

        //更新用户
        service.update(user);

        return R.wrap(user.getId());
    }

    /**
     * 检查第三方账号绑定状态
     */
    private void checkBind(String data) {
        if (StringUtils.isBlank(data)) {
            //没有绑定
            throw new ArgumentException();
        }
    }


    /**
     * 用户创建的歌单
     *
     * @return
     */
    @GetMapping("/{id}/create")
    public Object createSheets(@PathVariable String id) {
        return R.wrap(sheetService.findByUserId(id));
    }

    /**
     * 用户收藏的歌单
     *
     * @return
     */
    @GetMapping("/{id}/collect")
    public Object collectSheets(@PathVariable String id) {
        return R.wrap(sheetService.findCollectByUserId(id));
    }

    /**
     * 用户关注的人（好友）
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/following")
    public Object following(@PathVariable String id) {
        //查询数据
        List<User> datum = service.following(id);

        //返回数据
        return R.wrap(datum);
    }

    /**
     * 关注用户（我）的人（粉丝）
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/followers")
    public Object followers(@PathVariable String id) {
        //查询数据
        List<User> datum = service.followers(id);

        return R.wrap(datum);
    }
}

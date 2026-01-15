package com.example.courses.music.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.courses.music.mapper.UserMapper;
import com.example.courses.music.model.Friend;
import com.example.courses.music.model.SuggestItem;
import com.example.courses.music.model.User;
import com.example.courses.music.util.Constant;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户服务
 */
@Service
public class UserService {
    /**
     * 用户Elastic服务
     */
    @Autowired
    ElasticUserService elasticUserService;
    /**
     * 用户mapper
     */
    @Autowired
    private UserMapper mapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FriendService friendService;

    /**
     * 根据id查询
     *
     * @param data
     * @return
     */
    public User find(String data) {
        User user = mapper.selectById(data);

        return user;
    }

    /**
     * 根据id查询详情
     *
     * @param id
     * @param nickname
     * @return
     */
    public User findDetail(String id, String nickname) {
        User data;
        if ("-1".equals(id)) {
            //根据昵称查询用户
            data = findByNickname(nickname);
        } else {
            //查询数据
            data = mapper.selectById(id);
        }

        //判断是否关注了该用户
        if (StpUtil.isLogin()) {
            //只有登录了才判断关注状态

            Friend friend = friendService.findByFollowerIdAndFollowedId(StpUtil.getLoginIdAsString(), id);

            if (friend != null) {
                //关注了
                data.setFollowing(friend.getId());
            }
        }

        return data;
    }

    /**
     * 根据昵称查询
     *
     * @param nickname
     * @return
     */
    private User findByNickname(String nickname) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickname, nickname);

        //可能有多个，所以不能用selectOne方法查询
        List<User> results = mapper.selectPage(new Page<User>(1, 1), queryWrapper).getRecords();
        if (CollectionUtils.isNotEmpty(results)) {
            return results.get(0);
        }
        return null;
    }

    /**
     * 创建用户
     *
     * @param data
     * @return
     */
    public boolean create(User data) {
        //保存到数据
        mapper.insert(data);

        //更新到es
//        elasticUserService.update(data);

        return true;
    }

    /**
     * 更新
     *
     * @param data
     * @return
     */
    public int update(User data) {
        int result = mapper.updateById(data);

        //更新到es
//        elasticUserService.update(data);

        return result;
    }

    /**
     * 根据phone查询
     *
     * @param data
     * @return
     */
    public User findByPhone(User data) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, data.getPhone());

        User user = mapper.selectOne(queryWrapper);

//        findPermission(user);

        return user;
    }

    /**
     * 根据phone查询
     *
     * @param data
     * @return
     */
    public User findByPhone(String data) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, data);

        User user = mapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 根据email查询
     *
     * @param data
     * @return
     */
    public User findByEmail(String data) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, data);

        User user = mapper.selectOne(queryWrapper);
        return user;
    }

    public IPage<User> findAll(User data, int page, int size, String field, String order) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(data.getId()), "id", data.getId());
        queryWrapper.like(StringUtils.isNotEmpty(data.getNickname()), "nickname", data.getNickname());
        queryWrapper.eq(StringUtils.isNotEmpty(data.getPhone()), "phone", data.getPhone());
        queryWrapper.eq(StringUtils.isNotEmpty(data.getEmail()), "email", data.getEmail());

        queryWrapper.orderBy(StringUtils.isNotEmpty(field), order.equalsIgnoreCase("asc"), field);

        Page<User> result = mapper.selectPage(new Page<User>(page, size), queryWrapper);

        return result;
    }

    /*返回用户拥有的角色名称*/
    public List<String> findRoleNamesByUserId(String id) {
        User data = find(id);
        if (data == null) {
            return null;
        }

        return data.getRoleList();
    }

    /**
     * 根据邮箱，或者手机号查询
     *
     * @param email
     * @param phone
     * @return
     */
    public User findByEmailOrPhone(String email, String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email).or().eq(User::getPhone, phone);

        User user = mapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 查询直播间id不为空，直播间id倒序排序的用户
     *
     * @return
     */
    public User findByRoomIdNotNullOrderByRoomIdDesc() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNotNull(User::getRoomId).orderByDesc(User::getRoomId);

        User user = mapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 根据qqId，或者wechatId查询
     *
     * @param qqId
     * @param wechatId
     * @return
     */
    public User findByQQIdOrWechatId(String qqId, String wechatId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getQqId, qqId).or().eq(User::getWechatId, wechatId);

        User user = mapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 根据wechatId查询
     *
     * @param wechatId
     * @return
     */
    public User findByWechatId(String wechatId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getWechatId, wechatId);

        User user = mapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 用户关注的人（好友）
     *
     * @param data
     * @return
     */
    public List<User> following(String data) {
        return mapper.following(data);
    }

    /**
     * 关注用户（我）的人（粉丝）
     *
     * @param data
     * @return
     */
    public List<User> followers(String data) {
        return mapper.followers(data);
    }

    /**
     * 用户搜索
     *
     * @param page
     * @param query
     * @return
     */
    public IPage<User> search(Page<User> page, String query) {
        return mapper.search(page, query);
    }

    /**
     * 搜索建议
     *
     * @param query
     * @return
     */
    public List<SuggestItem> suggest(String query) {
        return mapper.suggest(query);
    }

    public void delete(String data) {
        mapper.deleteById(data);
    }

    /**
     * 总用户数
     * @return
     */
    public long usersCount() {
        return mapper.selectCount(null);
    }

    /**
     * 活跃用户数
     *
     * 这里就定义为最近一月登录的用户
     * 真实项目中根据需求实现就行了
     * @return
     */
    public long activeUsersCount() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        //当前日期减一月
        DateTime date = DateUtil.offsetMonth(new Date(), -1);

        queryWrapper.ge(User::getUpdatedAt, date);

        return mapper.selectCount(queryWrapper);
    }

    /**
     * 今天新增用户数
     * @return
     */
    public long dayUsersCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //拼接 sql
        //会有 sql 注入风险
        //最终sql：SELECT COUNT( * ) FROM user WHERE (to_days(updated_at) = to_days(now()))
        //to_days：是mysql中的函数，返回从年份0开始到now()现在的一个天数
        //例如：SELECT to_days(now());，就是0-0-0到现在的天数
        //SELECT to_days('0-1-2'); ，就是0-0-0到0-1-2的天数，为2天
        queryWrapper.apply("to_days(updated_at) = to_days(now())");

        return mapper.selectCount(queryWrapper);
    }

    /**
     * 上一天新增用户
     * @return
     */
    public long dayLastUsersCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //当前日期减1天
        DateTime date = DateUtil.offsetDay(new Date(), -1);

        //格式化为字符串
        String dateString = DateUtil.format(date, Constant.YEAR_MONTH_DAY);

        queryWrapper.apply("to_days(updated_at) = to_days('"+dateString+"')");

        return mapper.selectCount(queryWrapper);
    }

    public long weekUsersCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.apply("YEARWEEK(DATE_FORMAT(updated_at,'%Y-%m-%d'))=YEARWEEK(NOW())");

        return mapper.selectCount(queryWrapper);
    }

    public long lastWeekUsersCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //上周
        queryWrapper.apply("YEARWEEK(DATE_FORMAT(updated_at,'%Y-%m-%d'))=YEARWEEK(NOW())-1");

        return mapper.selectCount(queryWrapper);
    }

    public long monthUsersCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.apply("DATE_FORMAT(updated_at,'%Y%m')=DATE_FORMAT(NOW(),'%Y%m')");

        return mapper.selectCount(queryWrapper);
    }

    public long lastMonthUsersCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.apply("DATE_FORMAT(updated_at,'%Y%m')=DATE_FORMAT(NOW(),'%Y%m')");

        return mapper.selectCount(queryWrapper);
    }

    public User findByEmailConfirm(String data) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmailConfirm, data);

        return mapper.selectOne(queryWrapper);
    }
}
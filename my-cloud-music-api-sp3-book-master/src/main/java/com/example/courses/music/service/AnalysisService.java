package com.example.courses.music.service;

import com.example.courses.music.model.Analysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分析服务
 */
@Service
public class AnalysisService {
    @Autowired
    private UserService userService;

    /**
     * 返回基础统计数据
     * @return
     */
    public Analysis show() {
        Analysis result = new Analysis();

        //计算用户统计信息
        computerUserAnalysis(result);

        return result;
    }

    /**
     * 计算用户统计信息
     * @param result
     */
    private void computerUserAnalysis(Analysis result) {
        //用户数
        result.setUsersCount(userService.usersCount());

        //活跃用户数
        result.setActiveUsersCount(userService.activeUsersCount());

        //region 天统计
        //今天新增用户数
        result.setDayUsersCount(userService.dayUsersCount());

        //上一天新增用户
        result.setLastDayUsersCount(userService.dayLastUsersCount());
        //endregion

        //region 周统计
        result.setWeekUsersCount(userService.weekUsersCount());

        result.setLastWeekUsersCount(userService.lastWeekUsersCount());
        //endregion

        //region 月统计
        result.setMonthUsersCount(userService.monthUsersCount());

        result.setLastMonthUsersCount(userService.lastMonthUsersCount());
        //endregion
    }
}
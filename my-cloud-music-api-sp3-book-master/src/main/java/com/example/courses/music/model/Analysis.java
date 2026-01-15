package com.example.courses.music.model;

/**
 * 分析模型
 */
public class Analysis extends Common {
    /**
     * 天新增用户
     */
    private long dayUsersCount;

    /**
     * 天新增用户率
     */
    private int dayUsersCountRate;

    /**
     * 上一天新增用户
     *
     * 也就是环比，上一天
     * 同比：上一年这一天
     */
    private long lastDayUsersCount;

    /**
     * 周新增用户
     */
    private long weekUsersCount;

    /**
     * 周新增用户率
     */
    private int weekUsersCountRate;

    /**
     * 上周新增用户
     */
    private long lastWeekUsersCount;

    /**
     * 月新增用户
     */
    private long monthUsersCount;

    /**
     * 月新增用户率
     */
    private int monthUsersCountRate;

    /**
     * 上月新增用户
     */
    private long lastMonthUsersCount;

    /**
     * 总用户数
     */
    private long usersCount;

    /**
     * 活跃用户数
     */
    private long activeUsersCount;

    public int getDayUsersCountRate() {
        if (lastDayUsersCount == 0) {
            //如果上周期为0，可以不算增长率，或者为100%
            return 100;
        }
        return (int) (100 * (dayUsersCount-lastDayUsersCount) / lastDayUsersCount);
    }

    public long getDayUsersCount() {
        return dayUsersCount;
    }

    public void setDayUsersCount(long dayUsersCount) {
        this.dayUsersCount = dayUsersCount;
    }

    public void setDayUsersCountRate(int dayUsersCountRate) {
        this.dayUsersCountRate = dayUsersCountRate;
    }

    public long getLastDayUsersCount() {
        return lastDayUsersCount;
    }

    public void setLastDayUsersCount(long lastDayUsersCount) {
        this.lastDayUsersCount = lastDayUsersCount;
    }

    public long getWeekUsersCount() {
        return weekUsersCount;
    }

    public void setWeekUsersCount(long weekUsersCount) {
        this.weekUsersCount = weekUsersCount;
    }

    public int getWeekUsersCountRate() {
        if (lastWeekUsersCount == 0) {
            //如果上周期为0，可以不算增长率，或者为100%
            return 100;
        }
        return (int) (100 * (weekUsersCount-lastWeekUsersCount) / lastWeekUsersCount);
    }

    public void setWeekUsersCountRate(int weekUsersCountRate) {
        this.weekUsersCountRate = weekUsersCountRate;
    }

    public long getLastWeekUsersCount() {
        return lastWeekUsersCount;
    }

    public void setLastWeekUsersCount(long lastWeekUsersCount) {
        this.lastWeekUsersCount = lastWeekUsersCount;
    }

    public long getMonthUsersCount() {
        return monthUsersCount;
    }

    public void setMonthUsersCount(long monthUsersCount) {
        this.monthUsersCount = monthUsersCount;
    }

    public int getMonthUsersCountRate() {
        if (lastMonthUsersCount == 0) {
            //如果上周期为0，可以不算增长率，或者为100%
            return 100;
        }
        return (int) (100 * (monthUsersCount-lastMonthUsersCount) / lastMonthUsersCount);
    }

    public void setMonthUsersCountRate(int monthUsersCountRate) {
        this.monthUsersCountRate = monthUsersCountRate;
    }

    public long getLastMonthUsersCount() {
        return lastMonthUsersCount;
    }

    public void setLastMonthUsersCount(long lastMonthUsersCount) {
        this.lastMonthUsersCount = lastMonthUsersCount;
    }

    public long getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(long usersCount) {
        this.usersCount = usersCount;
    }

    public long getActiveUsersCount() {
        return activeUsersCount;
    }

    public void setActiveUsersCount(long activeUsersCount) {
        this.activeUsersCount = activeUsersCount;
    }
}
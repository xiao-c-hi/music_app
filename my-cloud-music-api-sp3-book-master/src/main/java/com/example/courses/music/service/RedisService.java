package com.example.courses.music.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * redis服务
 */
@Service
public class RedisService {
    /**
     * 日志api
     */
    private final static Logger log = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 删除数据
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 对key加1
     * <p>
     * 如果是第一次，先初始化为0，然后在加1
     *
     * @param key
     */
    public void increment(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param date
     */
    public void expireAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 获取key,int值，如果key不存在，返回
     *
     * @param key
     * @return
     */
    public int intValue(String key) {
        if (!redisTemplate.hasKey(key)) {
            return 0;
        }
        return (int) redisTemplate.opsForValue().get(key);
    }
}
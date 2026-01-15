package com.example.courses.music.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.util.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis string服务
 */
@Service
public class RedisStringService {
    /**
     * 日志api
     */
    private final static Logger log = LoggerFactory.getLogger(RedisStringService.class);

    /**
     * 专门用来存储值为字符串的redis客户端
     * 如果值是字符串，推荐使用这个
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查找字符串
     *
     * @param key
     * @return
     */
    public String findString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 更新字符串
     *
     * @param key
     * @param value
     */
    public void update(String key, String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("updateString error key {} {}", key, e);
            throw new CommonException(9940, "更新字符串失败！");
        }
    }

    /**
     * 更新字符串，并指定过期时间
     * <p>
     * 可以在redis命令行，通过pttl key查看剩余的毫秒；-1表示没有设置过期时间；-2表示key不存在
     *
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     * @return
     */
    public void update(String key, String value, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 删除数据
     *
     * @param key
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 查询对象
     *
     * @param key
     * @return
     */
    public <T> T findObject(String key, TypeReference<T> valueTypeRef) {
        String jsonString = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        return JSONUtil.parse(jsonString, valueTypeRef);
    }

    /**
     * 更新对象
     *
     * @param key
     * @param value
     */
    public void updateObject(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJSON(value));
    }

    /**
     * 更新对象，可以设置超时时间
     *
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    public void updateObject(String key, Object value, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJSON(value), timeout, timeUnit);
    }

    /**
     * 删除对象数据
     *
     * @param key
     */
    public void deleteObject(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 删除指定权限的所有key
     *
     * @param key
     */
    public void deletePrefix(String key) {
        //先查询所有key
        Set<String> keys = stringRedisTemplate.keys(key + "*");

        //批量删除
        stringRedisTemplate.delete(keys);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

}
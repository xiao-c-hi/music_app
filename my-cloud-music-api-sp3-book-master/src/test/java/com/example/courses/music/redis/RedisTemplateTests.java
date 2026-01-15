package com.example.courses.music.redis;


import com.example.courses.music.BaseTests;
import com.example.courses.music.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * redis模板测试
 */
public class RedisTemplateTests extends BaseTests {
    /**
     * 专门用来存储值为字符串的redis客户端
     * 如果值是字符串，推荐使用这个
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 可以操作所有类型
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 测试通过StringRedisTemplate操作redis
     */
    @Test
    public void testStringRedisTemplate() {
        //redis测试
        //设置字符串
        stringRedisTemplate.opsForValue().set("cache_string", "hello ixuea，爱学啊");

        //获取字符串
        String cacheString = stringRedisTemplate.opsForValue().get("cache_string");
        System.out.println("testRedisTemplate cacheString " + cacheString);
    }

    /**
     * 测试通过RedisTemplate操作redis
     */
    @Test
    public void testRedisTemplate() {
        //创建测试对象
        User data = new User();
        data.setId("123345234");
        data.setEmail("ixueaedu@163.com");
        data.setPhone("13141111222");
        data.setCity("成都");

        //设置对象
        //真实项目中不建议直接存储对象
        //建议转为json在以字符串的方式序列化
        //好处是，其他应用也可以很方便的复用
        //当然也可以自定义序列化器实现
        //后面会讲解
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("cacheUser", data);

        //获取对象
        //只有没有在RedisConfig里面setValueSerializer，setHashValueSerializer为json序列化才能直接转为对象
        //如果设置返回是map
        //推荐真实项目还是转为json按照字符串方式处理，因为这里可以方便在不同项目，不同语言中共享
        User cacheUser = (User) valueOperations.get("cacheUser");
        System.out.println("testRedisTemplate cacheUser " + cacheUser);

    }
}
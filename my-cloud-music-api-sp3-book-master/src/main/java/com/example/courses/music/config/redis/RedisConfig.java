package com.example.courses.music.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis配置类
 */
@Configuration
public class RedisConfig {
    /**
     * 命名空间
     */
    @Value("${spring.redis.namespace}")
    private String namespace;

    /**
     * 对象转换器
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 连接工厂
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    /**
     * 用来提供key是字符串，值可以是任意对象的 redis客户端
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //设置key序列化器
        //如果不设置
        //他会在key前面添加一些值
        setKeySerializer(redisTemplate);

        //设置值序列化器
        redisTemplate.setValueSerializer(jsonRedisSerializer());

        //设置map类型值序列化器
        redisTemplate.setHashValueSerializer(jsonRedisSerializer());

        return redisTemplate;
    }

    /**
     * json序列化器
     *
     * @return
     */
    private RedisSerializer<?> jsonRedisSerializer() {
        // 解决jackson2无法反序列化Date/LocalDateTime的问题
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    /**
     * 设置用来提供key是字符串，值也是字符串操作对象 客户端
     *
     * @return
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();

        //设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //设置key序列化器
        setKeySerializer(redisTemplate);

        return redisTemplate;
    }

    /**
     * 设置key序列化器
     *
     * @param redisTemplate
     */
    private void setKeySerializer(RedisTemplate redisTemplate) {
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
    }

    /**
     * 设置key序列化器
     *
     * @return
     */
    private RedisSerializer<?> keySerializer() {
        return new NamespaceStringRedisSerializer(namespace);
    }
}

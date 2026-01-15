package com.example.courses.music.config.redis;

import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 支持key命名空间的序列化器
 */
public class NamespaceStringRedisSerializer extends StringRedisSerializer {

    /**
     * 命名空间
     */
    private final String namespace;

    /**
     * 构造方法
     *
     * @param namespace
     */
    public NamespaceStringRedisSerializer(String namespace) {
        this.namespace = namespace;
    }

    /**
     * 序列化器
     *
     * @param string
     * @return
     */
    @Override
    public byte[] serialize(String string) {
        if (string.startsWith(namespace)) {
            //如果已经包含了命名空间，就不添加
            return super.serialize(string);
        }

        return super.serialize(String.format("%s%s", namespace, string));
    }
}

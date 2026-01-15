package com.example.courses.music.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.courses.music.AppContext;
import com.example.courses.music.exception.CommonException;

/**
 * json工具类
 */
public class JSONUtil {

    /**
     * 将对象转为json字符串
     *
     * @param data
     * @return
     */
    public static String toJSON(Object data) {
        //不能使用fastjson
        //因为我们返回数据的时候使用的jackjson
        //签名的时候也要使用jackjson
        //这样才能保证签名前，返回到客户端数据一样
        //当然都是用fastjson也是可以的
//        return JSON.toJSONString(data);

        //获取ObjectMapper
        //不能手动创建
        //因为我们要用默认的
        ObjectMapper objectMapper = AppContext.getBean(ObjectMapper.class);

        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new CommonException(Constant.ERROR_TO_JSON, Constant.ERROR_TO_JSON_MESSAGE);
        }
    }

    /**
     * json解析
     *
     * @param data
     * @return
     */
    public static <T> T parse(String data, TypeReference<T> valueTypeRef) {
        //不能使用fastjson
        //因为我们返回数据的时候使用的jackjson
        //签名的时候也要使用jackjson
        //这样才能保证签名前，返回到客户端数据一样
        //当然都是用fastjson也是可以的
//        return JSON.toJSONString(data);

        //获取ObjectMapper
        //不能手动创建
        //因为我们要用默认的
        ObjectMapper objectMapper = AppContext.getBean(ObjectMapper.class);

        try {
            return objectMapper.readValue(data, valueTypeRef);
        } catch (JsonProcessingException e) {
            throw new CommonException(Constant.ERROR_PARSE_JSON, Constant.ERROR_PARSE_JSON_MESSAGE);
        }
    }

    /**
     * json解析
     *
     * @param data
     * @return
     */
    public static <T> T parse(String data, Class<T> clazz) {
        ObjectMapper objectMapper = AppContext.getBean(ObjectMapper.class);

        try {
            return objectMapper.readValue(data, clazz);
        } catch (JsonProcessingException e) {
            throw new CommonException(Constant.ERROR_PARSE_JSON, Constant.ERROR_PARSE_JSON_MESSAGE);
        }
    }
}

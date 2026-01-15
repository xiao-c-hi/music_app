package com.example.courses.music.service;

import com.example.courses.music.exception.CommonException;
import com.example.courses.music.util.CacheKeyUtil;
import com.example.courses.music.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 验证码服务
 */
@Service
public class CodeImageService {
    private static Logger log = LoggerFactory.getLogger(CodeImageService.class);

    @Autowired
    RedisStringService redisStringService;

    /**
     * 校验验证码
     *
     * @param data
     */
    public void verify(String data) {
        //判断是否有验证码
        String key = CacheKeyUtil.codeDataKey(data);
        if (!redisStringService.hasKey(key)) {
            //不存在

            //提示验证码错误
            throw new CommonException(Constant.ERROR_CODE, Constant.ERROR_CODE_MESSAGE);
        }

        //删除图片验证码
        redisStringService.delete(key);
    }
}

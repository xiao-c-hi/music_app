package com.example.courses.music.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.Code;
import com.example.courses.music.util.CacheKeyUtil;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.MD5Util;
import com.example.courses.music.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务
 */
@Service
public class CodeService {
    private static Logger log = LoggerFactory.getLogger(CodeService.class);

    private RedisStringService redisStringService;
    private SMSService smsService;
    private MailService mailService;

    public CodeService(RedisStringService redisStringService, SMSService smsService, MailService mailService) {
        this.redisStringService = redisStringService;
        this.smsService = smsService;
        this.mailService = mailService;
    }

    /**
     * 创建验证码
     *
     * @param style
     * @param data
     * @return
     */
    public Code create(int style, String data) {
        //每天只能发送5条
        String codeCountKey = CacheKeyUtil.codeCountKey(data);
        int count = 0;
        if (redisStringService.hasKey(codeCountKey)) {
            count = Integer.valueOf(redisStringService.findString(codeCountKey));

            if (count >= 5) {
                throw new CommonException(Constant.ERROR_CODE_COUNT_LIMIT, Constant.ERROR_CODE_COUNT_LIMIT_MESSAGE);
            }
        }

        count++;

        Date currentDate = DateUtil.date();
//        Date endOfTodayDay = DateUtil.endOfDay(currentDate);
        DateTime endOfTodayDay = DateUtil.offsetMinute(currentDate, 2);
        long betweenSecond = DateUtil.between(currentDate, endOfTodayDay, DateUnit.SECOND);

        redisStringService.update(codeCountKey, String.valueOf(count), betweenSecond, TimeUnit.SECONDS);

        //生成验证码，并且保存
        String code = generateCodeAndSave(data);

        switch (style) {
            case Constant
                    .VALUE10:
//                smsService.sendSMSCode(data, code);
                break;
            default:
                mailService.sendEmailCode(data, code);
                break;
        }

        //返回验证码
        //这里只是方便测试
        //真实项目中不要返回
        Code result = new Code();
        result.setCode(code);
        return result;
    }

    /**
     * 生成验证码，并保存
     * <p>
     * 这里是保存到数据库，也可以像图片验证码那样保存到redis
     *
     * @param target
     * @return
     */
    private String generateCodeAndSave(String target) {
        //生成验证码
        int codeInt = RandomUtil.int6();

        String codeString = String.valueOf(codeInt);

        log.info("generateCodeAndSave code {}", codeString);

        // 验证码存入redis，可以使用自动失效，当然也可以存入数据库
        //保存规则，key=codes:target:data，值就就设置为加密后验证码，5分钟有效
        redisStringService.update(CacheKeyUtil.codeDataKey(target), MD5Util.encrypt(codeString), 5, TimeUnit.MINUTES);

        //限制发送频率，1分钟失效
        redisStringService.update(CacheKeyUtil.codeLimitKey(target), "0", 1, TimeUnit.MINUTES);

        return codeString;
    }

    /**
     * 检查验证码，并删除
     *
     * @param target
     * @param code
     * @return
     */
    public void checkAndDelete(String target, String code) {
        check(target, code);

        String key = CacheKeyUtil.codeDataKey(target);

        //验证码通过后，也就是已经用了，就清除
        redisStringService.delete(key);
    }

    /**
     * 检查验证码，不会删除
     *
     * @param target
     * @param code
     * @return
     */
    public void check(String target, String code) {
        String key = CacheKeyUtil.codeDataKey(target);
        if (!redisStringService.hasKey(key)) {
            throw new CommonException(Constant.ERROR_CODE, Constant.ERROR_CODE_MESSAGE);
        }

        //获取存的验证码内容
        String oldCode = redisStringService.findString(key);
        if (!MD5Util.encrypt(code).equals(oldCode)) {
            throw new CommonException(Constant.ERROR_CODE, Constant.ERROR_CODE_MESSAGE);
        }
    }

    public void checkIfSend(String target) {
        //同一个用户必须间隔1分钟
        if (redisStringService.hasKey(CacheKeyUtil.codeLimitKey(target))) {
            //间隔小于1分钟

            //提示接口调用频繁
            throw new CommonException(Constant.ERROR_TOO_FAST, Constant.ERROR_TOO_FAST_MESSAGE);
        }
    }
}
package com.example.courses.music.controller.v1;

import com.example.courses.music.service.AliyunSTSService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置控制器
 */
@RestController
@RequestMapping("/v1/oss")
public class AliyunOSSController {

    /**
     * 阿里云sts服务
     */
    @Autowired
    private AliyunSTSService aliyunSTSService;

    /**
     * 获取阿里云OSS上传配置
     * <p>
     * 我们这里实现的是，只有用户登录
     * 才能调用这里面的接口
     * 大家可以根据实际情况调整
     *
     * @return
     */
    @GetMapping("/config")
    public Object config() {
        return R.wrap(aliyunSTSService.getAliyunOSSSTSToken());
    }
}

package com.example.courses.music.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.model.AliyunOSSUploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 阿里云STS服务
 */
@Service
public class AliyunSTSService {
    private final static Logger log = LoggerFactory.getLogger(AliyunSTSService.class);

    /**
     * sts地址
     */
    @Value("${aliyun.sts.endpoint}")
    private String endpoint;

    /**
     * 阿里云ak
     */
    @Value("${aliyun.key}")
    private String key;

    /**
     * 阿里云sk
     */
    @Value("${aliyun.secret}")
    private String secret;

    /**
     * 阿里云 上传文件sts角色
     */
    @Value("${aliyun.sts.role}")
    private String role;

    /**
     * 项目（应用）名称
     */
    @Value("${info.name}")
    private String name;

    /**
     * 获取阿里云OSS上传配置
     *
     * @return
     */
    public AliyunOSSUploadConfig getAliyunOSSSTSToken() {
        try {
            // 构造default profile（参数留空，无需添加region ID）
            DefaultProfile profile = DefaultProfile.getProfile("", key, secret);

            // 用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);

            //创建扮演角色请求
            //com.aliyuncs.sts.model.v20150401
            AssumeRoleRequest request = new AssumeRoleRequest();
            request.setSysEndpoint(endpoint);
            request.setSysMethod(MethodType.POST);
            request.setRoleArn(role);

            //可以传递用户信息
            //这样不同用户生成的信息不能共用
            //我们这里用不到，所以就传递项目名称就行了
            request.setRoleSessionName(name);

            //这是策略
            //这里用不到
//            request.setPolicy(policy);

            //扮演角色
            AssumeRoleResponse response = client.getAcsResponse(request);

            //获取临时认证信息
            AssumeRoleResponse.Credentials credentials = response.getCredentials();

            log.info("getAliyunOSSSTSToken success {}", credentials.getAccessKeyId());

            //创建返回对象
            return new AliyunOSSUploadConfig(
                    credentials.getAccessKeyId(),
                    credentials.getAccessKeySecret(),
                    credentials.getSecurityToken(),
                    credentials.getExpiration(),
                    name
            );
        } catch (Exception e) {
            log.error("getAliyunOSSSTSToken failed {}", e);
        }

        throw new ArgumentException();
    }
}

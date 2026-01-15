package com.example.courses.music.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 阿里云OSS服务
 */
@Service
public class AliyunOSSService {
    private final static Logger log = LoggerFactory.getLogger(AliyunOSSService.class);

    /**
     * oss地址
     */
    @Value("${aliyun.oss.endpoint}")
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
     * 列举bucket
     */
    public void listBucket() {
        //创建OSSClient
        OSS ossClient = new OSSClientBuilder().build(
                endpoint,
                key,
                secret
        );

        //列举存储空间
        List<Bucket> buckets = ossClient.listBuckets();
        for (Bucket bucket : buckets) {
            log.info("listBucket {}", bucket.getName());
        }

        //关闭OSSClient
        ossClient.shutdown();
    }
}

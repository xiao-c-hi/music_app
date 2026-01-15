package com.example.courses.music.config.baidu;

import com.baidu.aip.nlp.AipNlp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 百度相关配置
 */
@Configuration
public class BaiduConfig {
    //在百度自然语言后台创建，并配置到这里，也可以放到配置文件中，大家测试的时候换成自己的
    //因为我们用的是免费账号，只有少量的额度，很有可能已经用完了
    //https://console.bce.baidu.com/ai/#/ai/nlp/app/list
    private static final String NLP_APP_ID = "33956051";
    private static final String NLP_API_KEY = "e12rZzHjZPR8cFG7PsuUKr47";
    private static final String NLP_SECRET_KEY = "jNsqX61eSa9Vt9xt8FIWZBNd3oCnrTyV";

    /**
     * 获取NLP客户端
     * <p>
     * https://cloud.baidu.com/doc/NLP/s/Nk6z52ci5
     *
     * @return
     */
    @Bean
    public AipNlp nlp() {
        AipNlp result = new AipNlp(NLP_APP_ID, NLP_API_KEY, NLP_SECRET_KEY);

//        // 可选：设置网络连接参数
//        result.setConnectionTimeoutInMillis(2000);
//        result.setSocketTimeoutInMillis(60000);
//
//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        result.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        result.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        return result;
    }
}

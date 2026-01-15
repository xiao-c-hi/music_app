package com.example.courses.music.config.advice;


import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.JSONUtil;
import com.example.courses.music.util.SHAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 通过拦截器对response签名
 * 核心原理是aop(Aspect Oriented Programming),面向切面编程
 * 简单来说通过这种机制可以实现
 * 可以实现拦截某个方法前，方法后
 * <p>
 * basePackages：对指定路径下的包拦截
 */
@ControllerAdvice(basePackages = "com.ixuea.courses.music.controller")
public class SignResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private static Logger log = LoggerFactory.getLogger(SignResponseBodyAdvice.class);

    /**
     * 是否开启拦截
     *
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("supports returnType {} converterType {}", returnType, converterType);

        //一直开启
        return true;
    }

    /**
     * 响应体返回到客户端前
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            log.info("beforeBodyWrite");

            //请求方式
            String method = request.getMethod().name();

            //请求地址
            String path = request.getURI().getPath();

            //判断要处理的url
            if (path.startsWith("/v2/addresses") && method.equalsIgnoreCase("get")) {
                //该接口响应需要添加签名

                //真实项目中可能所有接口都要处理
                //就不用判断了

                //我们这里为了降低课程难度
                //所以只处理几个接口

                //获取响应字符串
                String dataString = JSONUtil.toJSON(body);

                //计算签名
                String sign = SHAUtil.sha256(dataString);

                //添加签名到响应头
                response.getHeaders().set(Constant.HEADER_SIGN, sign);
            } else if (path.startsWith("/v3/addresses") && method.equalsIgnoreCase("get")) {
                //该接口响应需要加密

                //可以手动在这里对返回的data加密
            }
        } catch (Exception e) {
            log.error("beforeBodyWrite failed {}", e);

            //处理错误
            throw new ArgumentException();
        }

        return body;
    }
}

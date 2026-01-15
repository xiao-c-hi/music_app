package com.example.courses.music.config.advice;


import com.example.courses.music.annotation.DecryptRequestBody;
import com.example.courses.music.annotation.VerifyRequestBodySign;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.exception.RequestSignException;
import com.example.courses.music.util.AESUtil;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.SHAUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 检查参数签名；解密参数
 * Rest风格请求包装器
 * 只能处理@RequestBody的方法
 * 不能处理表单请求
 */
@RestControllerAdvice(basePackages = "com.ixuea.courses.music.controller")
public class SecurityRequestBodyAdvice implements RequestBodyAdvice {
    private static Logger log = LoggerFactory.getLogger(SecurityRequestBodyAdvice.class);

    /**
     * 是否拦截
     *
     * @param methodParameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("supports {},{},{}", methodParameter, targetType, converterType);
        return true;
    }

    /**
     * 请求体读取前
     *
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     * @throws IOException
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        log.info("beforeBodyRead {},{},{}", inputMessage, parameter, converterType);

        if (parameter.getMethod().isAnnotationPresent(VerifyRequestBodySign.class)) {
            //表示这个方法要检查请求体签名
            log.info("beforeBodyRead prepare check sign");

            //从header中取出签名
            List<String> signs = inputMessage.getHeaders().get(Constant.HEADER_SIGN);

            if (signs == null || signs.size() != 1) {
                log.error("beforeBodyRead not sign");

                //真实项目中一般很少会提示这么明显
                //因为错误提示的越详细
                //对攻击者也就越详细了
                throw new CommonException(Constant.ERROR_PARAMS_NOT_SIGN, Constant.ERROR_PARAMS_NOT_SIGN_MESSAGE);
            }

            //获取签名
            String sign = signs.get(0);

            //获取参数字符串
            String dataString = IOUtils.toString(inputMessage.getBody(), Constant.CHARSET);

            //计算签名
            String newSign = SHAUtil.sha256(dataString);

            if (!newSign.equals(sign)) {
                //签名错误
                log.error("beforeBodyRead sign incorrect");

                //抛出一个异常
                throw new RequestSignException();
            }

            //签名正确
            log.info("beforeBodyRead sign correct");

            //重新创建输入流
            //因为java中默认流不能读取两次
            return createInputMessage(dataString, inputMessage);
        } else if (parameter.getMethod().isAnnotationPresent(DecryptRequestBody.class)) {
            //表示这个方法要解密请求体
            log.info("beforeBodyRead prepare decrypt param");

            //获取参数字符串
            String dataString = IOUtils.toString(inputMessage.getBody(), Constant.CHARSET);

            //解密
            dataString = AESUtil.decrypt(dataString);

            //解密成功
            log.info("beforeBodyRead decrypt param correct");

            return createInputMessage(dataString, inputMessage);
        }

        return inputMessage;
    }

    /**
     * 创建输入消息
     * <p>
     * 因为java中默认流不能读取两次
     *
     * @param data
     * @param sourceInputMessage
     * @return
     */
    private HttpInputMessage createInputMessage(String data, HttpInputMessage sourceInputMessage) {
        return new HttpInputMessage() {
            /**
             * 返回body
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                return IOUtils.toInputStream(data, Constant.CHARSET);
            }

            /**
             * 返回header
             * @return
             */
            @Override
            public HttpHeaders getHeaders() {
                return sourceInputMessage.getHeaders();
            }
        };
    }

    /**
     * 请求体读取之后
     *
     * @param body
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /**
     * 没有请求体
     *
     * @param body
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}

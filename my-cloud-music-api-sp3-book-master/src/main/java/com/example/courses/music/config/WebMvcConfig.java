package com.example.courses.music.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.example.courses.music.model.Rule;
import com.example.courses.music.service.RuleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WebMvc配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 映射
     */
    @Autowired
    private RuleService ruleService;

    /**
     * 添加资源处理器
     * <p>
     * 我们这里主要是将上传图片的目录映射处理可以访问
     *
     * @param registry
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //当访问所有/files/路径下面的内容
//        //映射到上传文件目录
//        registry.addResourceHandler("/files/**")
//                .addResourceLocations("file:".concat(Config.DIR_UPLOAD));
//    }


    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册一个权限拦截器
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
            //没请求一个地址，就会调用这里

            //获取所有最后一级权限，内部缓存到redis了
            List<Rule> rules = ruleService.findAllByUriNotNull();

            if (CollectionUtils.isNotEmpty(rules)) {
                for (Rule rule : rules) {
                    SaRouter.match(rule.getUri(), () -> {
                        if (req.getMethod().equalsIgnoreCase(rule.getMethod())) {
                            StpUtil.checkPermission(rule.getValue());
                        }
                    });

                }
            }

       }))
       .addPathPatterns("/**");
    }
}
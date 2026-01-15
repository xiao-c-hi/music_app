package com.example.courses.music;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 应用上下文
 * <p>
 * 我们使用它，主要是获取某个bean实例
 */
@Component
public class AppContext implements ApplicationContextAware {

    /**
     * 上下文
     */
    private static ApplicationContext applicationContext;

    /**
     * 获取某个bean实例
     *
     * @param requiredType
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 设置上下文
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.applicationContext = applicationContext;
    }
}

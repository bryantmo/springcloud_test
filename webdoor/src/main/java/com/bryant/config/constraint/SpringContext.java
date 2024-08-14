package com.bryant.config.constraint;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * 全局通用Spring上下文帮助类
 */
@Service
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    private static Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
        SpringContext.environment = applicationContext.getEnvironment();

        // 设置APP_NAME
        String appName = environment.getProperty("spring.application.name");

    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getService(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getService(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getService(String name, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    public static String getEnvProperty(String key) {
        return environment.getProperty(key);
    }

    public static String getEnvProperty(String key, String defaultValue) {
        String value = environment.getProperty(key);
        if (value == null || "".equals(value.trim())) {
            return defaultValue;
        }
        return value;
    }
}

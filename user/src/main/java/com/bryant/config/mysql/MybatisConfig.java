package com.bryant.config.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Full(proxyBeanMethods = true) :proxyBeanMethods参数设置为true时即为：Full 全模式。 该模式下注入容器中的同一个组件无论被取出多少次都是同一个bean实例，即单实例对象，在该模式下SpringBoot每次启动都会判断检查容器中是否存在该组件
 * Lite(proxyBeanMethods = false) :proxyBeanMethods参数设置为false时即为：Lite 轻量级模式。该模式下注入容器中的同一个组件无论被取出多少次都是不同的bean实例，即多实例对象，在该模式下SpringBoot每次启动会跳过检查容器中是否存在该组件
 */
@Configuration(proxyBeanMethods = true)
@ConditionalOnProperty(prefix = "users.mybatis.custom", name = "interceptor", havingValue = "true")
@Slf4j
public class MybatisConfig implements InitializingBean {

    @Bean
    @Order(1)
    public TenantIdInjectInterceptor mybatisInterceptor() {
        log.info("TenantIdInjectInterceptor interceptor init...");
        return new TenantIdInjectInterceptor();
    }

    /**
     * @Order(0) 是为了保证 TenantIdInjectInterceptor拦截器先于sqlMonitorInterceptor拦截器执行
     * @return
     */
    @Bean
    @Order(0)
    public SqlMonitorInterceptor sqlMonitorInterceptor() {
        log.info("SqlMonitorInterceptor interceptor init...");
        return new SqlMonitorInterceptor();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

}

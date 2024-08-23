package com.bryant.config.mysql;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Full(proxyBeanMethods = true) :proxyBeanMethods参数设置为true时即为：Full 全模式。 该模式下注入容器中的同一个组件无论被取出多少次都是同一个bean实例，即单实例对象，在该模式下SpringBoot每次启动都会判断检查容器中是否存在该组件
 * Lite(proxyBeanMethods = false) :proxyBeanMethods参数设置为false时即为：Lite 轻量级模式。该模式下注入容器中的同一个组件无论被取出多少次都是不同的bean实例，即多实例对象，在该模式下SpringBoot每次启动会跳过检查容器中是否存在该组件
 */
@Configuration(proxyBeanMethods = true)
@ConditionalOnProperty(prefix = "users.mybatis.custom", name = "interceptor", havingValue = "true")
@Slf4j
public class MybatisConfig implements InitializingBean {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactorys;

    @Bean
    public MybatisSqlInterceptor mybatisInterceptor() {
        log.info("MybatisSqlInterceptor interceptor init...");
        return new MybatisSqlInterceptor();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 每个sqlSessionFactory都添加拦截器
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactorys) {
            sqlSessionFactory.getConfiguration()
                    .addInterceptor(mybatisInterceptor());
        }
    }
}

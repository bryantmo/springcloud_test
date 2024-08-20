package com.bryant.config.feign;

import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign 客户端拦截器-全局配置类，默认开启
 */
@Configuration
@ConditionalOnProperty(prefix = "feign.interceptor", name = "enable", havingValue = "true", matchIfMissing = true)
public class FeignInterceptorConfig {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new CustomFeignInterceptor();
    }

}

package com.bryant;

import com.bryant.config.interceptor.RequestInterceptor;
import com.bryant.config.param.CompatibleParameterAndJsonArgumentResolver;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("WebConfig add interceptor...");
        registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        log.info("WebConfig add argument resolver...");
        argumentResolvers.clear();
        argumentResolvers.add(new CompatibleParameterAndJsonArgumentResolver());
    }
}

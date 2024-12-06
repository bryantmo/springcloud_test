package com.bryant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决前端跨域问题
 */
@Configuration
@EnableAspectJAutoProxy
public class WebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*/**")
                // Access-Control-Allow-Origin 允许跨域的域名，如果携带cookies则不能为“*”
                .allowedOrigins("*")
                // Access-Control-Allow-Methods
                .allowedMethods("GET","POST","PUT", "OPTIONS", "DELETE");
    }
}
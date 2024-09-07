package com.bryant.config.constraint;

import com.bryant.config.constraint.request_mapping.WebRequestMappingHandlerMapping;
import com.bryant.config.constraint.request_mapping.WebRouterPathConstraintMatcher;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 重写 spring mvc 中 {@link RequestMappingHandlerMapping}
 * @see RequestMappingHandlerMapping
 * @see com.bryant.config.constraint.request_mapping.AbstractRequestMappingHandlerMapping
 */
@Configuration
public class WebRequestMappingRegistrationsConfig implements WebMvcRegistrations {

    /**
     * 返回一个自定义的 RequestMappingHandlerMapping 实例，用于处理 HTTP 请求映射。
     *
     * 具体怎么对请求进行映射呢？，参考 WebRequestMappingHandlerMapping
     * - WebRequestMappingHandlerMapping 有路径匹配器：WebRouterPathConstraintMatcher
     *    - WebRouterPathConstraintMatcher 有路径匹配器：PathMatcher#match
     */
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        WebRequestMappingHandlerMapping requestMappingHandlerMapping = new WebRequestMappingHandlerMapping();
        requestMappingHandlerMapping.setPathMatcher(routerPathConstraintMatcher());
        return requestMappingHandlerMapping;
    }

    @Override
    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
        return new ExtendedRequestMappingHandlerAdapter();
    }

    @Bean
    public WebRouterPathConstraintMatcher routerPathConstraintMatcher() {
        return new WebRouterPathConstraintMatcher();
    }

}

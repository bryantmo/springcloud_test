package com.bryant.config.constraint;

import com.bryant.config.constraint.request_mapping.WebRequestMappingHandlerMapping;
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

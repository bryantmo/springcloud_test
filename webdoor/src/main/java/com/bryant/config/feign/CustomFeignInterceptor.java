package com.bryant.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * feign拦截器，修改请求头信息
 */
public class CustomFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("feign interceptor header marker", "1");
    }
}

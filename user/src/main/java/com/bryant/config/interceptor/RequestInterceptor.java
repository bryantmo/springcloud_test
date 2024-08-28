package com.bryant.config.interceptor;

import com.bryant.config.filter.TokenValidateFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interceptor：拦截器是Spring MVC框架提供的，需要在Spring MVC配置文件中进行配置。
 * 拦截器会在请求到达控制器之前进行处理，也可以在响应返回给客户端之前进行处理。
 */
public class RequestInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(TokenValidateFilter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("RequestInterceptor preHandle.. url = {}", request.getRequestURI());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("RequestInterceptor postHandle.. url = {}", request.getRequestURI());
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("RequestInterceptor afterCompletion.. url = {}", request.getRequestURI());
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

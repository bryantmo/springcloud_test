package com.bryant.config.sleuth.filter;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Filter：过滤器是基于Java Servlet规范的，需要在web.xml文件中进行配置。
 * 过滤器会在请求到达Servlet之前进行处理，也可以在响应返回给客户端之前进行处理。
 */
@Component
@Order(1)
public class TokenValidateFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(TokenValidateFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("TokenValidateFilter doFilter...");
        if (request.getParameter("token") != null) {
            chain.doFilter(request, response);
        } else {
            logger.info("TokenValidateFilter doFilter fail, request with no token..");
        }

        // 在响应返回给客户端之前执行的操作，比如设置响应的Locale
        response.setLocale(Locale.CANADA);
        logger.info("TokenValidateFilter doFilter finish, 响应即将返回给客户端，这里可以对响应进行修改");
    }

}

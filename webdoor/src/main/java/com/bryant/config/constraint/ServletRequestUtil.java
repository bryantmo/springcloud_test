package com.bryant.config.constraint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletRequestUtil {

    /**
     * 获取当前请求的 {@link HttpServletRequest} 对象
     *
     * @return HttpServletRequest
     * @see org.springframework.web.filter.RequestContextFilter
     */
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        if (ObjectUtils.isEmpty(servletRequestAttributes)) {
            return null;
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取当前请求的 {@link HttpServletResponse} 对象
     *
     * @return HttpServletResponse
     * @see org.springframework.web.filter.RequestContextFilter
     */
    public static HttpServletResponse getCurrentResponse() {
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        if (ObjectUtils.isEmpty(servletRequestAttributes)) {
            return null;
        }
        return servletRequestAttributes.getResponse();
    }

    /**
     * 向调用端响应 json 格式
     *
     * @param request
     * @return
     */
    public static Boolean isAsynchronousRequests(HttpServletRequest request) {
        return (request.getHeader("accept") != null && request.getHeader("accept").contains("application/json")) ||
                (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest"));
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

}

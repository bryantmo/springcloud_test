package com.bryant.config.constraint.request_mapping;

import com.bryant.config.constraint.NoMatchRouteCache;
import com.bryant.config.constraint.WebRouterConstraintCondition;
import com.bryant.controller.constraint.router.PathConstraint;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

@ScanPackagePathConstraint(basePackageNames = {"com.bryant"})
public class WebRequestMappingHandlerMapping extends AbstractRequestMappingHandlerMapping {

    @Override
    protected void initHandlerMethods() {
        this.registerPackageRouterConstraint();
        super.initHandlerMethods();
    }

    /**
     * 循环遍历所有的 @RequestMapping 对一个的路由元信息，进行匹配，匹配到最佳 RequestMappingInfo
     * ```
     * for (RequestMappingInfo mapping : mappings) {
     * // 依次匹配 method\produces\consumes\header\...\patterns\customCondition
     * }
     * ```
     *
     * @param info
     * @param request
     * @return
     */
    @Override
    protected RequestMappingInfo getMatchingMapping(RequestMappingInfo info, HttpServletRequest request) {
        // {@link org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.addMatchingMappings} 引用
        // org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.lookupHandlerMethod 引用
        // 根据请求路径对通配符进行匹配，配置 useSuffixPatternMatch(.*) 以及 useTrailingSlashMatch(/)
        // 增加 constraintRegistry 中 pattern + ".*" 以及 "pattern + /" 和 constraint 对应的关系
        // pattern + ".*" 以及 pattern + "/" 和 pattern 共用同一个 constraint约束
        // 上述过程，对于一个指定的pattern 只会执行一次

        request.setAttribute(REQUEST_MAPPING_MATCHING, info);
        RequestMappingInfo requestMappingInfo = super.getMatchingMapping(info, request);
        request.removeAttribute(REQUEST_MAPPING_MATCHING);
        return requestMappingInfo;
    }

    /**
     * 对于任何一个请求 spring security filter 的拦截要优先于 spring mvc servlet 的拦截
     * 根据部分请求（并非所有）获取相应的handler，已经在 {@todo 调试 com.tencent.tgit.web.api.security.AccessDecisionManager#decide} 中进行获取
     *
     * @param lookupPath 请求路径，通过 {@link org.springframework.web.util.UrlPathHelper} 获取
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {

        if (StringUtils.isNotEmpty(lookupPath)) {
            request.setAttribute("startTime_" + lookupPath, System.currentTimeMillis());
        }

        if (!pathMatched4Security(request)) {
            return super.lookupHandlerMethod(lookupPath, request);
        }

        return null;
    }

    @Override
    protected void handleMatch(RequestMappingInfo info, String lookupPath, HttpServletRequest request) {
        super.handleMatch(info, lookupPath, request);
    }

    private boolean pathMatched4Security(HttpServletRequest request) {

        /**
         * 路径解析链路：
         * 请求 -> Security -> DispatcherServlet
         *
         * Security 中路径解析，{@link com.tencent.tgit.web.security.SecurityPrepareHandlerRequestMapping#getLookupPathForRequest(HttpServletRequest)}
         * Security 解析路径，根据路径中的资源内容进行权限解析，request 记录路径已经被解析
         *
         * DispatcherServlet 路径解析 {@link WebRequestMappingHandlerMapping#lookupHandlerMethod(String, HttpServletRequest)}
         * 判断 Security 是否解析过，如果解析，则 DispatcherSerlvet 中不再进行解析。
         *
         * 可能出现的问题：
         * 请求 -> Security -> 出现异常 -> [路径解析完毕，request 记录] -> forward -> /error
         * /error[不经过security] -> DispatcherServlet -> [request 记录路径解析完毕] DispatcherServlet不再解析路径 -> 出现问题
         *
         * 预期：DispatcherServlet 解析 /error 为 BasicErrorController#error
         * 结果：DispatcherServlet 使用 request 中的缓存，解析的结果为被拦截请求的controller方法
         *
         * /error => 被拦截的controller方法
         *
         * 解决方案：
         * SecurityPrepareHandlerRequestMapping 解析的路径 和 WebRequestMappingHandlerMapping 解析的路径必须是同一个
         */

        if (Objects.isNull(request)) {
            return false;
        }


        return true;
    }

    /**
     * 为每一个请求方法对应的 RequestMappingInfo 路由元信息，创建一个 RequestCondition
     *
     * @param method
     * @return
     */
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        PathConstraintDetection pathConstraintDetection = this.getPathConstraintDetection();
        if (ObjectUtils.isEmpty(pathConstraintDetection)) {
            return new WebRouterConstraintCondition(null);
        }

        PathConstraint pathConstraint = pathConstraintDetection.detect(method);
        if (ObjectUtils.isNotEmpty(pathConstraint)) {
            return new WebRouterConstraintCondition(pathConstraint);
        }

        Class<?> beanType = method.getDeclaringClass();
        if (ObjectUtils.isEmpty(beanType)) {
            return null;
        }

        pathConstraint = pathConstraintDetection.detect(beanType);
        if (ObjectUtils.isNotEmpty(pathConstraint)) {
            return new WebRouterConstraintCondition(pathConstraint);
        }

        pathConstraint = pathConstraintDetection.detect(beanType.getPackage());
        if (ObjectUtils.isNotEmpty(pathConstraint)) {
            return new WebRouterConstraintCondition(pathConstraint);
        }
        return new WebRouterConstraintCondition(null);
    }

    @Override
    protected HandlerMethod handleNoMatch(Set<RequestMappingInfo> infos, String lookupPath, HttpServletRequest request) throws ServletException {
        try {
            HandlerMethod handlerMethod = super.handleNoMatch(infos, lookupPath, request);
            return handlerMethod;
        } catch (Exception e) {
            throw e;
        } finally {
            NoMatchRouteCache.resetCache(request);
        }
    }
}

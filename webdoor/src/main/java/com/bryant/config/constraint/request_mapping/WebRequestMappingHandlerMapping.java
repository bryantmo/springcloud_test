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
        return super.lookupHandlerMethod(lookupPath, request);
    }

    @Override
    protected void handleMatch(RequestMappingInfo info, String lookupPath, HttpServletRequest request) {
        super.handleMatch(info, lookupPath, request);
    }

    /**
     * 为每一个请求方法对应的 RequestMappingInfo 路由元信息，创建一个 RequestCondition
     *
     * @param method
     * @return
     */
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {

        // 非灰度接口
        PathConstraintDetection pathConstraintDetection = this.getPathConstraintDetection();
        if (ObjectUtils.isEmpty(pathConstraintDetection)) {
            return new WebRouterConstraintCondition(null);
        }

        // 灰度接口处理
        PathConstraint pathConstraint = pathConstraintDetection.detect(method);
        if (ObjectUtils.isNotEmpty(pathConstraint)) {
            return new WebRouterConstraintCondition(pathConstraint);
        }

        // 兜底处理
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

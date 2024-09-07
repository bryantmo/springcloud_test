package com.bryant.config.constraint.request_mapping;

import com.bryant.config.constraint.NoMatchRouteCache;
import com.bryant.controller.constraint.router.PathRouterDecisionMaker;
import java.lang.reflect.Method;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
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
     * 循环遍历所有的 @RequestMapping 集合，根据路由注解元信息，进行匹配，匹配到最佳 RequestMappingInfo
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
        // request.setAttribute 这个步骤很重要，去掉之后，请求直接报500了
        request.setAttribute(REQUEST_MAPPING_MATCHING, info);
        RequestMappingInfo requestMappingInfo = super.getMatchingMapping(info, request);
        request.removeAttribute(REQUEST_MAPPING_MATCHING);
        return requestMappingInfo;
    }

    /**
     * 直接调用 org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#lookupHandlerMethod(java.lang.String, javax.servlet.http.HttpServletRequest) 即可，没有做特殊处理
     * @param lookupPath 请求路径，通过 {@link org.springframework.web.util.UrlPathHelper} 获取
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {
        return super.lookupHandlerMethod(lookupPath, request);
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
        WebRouterDecisionMakerDetection webRouterDecisionMakerDetection = this.getPathConstraintDetection();
        if (ObjectUtils.isEmpty(webRouterDecisionMakerDetection)) {
            return new WebRouterDecisionCondition(null);
        }

        // 灰度接口处理
        PathRouterDecisionMaker pathRouterDecisionMaker = webRouterDecisionMakerDetection.detect(method);
        if (ObjectUtils.isNotEmpty(pathRouterDecisionMaker)) {
            return new WebRouterDecisionCondition(pathRouterDecisionMaker);
        }

        // 兜底处理
        return new WebRouterDecisionCondition(null);
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

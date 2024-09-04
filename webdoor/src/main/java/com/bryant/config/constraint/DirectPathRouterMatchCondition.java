package com.bryant.config.constraint;

import com.bryant.controller.constraint.router.PathConstraint;
import com.bryant.controller.constraint.router.PathPartRequest;
import com.bryant.controller.constraint.router.RouterConstraints;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

public class DirectPathRouterMatchCondition {
    static String REQUEST_MAPPING_MATCHING = PathMatchedConstant.class.getName() + ".requestMappingMatching";

    public static String getLookupUrl(HttpServletRequest request) {
        Object lookupUrl = request.getAttribute(HandlerMapping.LOOKUP_PATH);
        return ObjectUtils.isEmpty(lookupUrl) ? "" : lookupUrl.toString();
    }

    /**
     * 匹配阶段，当前的路由信息存储在：{todo 修改此处注释为正确 com.bryant.PathMatchedConstant#REQUEST_MAPPING_MATCHING} 中。
     * 此方法主要用于如何判断当前请求和待匹配的路由时直接路由匹配，例如：`/dashboard/projects` 请求的路由匹配。
     *
     * @param request
     * @return
     */
    public static boolean isDirectUrlMatch(HttpServletRequest request) {
        String lookupUrl = getLookupUrl(request);
        Object requestMappingInfoObject = request.getAttribute(REQUEST_MAPPING_MATCHING);
        if (!(requestMappingInfoObject instanceof RequestMappingInfo)) {
            return false;
        }

        RequestMappingInfo requestMappingInfo = (RequestMappingInfo) requestMappingInfoObject;
        PatternsRequestCondition patternsRequestCondition = requestMappingInfo.getPatternsCondition();
        if (ObjectUtils.isEmpty(patternsRequestCondition)) {
            return false;
        }
        Set<String> patterns = patternsRequestCondition.getPatterns();
        if (CollectionUtils.isEmpty(patterns)) {
            return false;
        }
        for (String pattern : patterns) {
            if (StringUtils.equals(pattern, lookupUrl)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDirectUrlMatchPathConstraint(HttpServletRequest request, PathConstraint pathConstraint) {
        String lookupUrl = DirectPathRouterMatchCondition.getLookupUrl(request);

        // 路由 Controller 方法没有被 @PathConstraint 注解修饰
        if (ObjectUtils.isEmpty(pathConstraint)) {
            return true;
        }

        RouterConstraints routerConstraints = RouterConstraintsUtils.getRouterConstraint(pathConstraint.constraint());
        // 路由 PathConstraints 注解中没有 constraints 约束，默认视为 true
        if (ObjectUtils.isEmpty(routerConstraints)) {
            return true;
        }
        // 直接路径匹配，则 lookupUrl 和 pattern 相同
        PathPartRequest pathPartRequest = PathPartRequest.build(request, lookupUrl, lookupUrl, new HashMap<>(),
                new RouterPatternKey(lookupUrl, pathConstraint), null);
        return routerConstraints.matches(pathPartRequest);        // 这类会用到约束类的
    }
}

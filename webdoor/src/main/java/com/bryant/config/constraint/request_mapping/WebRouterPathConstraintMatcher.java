package com.bryant.config.constraint.request_mapping;


import com.bryant.config.constraint.PathMatchedConstant;
import com.bryant.controller.constraint.router.PathRouterDecisionMaker;
import com.bryant.controller.constraint.router.RouterDecisionMaker;
import com.bryant.controller.constraint.router.RouterPathRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

public class WebRouterPathConstraintMatcher implements PathMatcher {

    /**
     * 自定义了一个key到request的attribute
     */
    String REQUEST_MAPPING_MATCHING = PathMatchedConstant.class.getName() + ".requestMappingMatching";

    @Override
    public boolean isPattern(String path) {
        return false;
    }

    @Override
    public boolean match(String pattern, String path) {

        HttpServletRequest request = ServletRequestUtil.getCurrentRequest();

        // 1. 提取注解，
        PathRouterDecisionMaker pathRouterDecisionMaker = getRouterConstraintTypeByMatchingRequestMappingInfo(request);
        Class<? extends RouterDecisionMaker> routerConstraintClass = ObjectUtils.isEmpty(pathRouterDecisionMaker) ? null : pathRouterDecisionMaker.decision();

        // 2. 封装了一层 RouterPatternKey
        RouterPatternKey routerPatternKey = new RouterPatternKey(pattern, pathRouterDecisionMaker);

        /*
          3. 让前端辅助，做了一层缓存加速；上次的请求路径和 patterns 为能够匹配的时候，将会缓存至 NoMatchRouteCache 中。
         - url 请求路径
            - RouterPatternKey-1 [pattern, constraint]
            - RouterPatternKey-2 [pattern, constraint]
            ...
            - RouterPatternKey-n [pattern, constraint]
         */
//        boolean hitNoMatchRouteCache = NoMatchRouteCache.hitCache(request, path, routerPatternKey);
//        if (hitNoMatchRouteCache) {
//            return false;
//        }
//
//        // 3.提取请求路径中的变量
//        Map<String, String> variables = extractUriTemplateVariables(pattern, path);
//        if (Objects.isNull(variables)) {
//            // 无法匹配，进行缓存
////            NoMatchRouteCache.cache(request, path, routerPatternKey);
//            return false;
//        }

        // 有约束条件
        if (!ObjectUtils.isEmpty(routerConstraintClass)) {
            RouterDecisionMaker routerDecisionMaker = RouterConstraintsUtils.getRouterConstraint(routerConstraintClass);
            RouterPathRequest routerPathRequest = RouterPathRequest.build(request, pattern, path, null, routerPatternKey, pathRouterDecisionMaker.resourceCondition());
            if (!routerDecisionMaker.matches(routerPathRequest)) {
                /*
                   约束未能匹配到，未匹配到的原因可能有：
                   1. 从 db 中校验资源的时候，未能获取到
                   2. 从 db 中校验资源的时候，可以获取到，但是由于其他的约束条件未能匹配成功，例如：灰度条件、svn/git 等条件

                   如果因为 2 没有匹配到，那么需要将资源对象从 RouteResourceCache 中清除掉缓存
                 */
//                pathPartRequest.removeResource();
//                NoMatchRouteCache.cache(request, path, routerPatternKey);
                return false;
            }
        }
        // 不存在约束条件
        return true;
    }

    /**
     * 一个 RequestMappingInfo ->
     * method
     * patterns
     * produces
     * consumes
     * ...
     * customCondition [RouterConstraint]
     *
     * 每次在匹配的时候，当前匹配的 RequestMappingInfo 存储在 request 上下文中：
     * ```
     * for (RequestMappingInfo mapping : mappings) {
     * request.setAttribute(REQUEST_MAPPING_MATCHING, mapping);
     * // 依次匹配 method\produces\consumes\header\...\patterns\customCondition
     * request.removeAttribute(REQUEST_MAPPING_MATCHING, mapping);
     * }
     * ```
     * 以下方法获取每一个正在匹配的 RequestMappingInfo 中 customCondition 对应的 RouterConstraint 约束条件
     *
     * 参考:
     * 1. WebRequestMappingHandlerMapping#getMatchingMapping(RequestMappingInfo, HttpServletRequest)
     * 2. AbstractRequestMappingHandlerMapping#handleNoMatch(Set, String, HttpServletRequest)
     *
     * @param request
     * @return
     */
    private PathRouterDecisionMaker getRouterConstraintTypeByMatchingRequestMappingInfo(HttpServletRequest request) {
        Object requestMappingInfoObject = request.getAttribute(REQUEST_MAPPING_MATCHING);
        if (!(requestMappingInfoObject instanceof RequestMappingInfo)) {
            return null;
        }

        RequestMappingInfo requestMappingInfo = (RequestMappingInfo) requestMappingInfoObject;

        RequestCondition<?> requestCondition = requestMappingInfo.getCustomCondition();
        if (!(requestCondition instanceof WebRouterDecisionCondition)) {
            return null;
        }

        WebRouterDecisionCondition condition = (WebRouterDecisionCondition) requestCondition;
        Collection<PathRouterDecisionMaker> pathRouterDecisionMakers = condition.getContent();
        return CollectionUtils.isEmpty(pathRouterDecisionMakers) ? null : new ArrayList<>(pathRouterDecisionMakers).get(0);
    }

    @Override
    public boolean matchStart(String pattern, String path) {
        return false;
    }

    @Override
    public String extractPathWithinPattern(String pattern, String path) {
        return null;
    }

    @Override
    public Map<String, String> extractUriTemplateVariables(String pattern, String path) {
        // 默认空实现，因为我们灰度条件是直接写死策略，或者从数据库中动态获取的，所以不需要从 path 中提取变量
        return null;
    }

    @Override
    public Comparator<String> getPatternComparator(String path) {
        return null;
    }

    @Override
    public String combine(String pattern1, String pattern2) {
        return null;
    }

}

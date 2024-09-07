package com.bryant.controller.constraint.router;

import com.bryant.config.constraint.RouterPatternKey;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.MapUtils;

/**
 * 每一次 Url 和 pattern 进行匹配的上下文
 * 1. pattern 路由规则
 * 2. url 用户请求的 uri
 * 3. pathVariables url 和 pattern 匹配时的路径变量
 * 4. routerPatternKey 一条路由的标识，由 pattern 和 constraint 约束构成
 */
public class RouterPathRequest {

    private final String pattern;

    private final String url;

    private final Map<String, String> pathVariables;

    private final RouterPatternKey routerPatternKey;

    private final String routeCondition;

    private final HttpServletRequest request;

    public RouterPathRequest(HttpServletRequest request, String pattern, String url, Map<String, String> pathVariables,
            RouterPatternKey routerPatternKey, String routeCondition) {
        this.request = request;
        this.pattern = pattern;
        this.pathVariables = pathVariables;
        this.url = url;
        this.routerPatternKey = routerPatternKey;
        this.routeCondition = routeCondition;
    }

    public static RouterPathRequest build(HttpServletRequest request, String pattern, String url,
            Map<String, String> pathVariables, RouterPatternKey routerPatternKey, String routeCondition) {
        return new RouterPathRequest(request, pattern, url, pathVariables, routerPatternKey, routeCondition);
    }

    public String getPathVariable(String name) {

        if (name == null || MapUtils.isEmpty(pathVariables)) {
            return null;
        }
        return pathVariables.get(name);
    }

    public String getUrl() {
        return url;
    }

    public String getPattern() {
        return pattern;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public Map<String, String> getPathVariables() {
        return this.pathVariables;
    }

    public RouterPatternKey getRouterPatternKey() {
        return this.routerPatternKey;
    }

    public String getRouteCondition() {
        return this.routeCondition;
    }

}

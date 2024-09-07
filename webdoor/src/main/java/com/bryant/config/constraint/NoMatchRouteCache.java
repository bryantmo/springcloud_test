package com.bryant.config.constraint;

import com.bryant.config.constraint.request_mapping.RouterPatternKey;
import com.bryant.config.constraint.request_mapping.WebRouterPathConstraintMatcher;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 对请求路径url无法匹配到的某些patterns进行缓存：
 * url
 * constraintKey-1 [pattern-1 + constraintClassName]
 * constraintKey-2 [pattern-2 + constraintClassName]
 * ...
 * constraintKey-n [pattern-n + constraintClassName]
 *
 * 这么设计的目的：
 * 一个路由的pattern为: /namespacePath/projectPath/issues/{id}
 *
 * @PathConstraint(GitIssueConstraint.class)
 * @RequestMapping("/namespacePath/projectPath/issues/{id}") public void method1() {}
 * @PathConstraint(SVNIssueConstraint.class)
 * @RequestMapping("/namespacePath/projectPath/issues/{id}") public void method2() {}
 *
 *         当用户请求 GET /code/project/issues/2 路径的时候，进行路径和pattern匹配不仅仅要匹配 pattern，而且要匹配 Constraint 约束条件。
 *         假设用户的请求路径和 method1 匹配时，发现 pattern 匹配上了，但是 Constraint 未匹配上，此时：
 *         将 pattern 缓存至 NoMatchRouteCache 中。
 *
 *         根据：{@link com.tencent.tgit.web.api.config.router.matcher.WebRouterPathConstraintMatcher#match(String, String)}
 *         的逻辑，请求路径继续和 method2 进行匹配：
 *         1. 判断缓存 NoMatchRouteCache 中是否有 method2 对应的  pattern。
 *         由于 method1 和 method2 的 pattern 一模一样，因此，由于请求已经和 method1 匹配过，pattern 已经在 NoMatchRouteCahce 中。
 *         2. 直接从 NoMatchRouteCache 中判断 pattern 一定匹配不通过，但是，却忽略了 method2 的 Constraint 路由约束。
 *         -----
 *         因此，缓存的时候，要缓存 pattern + constraint
 */
public class NoMatchRouteCache implements Serializable {

    public static final String NO_MATCH_ROUTE_CACHE_KEY = NoMatchRouteCache.class.getName() + ".noMatchRouteCacheKey";
    private static final long serialVersionUID = 1834740640530394954L;
    private final List<RouterPatternKey> noMatchedPatterns4Db;
    private String url;

    public NoMatchRouteCache(String url) {
        this.url = url;
        this.noMatchedPatterns4Db = new ArrayList<>();
    }

    public static void cache(HttpServletRequest request, String url, RouterPatternKey routerPatternKey) {
        NoMatchRouteCache savedNoMatchRouteCaches = (NoMatchRouteCache) request.getAttribute(NoMatchRouteCache.NO_MATCH_ROUTE_CACHE_KEY);
        if (ObjectUtils.isEmpty(savedNoMatchRouteCaches)) {
            savedNoMatchRouteCaches = new NoMatchRouteCache(url);
        }
        savedNoMatchRouteCaches.add(routerPatternKey);
        request.setAttribute(NO_MATCH_ROUTE_CACHE_KEY, savedNoMatchRouteCaches);
    }

    /**
     * 是否命中缓存
     * 命中缓存，表示未能匹配成功
     *
     * @param request
     * @param url 请求路径
     * @param routerPatternKey [pattern, RouterConstraint.class]
     * @see WebRouterPathConstraintMatcher#match(String, String)
     */
    public static boolean hitCache(HttpServletRequest request, String url, RouterPatternKey routerPatternKey) {
        NoMatchRouteCache noMatchRouteCache = (NoMatchRouteCache) request.getAttribute(
                NoMatchRouteCache.NO_MATCH_ROUTE_CACHE_KEY);
        return ObjectUtils.isNotEmpty(noMatchRouteCache)
                && StringUtils.equals(url, noMatchRouteCache.getUrl())
                && noMatchRouteCache.contains(routerPatternKey);
    }

    public static void resetCache(HttpServletRequest request) {
        request.removeAttribute(NO_MATCH_ROUTE_CACHE_KEY);
    }

    public void add(RouterPatternKey routerPatternKey) {
        if (Objects.isNull(routerPatternKey)) {
            return;
        }
        this.noMatchedPatterns4Db.add(routerPatternKey);
    }

    public boolean contains(RouterPatternKey routerPatternKey) {
        return noMatchedPatterns4Db.contains(routerPatternKey);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

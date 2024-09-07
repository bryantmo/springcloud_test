package com.bryant.config.constraint.request_mapping;

import com.bryant.config.constraint.PathMatchedConstant;
import com.bryant.controller.constraint.router.PathRouterDecisionMaker;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
public class AbstractRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    static String REQUEST_MAPPING_MATCHING = PathMatchedConstant.class.getName() + ".requestMappingMatching";

    private static final Method HTTP_OPTIONS_HANDLE_METHOD;

    static {
        try {
            HTTP_OPTIONS_HANDLE_METHOD = HttpOptionsHandler.class.getMethod("handle");
        } catch (NoSuchMethodException ex) {
            // Should never happen
            throw new IllegalStateException("Failed to retrieve internal handler method for HTTP OPTIONS", ex);
        }
    }

    private final PackagePathConstraintScanner scanner = new PackagePathConstraintScanner();
    private WebRouterDecisionMakerDetection webRouterDecisionMakerDetection;
    /*
       主要为包名和 @PathConstraint 中的 constraint 路由条件之间的映射关系
     */
    private Map<String, PathRouterDecisionMaker> packageRouterConstraintRegistry = new HashMap<>();

    public Map<String, PathRouterDecisionMaker> getPackageRouterConstraintRegistry() {
        return this.packageRouterConstraintRegistry;
    }

    public WebRouterDecisionMakerDetection getPathConstraintDetection() {
        return this.webRouterDecisionMakerDetection;
    }

    public void setPathConstraintDetection(WebRouterDecisionMakerDetection webRouterDecisionMakerDetection) {
        this.webRouterDecisionMakerDetection = webRouterDecisionMakerDetection;
    }

    public void registerPackageRouterConstraint() {
        long startTime = System.currentTimeMillis();
        // 如果没有注解修饰，scanPackagePathConstraint 为空
        ScanPackagePathConstraint scanPackagePathConstraint = AnnotatedElementUtils.findMergedAnnotation(this.getClass(), ScanPackagePathConstraint.class);

        if (ObjectUtils.isNotEmpty(scanPackagePathConstraint)
                && ArrayUtils.isNotEmpty(scanPackagePathConstraint.basePackageNames())) {
            this.packageRouterConstraintRegistry = scanner.scan(scanPackagePathConstraint.basePackageNames());
            this.setPathConstraintDetection(new WebRouterDecisionMakerDetection(this.packageRouterConstraintRegistry));
            long endTime = System.currentTimeMillis();
            log.info("Scan @PathConstraint from the packages: "
                    + Arrays.toString(scanPackagePathConstraint.basePackageNames())
                    + ", cost:" + (endTime - startTime) + "ms");
        }

    }

    @Override
    protected HandlerMethod handleNoMatch(Set<RequestMappingInfo> infos, String lookupPath, HttpServletRequest request) throws ServletException {
        return super.handleNoMatch(infos, lookupPath, request);
    }


    /**
     * Aggregate all partial matches and expose methods checking across them.
     */
    private static class PartialMatchHelper {

        private final List<PartialMatch> partialMatches = new ArrayList<>();

        /*
        这么设计的原因，请求路径和一系列 pattern 进行匹配：
        requestPath:
        RequestMapping-1
            - method
            - produces
            - [pattern-1, ..., pattern-n]
        RequestMapping-2
            - method
            - produces
            - [pattern-1, ..., pattern-n]
        ...
        RequestMapping-n
            - method
            - produces
            - [pattern-1, ..., pattern-n]

        匹配的顺序：先匹配 method、produces 等，最后是 pattern 匹配。匹配不到的原因主要如下：
        1. method、produces、consumes、header 等未匹配上。
        2. pattern 本身没有匹配上。
        3. pattern 匹配上，但是 contstraint 约束条件没有通过。

        针对 2.3. 两种情况，进行缓存处理 `NoMatchRouteCache` 进行缓存，缓存的时机：在路径和 pattern 匹配的时候，匹配失败的时候，将
        匹配的 pattern 和 请求路径进行缓存。
        参考：org.springframework.web.servlet.mvc.condition.PatternsRequestCondition.getMatchingCondition

        针对 1. 情况，spring mvc 源码中，将这些他们对应的 pattern 和 请求路径继续匹配一遍，筛选出能够匹配 pattern 对应的
        RequestMappingInfo。即：确保请求路径能够和某一个 pattern 完全可以匹配上。
        然后依次，匹配 Method、Produces等内容，告知用户未匹配上的原因。

        针对 1. 可能也会命中 `NoMatchRouteCache` 缓存。考虑如下场景：两个不同的 Controller 方法：
        ```
        @GetMapping("/{+namespacePath}/{projectPath}/issues/{iid}")
        public void method1() { }

        @PostMapping("/{+namespacePath}/{projectPath}/issues/{iid}")
        public void method2() { }
        ```
        用户使用 GET /code/tgitxx/issues/1 路径的请求时候，对于上述两个不同的 RequestMappingInfo。
        1. 匹配 method1 的时候：
            method 可以匹配上
            pattern 可以匹配上，
            constraint 无法匹配，需要读取 DB 进行数据校验
            将 RouterPatternKey 写入到缓存中。
        2. 匹配 method2 的时候：
            method 无法匹配上

         那么匹配完毕之后，执行到 `handleNoMatch` 的步骤，即如下代码时，针对于 pattern 进行匹配，会依次匹配 method1 和 method2 对应的
         pattern。由于两个pattern是一模一样的：
         a. 如果没有缓存，则需要针对所有的 patterns 进行循环匹配，因此针对 method1 和 method2 对应的 pattern 均需要匹配一次，且需要读 DB。
         b. 如果有缓存，则直接从缓存中确定即可。
         */
        public PartialMatchHelper(Set<RequestMappingInfo> infos, HttpServletRequest request) {
            for (RequestMappingInfo info : infos) {
                request.setAttribute(REQUEST_MAPPING_MATCHING, info);
                if (info.getPatternsCondition().getMatchingCondition(request) != null) {
                    this.partialMatches.add(new PartialMatch(info, request));
                }
                request.removeAttribute(REQUEST_MAPPING_MATCHING);
            }
        }

        /**
         * Whether there any partial matches.
         */
        public boolean isEmpty() {
            return this.partialMatches.isEmpty();
        }

        /**
         * Any partial matches for "methods"?
         */
        public boolean hasMethodsMismatch() {
            for (PartialMatch match : this.partialMatches) {
                if (match.hasMethodsMatch()) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Any partial matches for "methods" and "consumes"?
         */
        public boolean hasConsumesMismatch() {
            for (PartialMatch match : this.partialMatches) {
                if (match.hasConsumesMatch()) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Any partial matches for "methods", "consumes", and "produces"?
         */
        public boolean hasProducesMismatch() {
            for (PartialMatch match : this.partialMatches) {
                if (match.hasProducesMatch()) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Any partial matches for "methods", "consumes", "produces", and "params"?
         */
        public boolean hasParamsMismatch() {
            for (PartialMatch match : this.partialMatches) {
                if (match.hasParamsMatch()) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Return declared HTTP methods.
         */
        public Set<String> getAllowedMethods() {
            Set<String> result = new LinkedHashSet<>();
            for (PartialMatch match : this.partialMatches) {
                for (RequestMethod method : match.getInfo().getMethodsCondition().getMethods()) {
                    result.add(method.name());
                }
            }
            return result;
        }

        /**
         * Return declared "consumable" types but only among those that also
         * match the "methods" condition.
         */
        public Set<MediaType> getConsumableMediaTypes() {
            Set<MediaType> result = new LinkedHashSet<>();
            for (PartialMatch match : this.partialMatches) {
                if (match.hasMethodsMatch()) {
                    result.addAll(match.getInfo().getConsumesCondition().getConsumableMediaTypes());
                }
            }
            return result;
        }

        /**
         * Return declared "producible" types but only among those that also
         * match the "methods" and "consumes" conditions.
         */
        public Set<MediaType> getProducibleMediaTypes() {
            Set<MediaType> result = new LinkedHashSet<>();
            for (PartialMatch match : this.partialMatches) {
                if (match.hasConsumesMatch()) {
                    result.addAll(match.getInfo().getProducesCondition().getProducibleMediaTypes());
                }
            }
            return result;
        }

        /**
         * Return declared "params" conditions but only among those that also
         * match the "methods", "consumes", and "params" conditions.
         */
        public List<String[]> getParamConditions() {
            List<String[]> result = new ArrayList<>();
            for (PartialMatch match : this.partialMatches) {
                if (match.hasProducesMatch()) {
                    Set<NameValueExpression<String>> set = match.getInfo().getParamsCondition().getExpressions();
                    if (!org.springframework.util.CollectionUtils.isEmpty(set)) {
                        int i = 0;
                        String[] array = new String[set.size()];
                        for (NameValueExpression<String> expression : set) {
                            array[i++] = expression.toString();
                        }
                        result.add(array);
                    }
                }
            }
            return result;
        }


        /**
         * Container for a RequestMappingInfo that matches the URL path at least.
         */
        private static class PartialMatch {

            private final RequestMappingInfo info;

            private final boolean methodsMatch;

            private final boolean consumesMatch;

            private final boolean producesMatch;

            private final boolean paramsMatch;

            /**
             * Create a new {@link PartialMatch} instance.
             *
             * @param info the RequestMappingInfo that matches the URL path.
             * @param request the current request
             */
            public PartialMatch(RequestMappingInfo info, HttpServletRequest request) {
                this.info = info;
                this.methodsMatch = (info.getMethodsCondition().getMatchingCondition(request) != null);
                this.consumesMatch = (info.getConsumesCondition().getMatchingCondition(request) != null);
                this.producesMatch = (info.getProducesCondition().getMatchingCondition(request) != null);
                this.paramsMatch = (info.getParamsCondition().getMatchingCondition(request) != null);
            }

            public RequestMappingInfo getInfo() {
                return this.info;
            }

            public boolean hasMethodsMatch() {
                return this.methodsMatch;
            }

            public boolean hasConsumesMatch() {
                return (hasMethodsMatch() && this.consumesMatch);
            }

            public boolean hasProducesMatch() {
                return (hasConsumesMatch() && this.producesMatch);
            }

            public boolean hasParamsMatch() {
                return (hasProducesMatch() && this.paramsMatch);
            }

            @Override
            public String toString() {
                return this.info.toString();
            }
        }
    }


    /**
     * Default handler for HTTP OPTIONS.
     */
    private static class HttpOptionsHandler {

        private final HttpHeaders headers = new HttpHeaders();

        public HttpOptionsHandler(Set<String> declaredMethods) {
            this.headers.setAllow(initAllowedHttpMethods(declaredMethods));
        }

        private static Set<HttpMethod> initAllowedHttpMethods(Set<String> declaredMethods) {
            Set<HttpMethod> result = new LinkedHashSet<>(declaredMethods.size());
            if (declaredMethods.isEmpty()) {
                for (HttpMethod method : HttpMethod.values()) {
                    if (method != HttpMethod.TRACE) {
                        result.add(method);
                    }
                }
            } else {
                for (String method : declaredMethods) {
                    HttpMethod httpMethod = HttpMethod.valueOf(method);
                    result.add(httpMethod);
                    if (httpMethod == HttpMethod.GET) {
                        result.add(HttpMethod.HEAD);
                    }
                }
                result.add(HttpMethod.OPTIONS);
            }
            return result;
        }

        @SuppressWarnings("unused")
        public HttpHeaders handle() {
            return this.headers;
        }
    }
}

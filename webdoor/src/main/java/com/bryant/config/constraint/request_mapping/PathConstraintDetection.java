package com.bryant.config.constraint.request_mapping;

import com.bryant.controller.constraint.router.PathRouterDecisionMaker;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class PathConstraintDetection {

    public Map<String, PathRouterDecisionMaker> packagePathConstraintRegistry = new HashMap<>();

    public PathConstraintDetection(Map<String, PathRouterDecisionMaker> packagePathConstraintRegistry) {
        this.packagePathConstraintRegistry = packagePathConstraintRegistry;
    }

    /**
     * 判断方法中是否被 @PathConstraint 注解装饰
     *
     * @param handlerMethod
     * @return
     */
    public PathRouterDecisionMaker detect(Method handlerMethod) {
        if (Objects.isNull(handlerMethod)) {
            return null;
        }

        return AnnotatedElementUtils.findMergedAnnotation(handlerMethod, PathRouterDecisionMaker.class);
    }

    /**
     * 判断 Controller 是否被 @PathConstraint 注解修饰
     *
     * @param beanType
     * @return
     */
    public PathRouterDecisionMaker detect(Class<?> beanType) {
        if (ObjectUtils.isEmpty(beanType)) {
            return null;
        }
        return AnnotatedElementUtils.findMergedAnnotation(beanType, PathRouterDecisionMaker.class);
    }

    /**
     * 根据 package 获取包中是否包含 package-info.java 且被 @PathConstraint 注解修饰，获取过程：
     * 1. 获取 package 和 @PathConstraint 之间的映射关系。
     * 2. 根据映射关系和指定的 packageInfo 进行查找:
     * - 映射关系中是否包含指定 packageInfo 下 package-info.java 上的 @PathConstraint 注解。
     * - 映射关系中是否包含 packageInfo 父级 package 下 package-info.java 上的 @PathConstraint 注解。
     * ...
     *
     * @param packageInfo
     * @return
     */
    public PathRouterDecisionMaker detect(Package packageInfo) {
        if (ObjectUtils.isEmpty(packageInfo)) {
            return null;
        }

        String packageInfoName = packageInfo.getName();
        if (StringUtils.isEmpty(packageInfoName)) {
            return null;
        }

        /*
         1. @ScanPackagePathConstraint 没有指定 basePackageNames时，this.packagePathConstraintRegistry 为空
         2. 兜底方案：直接获取 packageInfo 上是否被 @PathConstraint 注解修饰
         */
        if (MapUtils.isEmpty(this.packagePathConstraintRegistry)) {
            // 获取当前 method 对应的 package 中的 package-info.java 上的路由约束条件
            // 可能会不存在，直接返回 null
            return AnnotatedElementUtils.findMergedAnnotation(packageInfo, PathRouterDecisionMaker.class);
        }

        Set<String> packageNameSet = this.packagePathConstraintRegistry.keySet();
        List<List<String>> packageNameCollection = new ArrayList<>();
        for (String packageName : packageNameSet) {
            if (StringUtils.isEmpty(packageName)) {
                continue;
            }
            packageNameCollection.add(new ArrayList<>(Arrays.asList(StringUtils.split(packageName, "."))));
        }

        if (CollectionUtils.isEmpty(packageNameCollection)) {
            // 兜底措施
            return AnnotatedElementUtils.findMergedAnnotation(packageInfo, PathRouterDecisionMaker.class);
        }

        // 根据 packageInfo 获取到映射关系 packagePathConstraintRegistry 中【距离最近】的 package 包
        List<String> targetPackageNameList = new ArrayList<>(Arrays.asList(StringUtils.split(packageInfoName, ".")));
        int max = Integer.MIN_VALUE;
        List<String> matchedPackage = null;
        for (List<String> list : packageNameCollection) {
            int index = Collections.indexOfSubList(targetPackageNameList, list);
            if (index == 0 && list.size() > max) {
                max = list.size();
                matchedPackage = list;
            }
        }

        if (CollectionUtils.isEmpty(matchedPackage)) {
            return AnnotatedElementUtils.findMergedAnnotation(packageInfo, PathRouterDecisionMaker.class);
        }
        return this.packagePathConstraintRegistry.get(StringUtils.join(matchedPackage, "."));
    }
}

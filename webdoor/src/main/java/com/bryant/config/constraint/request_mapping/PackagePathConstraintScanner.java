package com.bryant.config.constraint.request_mapping;


import com.bryant.controller.constraint.router.PathConstraint;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Slf4j
public class PackagePathConstraintScanner {

    private static final String PACKAGE_INFO = "package-info";

    private static final Predicate<ClassInfo> predicatePathInfoAnnotatedByPathConstraint = classInfo -> {
        try {
            Class<?> type = classInfo.load();
            return StringUtils.equals(type.getSimpleName(), PACKAGE_INFO)
                    && AnnotatedElementUtils.isAnnotated(type, PathConstraint.class);
        } catch (Throwable e) {
            return false;
        }
    };

    /**
     * 扫描指定包下的所有的：被 @PathConstraint 修饰的 package-info.java 类
     * 最终创建包名和 PathConstraint 之间的映射关系：
     * packageName -> PathConstraint
     * ...
     * packageName -> PathConstraint
     *
     * 设计的目的：
     * Server 启动阶段，spring 创建路由元信息 RequestMappingInfo 中 customCondition 时，根据路由对应 method 的 @PathConstraint
     * 注解。获取的过程如下：
     * 1. 确定 method 是否被 @PathConstraint 注解修饰
     * 2. 确定 class 是否被 @PathConstraint 注解修饰
     * 3. 确定 package 是否被 @PathConstraint 注解修饰
     *
     * 确定 package 上是否被 @PathConstraint 注解修饰的过程，确定扫描哪些 package？
     * 在 server 启动阶段，根据 {@link ScanPackagePathConstraint#basePackageNames()} 指定的包名，扫描这些包下面所有的package 和
     * PathConstraint 之间的映射关系。
     * 即：在确定 package 是否被 @PathConstraint 注解修饰的时候，仅在上述的映射关系对应的包中进行查找。
     *
     * 参考： com.tencent.tgit.web.config.router.WebRequestMappingHandlerMapping#getCustomMethodCondition(Method)
     *
     * @param basePackageNames
     * @return
     * @see WebRequestMappingHandlerMapping#registerPackageRouterConstraint()
     * @see ScanPackagePathConstraint
     */
    public Map<String, PathConstraint> scan(String[] basePackageNames) {
        Map<String, PathConstraint> packageRouterConstraintRegistry = new HashMap<>();
        if (ArrayUtils.isEmpty(basePackageNames)) {
            return packageRouterConstraintRegistry;
        }

        List<ClassInfo> classInfoList = new ArrayList<>();
        try {
            for (String basePackageName : basePackageNames) {
                if (StringUtils.isEmpty(basePackageName)) {
                    continue;
                }

                ImmutableSet<ClassInfo> classInfoImmutableSet = ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClassesRecursive(basePackageName);
                if (CollectionUtils.isEmpty(classInfoImmutableSet)) {
                    continue;
                }
                classInfoList.addAll(classInfoImmutableSet.stream().filter(predicatePathInfoAnnotatedByPathConstraint).collect(Collectors.toList()));
            }
            if (CollectionUtils.isEmpty(classInfoList)) {
                return packageRouterConstraintRegistry;
            }

            for (ClassInfo classInfo : classInfoList) {
                try {
                    Class<?> type = classInfo.load();
                    if (!ObjectUtils.isEmpty(type) && !ObjectUtils.isEmpty(type.getPackage())) {
                        PathConstraint pathConstraint = AnnotatedElementUtils.findMergedAnnotation(type, PathConstraint.class);
                        if (ObjectUtils.isEmpty(pathConstraint) || ObjectUtils.isEmpty(pathConstraint.constraint())) {
                            continue;
                        }
                        String packageName = type.getPackage().getName();
                        if (StringUtils.isEmpty(packageName)) {
                            continue;
                        }
                        packageRouterConstraintRegistry.put(packageName, pathConstraint);
                    }

                } catch (Throwable e) {
                    log.error("Scan @PathConstraint from the packages error", e.getMessage());
                    continue;
                }
            }
        } catch (Throwable e) {
            log.error("Scan @PathConstraint from the packages error", e.getMessage());
        }
        return packageRouterConstraintRegistry;
    }
}

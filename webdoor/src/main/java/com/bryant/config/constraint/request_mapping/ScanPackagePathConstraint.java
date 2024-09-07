package com.bryant.config.constraint.request_mapping;

import com.bryant.controller.constraint.router.PathRouterDecisionMaker;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @PathConstraint 作为路由的约束条件，允许修饰：
 *         1. method 方法
 *         2. class 类型
 *         3. package-info.java
 *
 *         注解 @EnablepPackagePathConstraint(basePackageName = "") 修饰
 *         {@link WebRequestMappingHandlerMapping}
 *
 *         针对于修饰 package-info.java 的时候，需要指定 spring mvc 扫描 package-info.java 的 basePackageName。如下所示：
 *
 *         ```
 * @ScanPackagePathConstraint(basePackageName = "com.")
 *         ```
 *         上述配置表示 spring mvc 从当前类路径的 "com." 包下开始扫描，扫描出所有的 package-info.java 文件，获得对应的路径约束条件。
 *
 *         获取一个路由 Controller 方法的约束条件，顺序如下：
 *         1. 判断 method 上是否被 @PathConstraint 修饰。
 *         2. 判断 method 对应的 class 是否被 @PathConstraint 修饰。
 *         3. 判断 method 所在的 package 或者父级 package 中的 package-info.java 是否被 @PathConstraint 修饰。
 *
 *         对于第三步：扫描 package 或者父级 package 中的 package-info.java 文件，此注解 @ScanPackagePathConstraint 中的
 *         basePackageName 指定扫描的根 package 名称。
 *         扫描 package-info.java 的优先级：当前package > 上一级 package > 上上一级 package ... > basePackageName
 *
 *         如果注解 @ScanPackagePathConstraint 未指定 basePackageName，则默认仅在 method 所处的当前 package 进行扫描，
 *         判断是否存在 package-info.java。
 * @see PathRouterDecisionMaker
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ScanPackagePathConstraint {

    String[] basePackageNames() default {};
}

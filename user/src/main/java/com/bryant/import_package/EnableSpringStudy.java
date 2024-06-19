package com.bryant.import_package;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 自定义了一个注解，类似于@EnableEurekaClient、@EnableDiscoveryClient、@EnableScheduleLock等
 *
 * 并使用了@Import导入我们刚才写的 SpringStudySelector
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import(SpringStudySelector.class)
public @interface EnableSpringStudy {
}
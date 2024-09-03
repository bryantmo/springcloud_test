package com.bryant.controller.constraint.router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE, ElementType.PACKAGE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PathConstraint {
    Class<? extends RouterConstraints> constraint();
    String resourceCondition() default "";
    int order() default 0;
}

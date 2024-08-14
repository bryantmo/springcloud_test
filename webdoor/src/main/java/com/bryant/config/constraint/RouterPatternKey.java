package com.bryant.config.constraint;

import com.bryant.controller.constraint.router.PathConstraint;
import com.bryant.controller.constraint.router.RouterConstraints;
import java.util.Objects;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

/**
 * 区分 pattern 的key，由如下部分构成：
 * - pattern
 * - routerConstraint
 * - @PathConstraint#constraint 全限定类名
 * - @PathConstraint#resourceCondition 资源约束条件
 */
public class RouterPatternKey {

    private final String pattern;

    private final String routerConstraints;

    public RouterPatternKey(String pattern, PathConstraint pathConstraint) {
        this.pattern = pattern;
        if (ObjectUtils.isEmpty(pathConstraint)) {
            this.routerConstraints = StringUtils.EMPTY;
        } else {

            Class<? extends RouterConstraints> type = pathConstraint.constraint();
            String condition = pathConstraint.resourceCondition();

            // 注意：routerConstraints 被代理多次，对应的 class 信息变化。获取其最原始的 class 信息，使用 spring 工具类 ClassUtils 获取。
            String typeName = ObjectUtils.isEmpty(type) ? StringUtils.EMPTY :
                    ClassUtils.getUserClass(type).getName();

            this.routerConstraints = typeName + (StringUtils.isEmpty(condition) ? StringUtils.EMPTY : condition);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RouterPatternKey)) {
            return false;
        }
        RouterPatternKey that = (RouterPatternKey) o;
        return StringUtils.equals(pattern, that.pattern) && StringUtils.equals(routerConstraints,
                that.routerConstraints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern, routerConstraints);
    }

    @Override
    public String toString() {
        return this.pattern + ":" + this.routerConstraints;
    }
}

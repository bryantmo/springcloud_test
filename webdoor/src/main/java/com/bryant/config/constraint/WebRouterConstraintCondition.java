package com.bryant.config.constraint;

import com.bryant.controller.constraint.router.PathConstraint;
import com.bryant.controller.constraint.router.RouterConstraints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;

public class WebRouterConstraintCondition extends AbstractRequestCondition<WebRouterConstraintCondition> {

    private final PathConstraint pathConstraint;

    public WebRouterConstraintCondition(PathConstraint pathConstraint) {
        this.pathConstraint = pathConstraint;
    }

    @Override
    public Collection<PathConstraint> getContent() {
        if (ObjectUtils.isEmpty(this.pathConstraint)) {
            return new ArrayList<>(Collections.singletonList(null));
        }
        return new ArrayList<>(Collections.singletonList(this.pathConstraint));
    }

    @Override
    public String toString() {

        Class<? extends RouterConstraints> routerConstraint = ObjectUtils.isEmpty(this.pathConstraint) ? null : this.pathConstraint.constraint();

        return ObjectUtils.isEmpty(routerConstraint) ? StringUtils.EMPTY : ClassUtils.getUserClass(routerConstraint).getName();
    }

    @Override
    protected String getToStringInfix() {
        return "[]";
    }

    /**
     * 创建 RequestMappingInfo 的时候，会进行两件事情：
     * 1. 查看 method 上的 @RequestMapping 信息，同时根据 method 类型，创建 condition。
     * WebRequestMappingHandlerMapping#getCustomMethodCondition(Method)
     * - 创建相应的 condition
     * 2. 查看 Controller 上的 @RequestMapping 信息，同时根据 Controller 类型，创建 condition。
     * WebRequestMappingHandlerMapping#getCustomTypeCondition(Class)
     * - 创建相应的 condition
     *
     * 注意：创建之前，会判断 `@RequestMapping` 分别在 Method 和 Controller 上是否存在，如果不存在，则不会创建对应的 Condition。
     * 如果都存在，则会执行下面的 combine 方法，进行结合。
     * 但是，仅仅将 @PathConstraint 约束类名作为 condition【仅装饰类和package】。在 spring mvc 框架判断获取类上面的 @RequestMapping
     * 还是方法上面的 @RequestMapping 信息。获取 condition 的顺序：
     * 1. 优先获取 class 类上的 @PathConstraint
     * 2. 其次获取 package-info.java 上的 @PathConstraint
     * 因此， 不存在 combine 方法意义不大，返回 this 即可。
     *
     * 查看：org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping#createRequestMappingInfo(AnnotatedElement)
     *
     * @param other
     * @return
     */
    @Override
    public WebRouterConstraintCondition combine(WebRouterConstraintCondition other) {
        return other;
    }

    @Override
    public WebRouterConstraintCondition getMatchingCondition(HttpServletRequest request) {
        boolean isDirectUrlMatched = DirectPathRouterMatchCondition.isDirectUrlMatch(request);
        if (!isDirectUrlMatched) {
            // 如果是非直接路径匹配，则在 com.tencent.tgit.web.api.config.router.matcher.WebRouterPathConstraintMatcher.match 阶段已经进行 PathConstraint 匹配
            return this;
        }
        // 直接路径匹配
        return DirectPathRouterMatchCondition.isDirectUrlMatchPathConstraint(request, this.pathConstraint) ? this : null;
    }

    @Override
    public int compareTo(WebRouterConstraintCondition other, HttpServletRequest request) {
        PathConstraint otherPathConstraint = other.pathConstraint;
        PathConstraint pathConstraint = this.pathConstraint;
        if (otherPathConstraint == null && pathConstraint == null) {
            return 0;
        } else if (pathConstraint == null) {
            // this that 交换顺序
            return 1;
        } else if (otherPathConstraint == null) {
            // this that 保持顺序
            return -1;
        } else {
            // @PathConstraint 中非必要属性: resourceCondition
            String otherResourceCondition = otherPathConstraint.resourceCondition();
            String thisResourceCondition = pathConstraint.resourceCondition();
            if (StringUtils.isEmpty(otherResourceCondition) && StringUtils.isEmpty(thisResourceCondition)) {
                return 0;
            } else if (StringUtils.isEmpty(otherResourceCondition)) {
                return -1;
            } else if (StringUtils.isEmpty(thisResourceCondition)) {
                return 1;
            } else {
                int otherOrder = otherPathConstraint.order();
                int thisOrder = pathConstraint.order();
                if (otherOrder > thisOrder) {
                    return 1;
                } else if (otherOrder < thisOrder) {
                    return -1;
                }
                // resourceCondition 均为不为空
                return 0;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebRouterConstraintCondition)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        WebRouterConstraintCondition that = (WebRouterConstraintCondition) o;
        boolean isConstraintClassEquals = Objects.equals(that.getContent(), this.getContent());
        String conditionThis = ObjectUtils.isEmpty(this.pathConstraint) ? StringUtils.EMPTY : this.pathConstraint.resourceCondition();
        String conditionThat = ObjectUtils.isEmpty(that.pathConstraint) ? StringUtils.EMPTY : that.pathConstraint.resourceCondition();
        return isConstraintClassEquals && StringUtils.equals(conditionThat, conditionThis);
    }

    @Override
    public int hashCode() {
        String condition = ObjectUtils.isEmpty(pathConstraint) ? StringUtils.EMPTY : pathConstraint.resourceCondition();
        Class<? extends RouterConstraints> type = ObjectUtils.isEmpty(pathConstraint) ? null : pathConstraint.constraint();
        return Objects.hash(condition, type);
    }
}
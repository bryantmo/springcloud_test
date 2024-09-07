package com.bryant.config.constraint.request_mapping;

import com.bryant.config.constraint.DirectPathRouterMatchCondition;
import com.bryant.controller.constraint.router.PathRouterDecisionMaker;
import com.bryant.controller.constraint.router.RouterDecisionMaker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;

/**
 * 自定义的路由匹配条件
 */
public class WebRouterDecisionCondition extends AbstractRequestCondition<WebRouterDecisionCondition> {

    private final PathRouterDecisionMaker pathRouterDecisionMaker;

    public WebRouterDecisionCondition(PathRouterDecisionMaker pathRouterDecisionMaker) {
        this.pathRouterDecisionMaker = pathRouterDecisionMaker;
    }

    @Override
    public Collection<PathRouterDecisionMaker> getContent() {
        if (ObjectUtils.isEmpty(this.pathRouterDecisionMaker)) {
            return new ArrayList<>(Collections.singletonList(null));
        }
        return new ArrayList<>(Collections.singletonList(this.pathRouterDecisionMaker));
    }

    @Override
    public String toString() {

        Class<? extends RouterDecisionMaker> routerConstraint = ObjectUtils.isEmpty(this.pathRouterDecisionMaker) ? null : this.pathRouterDecisionMaker.decision();

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
     * 查看：org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping#createRequestMappingInfo(AnnotatedElement)
     *
     * @param other
     * @return
     */
    @Override
    public WebRouterDecisionCondition combine(WebRouterDecisionCondition other) {
        return other;
    }

    @Override
    public WebRouterDecisionCondition getMatchingCondition(HttpServletRequest request) {// 运行时，获取methodHandler，会回调该函数；用到DirectPathRouterMatchCondition对路由进行匹配
        boolean isDirectUrlMatched = DirectPathRouterMatchCondition.isDirectUrlMatch(request);
        if (!isDirectUrlMatched) {
            return this;
        }
        return DirectPathRouterMatchCondition.isDirectUrlMatchPathConstraint(request, this.pathRouterDecisionMaker) ? this : null;
    }

    @Override
    public int compareTo(WebRouterDecisionCondition other, HttpServletRequest request) {
        PathRouterDecisionMaker otherPathRouterDecisionMaker = other.pathRouterDecisionMaker;
        PathRouterDecisionMaker pathRouterDecisionMaker = this.pathRouterDecisionMaker;
        if (otherPathRouterDecisionMaker == null && pathRouterDecisionMaker == null) {
            return 0;
        } else if (pathRouterDecisionMaker == null) {
            // this that 交换顺序
            return 1;
        } else if (otherPathRouterDecisionMaker == null) {
            // this that 保持顺序
            return -1;
        } else {
            // @PathConstraint 中非必要属性: resourceCondition
            String otherResourceCondition = otherPathRouterDecisionMaker.resourceCondition();
            String thisResourceCondition = pathRouterDecisionMaker.resourceCondition();
            if (StringUtils.isEmpty(otherResourceCondition) && StringUtils.isEmpty(thisResourceCondition)) {
                return 0;
            } else if (StringUtils.isEmpty(otherResourceCondition)) {
                return -1;
            } else if (StringUtils.isEmpty(thisResourceCondition)) {
                return 1;
            } else {
                int otherOrder = otherPathRouterDecisionMaker.order();
                int thisOrder = pathRouterDecisionMaker.order();
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
        if (!(o instanceof WebRouterDecisionCondition)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        WebRouterDecisionCondition that = (WebRouterDecisionCondition) o;
        boolean isConstraintClassEquals = Objects.equals(that.getContent(), this.getContent());
        String conditionThis = ObjectUtils.isEmpty(this.pathRouterDecisionMaker) ? StringUtils.EMPTY : this.pathRouterDecisionMaker.resourceCondition();
        String conditionThat = ObjectUtils.isEmpty(that.pathRouterDecisionMaker) ? StringUtils.EMPTY : that.pathRouterDecisionMaker.resourceCondition();
        return isConstraintClassEquals && StringUtils.equals(conditionThat, conditionThis);
    }

    @Override
    public int hashCode() {
        String condition = ObjectUtils.isEmpty(pathRouterDecisionMaker) ? StringUtils.EMPTY : pathRouterDecisionMaker.resourceCondition();
        Class<? extends RouterDecisionMaker> type = ObjectUtils.isEmpty(pathRouterDecisionMaker) ? null : pathRouterDecisionMaker.decision();
        return Objects.hash(condition, type);
    }
}

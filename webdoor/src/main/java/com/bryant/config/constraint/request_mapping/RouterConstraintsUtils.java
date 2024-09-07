package com.bryant.config.constraint.request_mapping;

import com.bryant.controller.constraint.router.RouterDecisionMaker;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationContext;

public class RouterConstraintsUtils {

    /**
     * 获取 spring 上下文中 RouterConstraints 类的实例化对象
     *
     * @param routerConstraintClass
     * @return
     */
    public static RouterDecisionMaker getRouterConstraint(Class<? extends RouterDecisionMaker> routerConstraintClass) {
        if (ObjectUtils.isEmpty(routerConstraintClass)) {
            return null;
        }

        // routerConstraintClass 可能会被继承，因此，一个 RouterConstraintClass 可能会对应多个实例对象，此处可能会报异常
        // 此处限制：RouterConstraintClass 只能获取到其本身的 spring bean 类
        ApplicationContext context = SpringContext.getApplicationContext();
        Map<String, ? extends RouterDecisionMaker> beanMap = context.getBeansOfType(routerConstraintClass);
        if (MapUtils.isEmpty(beanMap)) {
            return null;
        }

        for (RouterDecisionMaker routerConstraint : beanMap.values()) {
            if (routerConstraint.getClass() == routerConstraintClass) {
                return routerConstraint;
            }
        }
        return null;
    }

}
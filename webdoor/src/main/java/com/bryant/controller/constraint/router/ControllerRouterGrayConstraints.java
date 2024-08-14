package com.bryant.controller.constraint.router;

import org.springframework.stereotype.Component;

/**
 * Git 业务中，web-pc 的前端 api 路由的约束实现
 */
@Component(value = "ControllerRouterGrayConstraints")
public class ControllerRouterGrayConstraints extends ControllerRouterDynamicGrayConstraints {

    @Override
    public boolean matches(PathPartRequest pathPartRequest) {
//        return super.matches(pathPartRequest);
        return Boolean.TRUE;
    }

}

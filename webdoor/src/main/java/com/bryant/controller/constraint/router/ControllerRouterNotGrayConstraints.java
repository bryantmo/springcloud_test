package com.bryant.controller.constraint.router;

import org.springframework.stereotype.Component;

@Component("ApiDashboardNotGrayConstraints")
public class ControllerRouterNotGrayConstraints extends ControllerRouterDynamicGrayConstraints {

    /**
     * 取反，跟ControllerRouterGrayConstraints互斥
     * @param pathPartRequest
     * @return
     */
    @Override
    public boolean matches(PathPartRequest pathPartRequest) {
//        return !super.matches(pathPartRequest);
        return Boolean.FALSE;
    }
}

package com.bryant.controller.constraint.router;

import org.springframework.stereotype.Component;

@Component(value = "ControllerRouterGrayConstraints")
public class ControllerRouterGrayConstraints extends ControllerRouterDynamicGrayConstraints {

    @Override
    public boolean matches(PathPartRequest pathPartRequest) {
//        return super.matches(pathPartRequest);
        return Boolean.TRUE;
    }

}

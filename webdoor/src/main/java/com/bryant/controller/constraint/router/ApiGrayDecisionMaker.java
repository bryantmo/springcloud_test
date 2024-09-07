package com.bryant.controller.constraint.router;

import org.springframework.stereotype.Component;

@Component(value = "ApiGrayDecisionMaker")
public class ApiGrayDecisionMaker implements RouterDecisionMaker {

    @Override
    public boolean matches(RouterPathRequest routerPathRequest) {
//        return super.matches(pathPartRequest);
        return Boolean.TRUE;
    }

}

package com.bryant.controller.constraint.router;

import org.springframework.stereotype.Component;

@Component("ApiNotGrayDecisionMaker")
public class ApiNotGrayDecisionMaker implements RouterDecisionMaker {

    /**
     * 取反，跟 ApiGrayDecision#matches 互斥
     * @param routerPathRequest
     * @return
     */
    @Override
    public boolean matches(RouterPathRequest routerPathRequest) {
//        return !super.matches(pathPartRequest);
        return Boolean.FALSE;
    }
}

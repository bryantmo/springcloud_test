package com.bryant.controller.constraint.router;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ControllerRouterDynamicGrayConstraints implements RouterDecisionMaker {

    @Override
    public boolean matches(RouterPathRequest routerPathRequest) {
        int num = new Random().nextInt(100);
        log.info("match random value = {}, result = {}", num, (num > 50));
        return (num > 50);
    }

}

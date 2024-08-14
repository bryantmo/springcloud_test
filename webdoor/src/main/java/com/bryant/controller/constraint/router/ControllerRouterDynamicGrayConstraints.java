package com.bryant.controller.constraint.router;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;

/**
 * Git 业务中，web-pc 的前端 api 路由的约束实现
 */
@Slf4j
public class ControllerRouterDynamicGrayConstraints implements RouterConstraints {

    @Override
    public boolean matches(PathPartRequest pathPartRequest) {
        int num = new Random().nextInt(100);
        log.info("match random value = {}, result = {}", num, (num > 50));
        return (num > 50);
    }

}

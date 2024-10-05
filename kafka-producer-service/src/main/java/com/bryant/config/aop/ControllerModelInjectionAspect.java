package com.bryant.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ControllerModelInjectionAspect {

    @Pointcut("execution(public * com.bryant.controller.user.AopController.test(..))"
        + "|| execution(public * com.bryant.controller.user.AopController.testControllerEx(..))"
        + "|| execution(public * com.bryant.controller.user.UsersController.batchCreate(..))"
    )
    public void pointCutControllerMethod() {
        log.info("=====aop:pointCutControllerMethod=====");
    }

    @Before("pointCutControllerMethod()")
    public void beforePointCut() {
        log.info("=====aop:beforePointCut=====");
    }

    @After("pointCutControllerMethod()")
    public void afterPointCut() {
        log.info("=====aop:afterPointCut=====");
    }

//    @Around("pointCutControllerMethod()")
//    public void aroundPointCut() {
//        log.info("=====aop:aroundPointCut=====");
//    }

    @AfterReturning("pointCutControllerMethod()")
    public void afterReturningPointCut() {
        log.info("=====aop:afterReturningPointCut=====");
    }

    @AfterThrowing("pointCutControllerMethod()")
    public void afterThrowingPointCut() {
        log.info("=====aop:afterThrowingPointCut=====");
    }

}

package com.bryant.config.bean_init;

import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

@Slf4j
public class TestBean implements DisposableBean {

    private String name;

    public TestBean() {
        log.info("TestBean init");
    }

    public String getName() {
        return name;
    }

    /**
     * 初始化bean方法
     */
    public void initMethod() {
        name = "testBean";
    }

    /**
     * 销毁bean方法
     */
    @PreDestroy
    public void destroyMethod2() {
        log.info("@PreDestroy指定的前置方法: testBean destroyMethod bean销毁方法");
    }

    public void destroyMethod(){
        log.info("@Bean指定的销毁方法：testBean destroyMethod bean销毁方法");
    }

    @Override
    public void destroy() throws Exception {
        log.info("DisposableBean接口指定的销毁方法：testBean destroy bean销毁方法");
    }
}

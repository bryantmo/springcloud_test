package com.bryant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class TestController {

    /**
     * 除了通过eValue注解绑定注入之外，也可以通过Environment 对象来获取配置 属性，比如:注入Environment
     */
    @Value("${from}")
    private String from;

    @Autowired
    private Environment environment;

    @GetMapping("/from_test")
    public String getFrom() {
        return "Hello from " + from;
    }

    @GetMapping("/from_test2")
    public String getFrom2() {
        return "Hello from " + environment.getProperty("from", "undefined");
    }

}

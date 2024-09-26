package com.bryant.controller;

import com.bryant.config.bean_init.lazy_bean_init.TestLazyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestLazyBean testLazyBean;
    @GetMapping("/lazy_initlizate")
    public String lazyInitlizate()
    {
        return testLazyBean.getName();
    }
}

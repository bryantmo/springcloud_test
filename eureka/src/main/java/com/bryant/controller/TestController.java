package com.bryant.controller;

import com.bryant.config.EurekaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app")
public class TestController {

    @Autowired
    private EurekaConfig eurekaConfig;

    @RequestMapping("/test")
    @ResponseBody
    public String testDemo() {
        return eurekaConfig.getUserName();
    }
}

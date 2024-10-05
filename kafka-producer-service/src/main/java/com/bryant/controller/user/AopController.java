package com.bryant.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
public class AopController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/controller_ex")
    public String testControllerEx() {
        throw new RuntimeException("test_controller_ex");
    }
}

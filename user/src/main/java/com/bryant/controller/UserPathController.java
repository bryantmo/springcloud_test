package com.bryant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users111")
public class UserPathController {

    @GetMapping("/hello")
    public String hello() {
        return "users111.hello";
    }

}

package com.bryant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/door")
public class DoorPathController {

    @GetMapping("/hello")
    public String hello() {
        return "hello web door";
    }

}

package com.bryant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/getNameByUserId")
    public String getNameByUserId() {
        return "test_user_name";
    }

}

package com.bryant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DoorGatewayRateLimitController {

    @GetMapping("/rate/limit")
    public String rateLimit() {
        return "rate limit";
    }

}

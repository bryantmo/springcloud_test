package com.bryant.controller;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    private static final ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();

    @GetMapping("/hello")
    public String hello() {
        return "hello gateway";
    }

    @GetMapping("/retry")
    public String retry(
            @RequestParam(value = "key") String key,
            @RequestParam(value = "count", defaultValue = "3") Integer count
    ) {
        AtomicInteger num = map.computeIfAbsent(key, k -> new AtomicInteger(0));
        int i = num.incrementAndGet();
        if (i < count) {
            throw new RuntimeException("retry gateway exception..");
        }
        return "retry gateway success..";
    }

}

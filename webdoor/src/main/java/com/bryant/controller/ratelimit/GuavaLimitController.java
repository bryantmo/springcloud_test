package com.bryant.controller.ratelimit;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GuavaLimitController {

    private static final Integer RATE_LIMIT = 10;
    RateLimiter rateLimiter = RateLimiter.create(RATE_LIMIT);

    @GetMapping("/try_require_rate_limit")
    public String hello() {
        boolean limit = false;
        try {
            limit = rateLimiter.tryAcquire();
        } catch (Exception e) {
            log.error("limit error", e);
        }

        if (limit) {
            return "hello guava rate limit success...";
        }
        throw new RuntimeException("hello guava rate limit fall...");
    }

    @GetMapping("/acquire_rate_limit")
    public String hello2() {
        double acquireTimeGap = rateLimiter.acquire();
        return "hello guava rate limit success, acquireTimeGap: " + acquireTimeGap;
    }


}

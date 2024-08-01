package com.bryant.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class GatewayFallbackController {

    /**
     * todo 这个降级方法没有验证成功，要检查一下 application.yml 的users模块，是否配置有问题?
     * @return
     */
    @RequestMapping("/fallbackcontroller")
    @ResponseStatus
    public Map<String, Object> fallbackcontroller() {
        return Collections.singletonMap("from", "fallbackcontroller");
    }

    @RequestMapping("/fallback")
    @ResponseStatus
    public Mono<Map<String, Object>> fallback(ServerWebExchange exchange, Throwable throwable){
        Map<String, Object> result = new HashMap<>(8);
        ServerHttpRequest request = exchange.getRequest();
        result.put("path", request.getPath().pathWithinApplication().value());
        result.put("method", request.getMethodValue());
        if (null != throwable.getCause()) {
            result.put("message", throwable.getCause().getMessage());
        } else {
            result.put("message", throwable.getMessage());
        }
        return Mono.just(result);
    }
}

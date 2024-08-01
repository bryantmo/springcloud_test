package com.bryant.config.ratelimit;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayRateLimitConfig {

    /**
     * 为了达到不同的限流效果和规则，可以通过实现 KeyResolver 接口，定义不同请求类型的限流键。
     * 这里的KeyResolverConfiguration类对应的application.yml配置文件中的对应分解器 key-resolver
     */
    @Bean
    public KeyResolver pathKeyResolver() {
        return new KeyResolver(){
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                return Mono.just(exchange.getRequest().getPath().toString());
            }
        };
    }

}

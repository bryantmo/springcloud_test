package com.bryant.config.ratelimit.filter;

import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.isomorphism.util.TokenBucket;
import org.isomorphism.util.TokenBuckets;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义令牌桶算法过滤器
 */
public class ThrottleGatewayFilterFactory implements GatewayFilter {

    private static final Log log = LogFactory.getLog(ThrottleGatewayFilterFactory.class);

    private TokenBucket tokenBucket;

    /**
     * 过滤器有四个必须的参数:令牌桶的容量、填充的令牌数、周期和周期的单位 。
     * @param capacity
     * @param refillTokens
     * @param refillPeriod
     * @param refillUnit
     */
    public ThrottleGatewayFilterFactory(int capacity, int refillTokens,
                                        int refillPeriod, TimeUnit refillUnit) {
        this.tokenBucket = TokenBuckets.builder().withCapacity(capacity)
                                .withFixedIntervalRefillStrategy(refillTokens, refillPeriod, refillUnit)
                                .build();
    }

    /**
     * 应用节流过滤器，将会构造一个令牌桶，并尝试消费该请求 。
     * - 如果消费成功，则过滤器链继续传递;
     * - 否则,直接拒绝该请求 。
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("TokenBucket capacity: " + tokenBucket.getCapacity());
        boolean consumed = tokenBucket.tryConsume();
        if (consumed) {
            // 如果消费成功，则过滤器链继续传递
            return chain.filter(exchange);
        }
        // 否则,直接拒绝该请求
        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
    }
}

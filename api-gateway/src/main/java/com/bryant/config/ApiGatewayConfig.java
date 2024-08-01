package com.bryant.config;

import com.bryant.config.ratelimit.filter.MyFilter;
import com.bryant.config.ratelimit.filter.ThrottleGatewayFilterFactory;
import java.util.concurrent.TimeUnit;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    /**
     * 路由定义
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("api-gateway",
                        r -> r.path("/gateway/**")
                            .filters(f -> f.stripPrefix(1).filter(new MyFilter()).filter(new ThrottleGatewayFilterFactory(1, 1, 5, TimeUnit.SECONDS)))
                            .uri("lb://api-gateway")
                            .order(-1))
                .build();
    }

}

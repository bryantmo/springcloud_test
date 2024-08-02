package com.bryant.config;

import com.bryant.config.ratelimit.filter.MyFilter;
import com.bryant.config.ratelimit.filter.ThrottleGatewayFilterFactory;
import java.util.concurrent.TimeUnit;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 网关路由配置类
 */
@Configuration
public class ApiGatewayRouteLocatorConfig {

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
                            .filters(f -> f.stripPrefix(1)
                                    // 此处设置了重试次数
                                    .retry(config -> config.setRetries(10).setStatuses(HttpStatus.INTERNAL_SERVER_ERROR))
                                    .filter(new MyFilter())
                                    .filter(new ThrottleGatewayFilterFactory(10, 10, 5, TimeUnit.SECONDS)))
                            .uri("lb://api-gateway")
                            .order(-1))
                .build();
    }

}

package com.bryant.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

//    @Bean
//    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("users", r -> r.path("/users/**")
//                        .filters(f -> f.stripPrefix(1).filter(new MyFilter()))
//                        .uri("lb://users"))
//                .route("api-gateway", r -> r.path("/gateway/**")
//                        .filters(f -> f.stripPrefix(1).filter(new MyFilter()))
//                        .uri("lb://api-gateway"))
//                .build();
//    }

}

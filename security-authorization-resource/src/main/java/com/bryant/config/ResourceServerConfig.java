package com.bryant.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * 通过@EnableResourceServer开启资源服务器的相关自动配置类。
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 资源服务器：配置TokenStore
        resources.tokenStore(new JwtTokenStore(accessTokenConverter()))
                .stateless(true);

        // 配置RemoteTokenService，用于向AuthorizationServer验证token令牌
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setAccessTokenConverter(accessTokenConverter());

        // 为Resttemplate配置异常处理器，忽略400错误
        RestTemplate restTemplate = restTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400) {
                    super.handleError(response);
                }
            }
        });
        tokenServices.setRestTemplate(restTemplate);
        tokenServices.setCheckTokenEndpointUrl("http://AUTHORIZATION-SERVER/oauth/check_token");
        tokenServices.setClientId("client");
        tokenServices.setClientSecret("secret");

        // 资源服务器：配置TokenServices
        resources.tokenServices(tokenServices)
                .stateless(true);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("secret");
        return jwtAccessTokenConverter;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 配置资源服务器的拦截规则
        http.
            sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                // /users/** 端点的访问必须要验证
                .antMatchers("/users/**").authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}

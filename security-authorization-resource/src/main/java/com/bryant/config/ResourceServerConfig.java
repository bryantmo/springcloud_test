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
 *
 * Oauth2的相关配置，包括如何解析令牌token的算法逻辑。
 *
 * 因为资源服务器，是客户端最后带着token过来的。
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

        // 为RestTemplate配置异常处理器，忽略400错误
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
        // 资源服务器-请求-授权服务器-校验token
        tokenServices.setCheckTokenEndpointUrl("http://AUTHORIZATION-SERVER/oauth/check_token");
        // 资源服务器-只认这个clientId
        tokenServices.setClientId("client");
        // 资源服务器-只认这个secret
        tokenServices.setClientSecret("secret");

        // 资源服务器：配置TokenServices
        resources.tokenServices(tokenServices)
                .stateless(true);
    }

    /**
     * JwtAccessTokenConverter，配置签名key=secret
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("secret");
        return jwtAccessTokenConverter;
    }

    /**
     * 负载均衡 RestTemplate
     *
     * 因为资源服务器，要请求授权服务器去鉴权，所以用得到restTemplate
     * @return
     */
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

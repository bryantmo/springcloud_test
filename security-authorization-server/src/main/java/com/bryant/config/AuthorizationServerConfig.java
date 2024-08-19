package com.bryant.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 *
 * SpringAuthorization 授权服务器配置
 *
 * @EnableAuthorizationServer 开启授权服务器的相关自动配置类。
 *
 * 通过继承 AuthorizationServerConfigurerAdapter 授权服务器配 置适配器类，覆盖我 们 需要的配置 。
 * 首先在内存中配置一个客户端(可以配置在数据库中)，它可以通过密码类型 和 授权码类型 获取到访问令牌;
 * 然后对tokenServices进行了配置，使用了JWT作为令牌的转换器，这 里将 JWT 的 签名的密钥设 置 为“ secret",
 * 同时 使用 InMemoryTokenStore 将 令牌保存到 内存中(可以保存到数据库和 Redis 中 ，官方提供了相关的默认实现 JdbcTokenStore 和 RedisTokenStore)，
 * 最后对授权端点的访问控 制进行 配 置 。
 *
 * 到这里授权服务器的基本功能就实现了。
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String RESOURCE_ID = "users";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置一个客户端
        // 既可以通过授权码获取令牌，也可以通过密码模式获取令牌
        clients.inMemory()
                // 客户端ID
                .withClient("client")
                // 支持的授权模式
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                // 允许请求范围
                .scopes("all")
                // 客户端密钥（客户端安全码）
                .secret("secret")
                // 回调地址
                .redirectUris("http://localhost:8445");

        // 上述最终对请求AuthorizationServer获取授权码code起效
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new InMemoryTokenStore())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager)
                .reuseRefreshTokens(false);
    }

    /**
     * 配置JWT转换器
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("secret");
        return converter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许所有人请求令牌
        //已验证的客户端才能请求 check_token端点
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }
}

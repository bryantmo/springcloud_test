package com.bryant.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissionConfig {

    @Bean("redissionClient")
    public RedissonClient redissionClient() {
        //1 创建redission的config对象并配置redis服务器(此处使用singleServer)
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");

        //2 创建redis客户端redissionClient
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

}

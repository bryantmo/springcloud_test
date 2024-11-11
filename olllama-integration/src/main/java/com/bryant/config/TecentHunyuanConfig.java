package com.bryant.config;

import com.bryant.service.util.HunyuanClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tencent.hunyuan")
@Data
public class TecentHunyuanConfig {

    private String secretId;
    private String secretKey;

}
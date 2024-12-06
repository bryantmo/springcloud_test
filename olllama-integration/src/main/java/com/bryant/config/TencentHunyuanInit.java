package com.bryant.config;

import com.bryant.service.util.HunyuanClient;
import com.bryant.service.util.TtsClient;
import com.tencentcloudapi.common.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TencentHunyuanInit {

    @Autowired
    private TecentHunyuanConfig config;

    @Bean
    public HunyuanClient hunyuanClient() {
        Credential credential = new Credential(config.getSecretId(), config.getSecretKey());
        return new HunyuanClient(credential, "ap-guangzhou");
    }

    @Bean
    public TtsClient ttsClient() {
        Credential credential = new Credential(config.getSecretId(), config.getSecretKey());
        return new TtsClient(credential, "ap-guangzhou");
    }
}

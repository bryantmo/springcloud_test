package com.bryant.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "ollama", name = "enable", havingValue = "true", matchIfMissing = false)
@Data
public class OllamaConfig {

    private String modelName;
    private String baseUrl;

}

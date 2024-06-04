package com.bryant.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class EurekaConfig {

    @Value("${user.name}")
    private String userName;

}

package com.bryant.config.sleuth;

import org.springframework.cloud.sleuth.instrument.web.client.feign.TraceFeignClientAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TraceFeignClientAutoConfiguration.class)
public class SleuthConfig {

}
package com.bryant.config;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestConfig {

    private static String DEFAULT_SERVICE_ID = null;

    @PostConstruct
    public void init() {
        log.info("@PostConstruct：webdoor init");
        DEFAULT_SERVICE_ID ="webdoor";
    }
}

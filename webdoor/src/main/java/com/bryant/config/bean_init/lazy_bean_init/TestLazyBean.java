package com.bryant.config.bean_init.lazy_bean_init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Slf4j
public class TestLazyBean {

    public TestLazyBean() {
        log.info("lazy initlizate bean...");
    }

    public String getName(){
        return "lazy bean";
    }
}

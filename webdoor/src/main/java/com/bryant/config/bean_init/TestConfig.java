package com.bryant.config.bean_init;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 参考：https://blog.csdn.net/u010261965/article/details/130456388
 */
@Slf4j
@Configuration
public class TestConfig implements InitializingBean {

    @Autowired
    private TestBean testBean;

    private static String DEFAULT_SERVICE_ID = null;
    private static String DEFAULT_SERVICE_NAME = null;

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public TestBean testBean() {
        return new TestBean();
    }

    @PostConstruct
    public void init() {
        log.info("@PostConstruct check autowired, testBean = {}", testBean);
        DEFAULT_SERVICE_ID ="webdoor";
        log.info("@PostConstruct：webdoor DEFAULT_SERVICE_ID = {}", DEFAULT_SERVICE_ID);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("afterPropertiesSet check autowired, testBean = {}", testBean);
        DEFAULT_SERVICE_NAME = testBean.getName();
        log.info("@afterPropertiesSet：webdoor DEFAULT_SERVICE_NAME = {}", DEFAULT_SERVICE_NAME);
    }
}

package com.bryant.config.bean_factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FactoryConfiguration implements InitializingBean {

    @Autowired
    private UserFacotryBean userFacotryBean;

    @Bean
    public UserFacotryBean userFacotryBean() {
        return new UserFacotryBean();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("FactoryConfiguration afterPropertiesSet : {}", userFacotryBean.getObject());
    }
}

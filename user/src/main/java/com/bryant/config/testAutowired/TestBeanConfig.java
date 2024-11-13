package com.bryant.config.testAutowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestBeanConfig {

    /**
     * 重复的bean定义，默认使用@Primary注解的bean
     * @return
     */
    @Bean(name = "testBean1")
    public TestString testBean1() {
        return new TestBean("testBean1");
    }

    /**
     * @Bean(name = "testBean1")，这里同名bean，会冲突，因此只加载第一个
     * @return
     */
    @Bean(name = "testBean1")
    public TestString testBean11() {
        return new TestBean("testBean11");
    }

    @Bean
    @Primary
    public TestString testBean2() {
        return new TestBean("testBean2");
    }

    @Bean(name = "testBean3")
    public TestBean testBean3() {
        return new TestBean("testBean3");
    }

    @Bean(name = "testBean4")
    public TestBean2 testBean4() {
        return new TestBean2("testBean4");
    }

}

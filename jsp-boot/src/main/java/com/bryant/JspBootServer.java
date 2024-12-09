package com.bryant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 如果在 Web 容器中部署，@SpringBootApplication 注解类应继承 SpringBootServletInitializer 并提供必要的配置选项
 */
@SpringBootApplication
public class JspBootServer extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(JspBootServer.class, args);
    }

}

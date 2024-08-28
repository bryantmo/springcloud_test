package com.bryant;

import com.bryant.import_package.EnableSpringStudy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableDiscoveryClient
@SpringBootApplication
// 测试自定义启动类加载
@EnableSpringStudy
@EnableCircuitBreaker
// mapper扫描的包路径
@MapperScan("com.bryant.mapper")
@EnableWebMvc
public class UserServer {

	public static void main(String[] args) {
	    SpringApplication.run(UserServer.class, args);
	}

}

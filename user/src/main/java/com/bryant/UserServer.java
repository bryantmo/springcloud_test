package com.bryant;

import com.bryant.import_package.EnableSpringStudy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSpringStudy // 测试自定义启动类加载
@EnableCircuitBreaker
@MapperScan("com.bryant.mapper") // mapper扫描的包路径
@EnableTransactionManagement
@EnableWebMvc
public class UserServer {

	public static void main(String[] args) {
	    SpringApplication.run(UserServer.class, args);
	}

}

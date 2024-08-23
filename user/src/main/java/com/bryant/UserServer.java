package com.bryant;

import com.bryant.import_package.EnableSpringStudy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
// 测试自定义启动类加载
@EnableSpringStudy
@EnableCircuitBreaker
// mapper扫描的包路径
@MapperScan("com.bryant.mapper")
public class UserServer {

	public static void main(String[] args) {
	    SpringApplication.run(UserServer.class, args);
	}

}

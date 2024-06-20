package com.bryant;

import com.bryant.import_package.EnableSpringStudy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
// 测试自定义启动类加载
@EnableSpringStudy
public class UserServer {

	public static void main(String[] args) {
	    SpringApplication.run(UserServer.class, args);
	}

}

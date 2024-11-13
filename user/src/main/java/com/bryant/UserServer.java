package com.bryant;

import com.bryant.import_package.EnableSpringStudy;
import com.sun.tools.attach.VirtualMachine;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserServer {

	/**
	 * JVM参数
	 * -javaagent:/Users/bryantmo/Downloads/code/springcloud_test/agent/target/agent.jar=bryantCanDoIt
	 * @param args
	 */
	public static void main(String[] args) {
	    SpringApplication.run(UserServer.class, args);
		log.info("UserServer start...");
//
//		// 加载代理
//		try {
//			// 获取当前 JVM 进程的 PID
//			String pid = Long.toString(ProcessHandle.current().pid());
//
//			// 加载代理
//			VirtualMachine vm = VirtualMachine.attach(pid);
//			vm.loadAgent("/Users/bryantmo/Downloads/code/springcloud_test/agent/target/agent.jar");
//			vm.detach();
//		} catch (Exception e) {
//
//		}
	}

}

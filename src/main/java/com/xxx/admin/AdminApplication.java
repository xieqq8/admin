package com.xxx.admin;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class AdminApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(AdminApplication.class, args);
////		SpringApplication.run(org.bumishi.admin.AdminApplication.class);
//	}
//}

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiexx on 2016/9/11.
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan(basePackages="com.xxx.admin.domain.mapper")   // MyBatisConfig.java
@EnableAdminServer
public class AdminApplication extends SpringBootServletInitializer {

	public static void main(String[] args){
		SpringApplication.run(AdminApplication.class, args);

		// 代码动态设置扫描路径  == @MapperScanner(basePackages = { "${db.mybatis.mapperScanner.basePackage}" }, sqlSessionFactoryRef = "sqlSessionFactory")
//		SpringApplication application;
//		application = new SpringApplication(AdminApplication.class);
//		Map<String, Object> defaultProperties = new HashMap<>();
//		defaultProperties.put("db.mybatis.mapperScanner.basePackage", "com.xxx.admin.domain.mapper");
//		application.setDefaultProperties(defaultProperties);
//		application.setWebEnvironment(true);
//		application.run(args);
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AdminApplication.class);
	}

//	public static void main(String[] args) throws Exception {
//		SpringApplication.run(AdminApplication.class, args);
//	}
}


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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

/**
 * Created by xiexx on 2016/9/11.
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableAdminServer
public class AdminApplication {

	public static void main(String[] args){
		SpringApplication.run(AdminApplication.class, args);
	}
}


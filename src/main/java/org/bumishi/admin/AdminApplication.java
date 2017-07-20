package org.bumishi.admin;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

/**
 * Created by xieqiang on 2016/9/11.
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableAdminServer
public class AdminApplication {

    public static void main(String[] arg){
        SpringApplication.run(AdminApplication.class);
    }
}


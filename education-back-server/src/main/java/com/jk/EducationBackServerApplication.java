package com.jk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer  //声明是Eureka的服务端
public class EducationBackServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationBackServerApplication.class, args);
    }
}

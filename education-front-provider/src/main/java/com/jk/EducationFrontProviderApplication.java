package com.jk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient  //声明是Eureka的客户端
@MapperScan("com.jk.mapper")
public class EducationFrontProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationFrontProviderApplication.class, args);
    }
}

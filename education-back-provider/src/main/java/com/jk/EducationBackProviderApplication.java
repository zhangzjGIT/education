package com.jk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient  //声明是Eureka的客户端
/*@MapperScan("com.jk.mapper.user")*/
public class EducationBackProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationBackProviderApplication.class, args);
    }
}

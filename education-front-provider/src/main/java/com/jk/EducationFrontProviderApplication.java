package com.jk;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient  //声明是Eureka的客户端
/*@MapperScan("com.jk.mapper.user")*/
public class EducationFrontProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationFrontProviderApplication.class, args);
    }

    @Value("${spring.data.solr.zk-host}")
    private String zkHost;

    @Bean
    public CloudSolrClient solrClient() {
        return new CloudSolrClient(zkHost);
    }
}


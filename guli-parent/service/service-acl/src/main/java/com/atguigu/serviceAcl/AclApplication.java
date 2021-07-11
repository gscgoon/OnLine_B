package com.atguigu.serviceAcl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.atguigu")
@MapperScan("com.atguigu.serviceAcl.mapper")
public class AclApplication {

    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }

}

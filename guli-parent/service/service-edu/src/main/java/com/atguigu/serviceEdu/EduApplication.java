package com.atguigu.serviceEdu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Super
 * @date 2020-03-29 7:43
 */
@SpringBootApplication
//扫描
@ComponentScan(basePackages = {"com.atguigu"})
//服务可以被发现，并注册到注册中心
@EnableDiscoveryClient
//调用其他服务
@EnableFeignClients
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}

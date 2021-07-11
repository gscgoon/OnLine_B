package com.atguigu.serviceApiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Super
 * @date 2020-04-13 23:18
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGateWayApplication.class,args);
    }
}

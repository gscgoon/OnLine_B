package com.atguigu.serviceUserCenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Super
 * @date 2020-04-08 23:42
 */
@SpringBootApplication
@ComponentScan({"com.atguigu"})
@MapperScan("com.atguigu.serviceUserCenter.mapper")
@EnableDiscoveryClient
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class,args);
    }
}

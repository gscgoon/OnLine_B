package com.atguigu.serviceOrder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Super
 * @date 2020-04-12 8:15
 */
@SpringBootApplication
@ComponentScan({"com.atguigu"})
@MapperScan("com.atguigu.serviceorder.mapper")
@EnableDiscoveryClient
//调用其他服务
@EnableFeignClients
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

}

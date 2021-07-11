package com.atguigu.serviceStatistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Super
 * @date 2020-04-13 7:30
 */
@SpringBootApplication
@MapperScan("com.atguigu.serviceStatistics.mapper")
@ComponentScan({"com.atguigu"})
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class DailyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyApplication.class,args);
    }
}

package com.atguigu.serviceComment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Super
 * @date 2020-04-20 22:39
 */
@SpringBootApplication
//扫描
@ComponentScan(basePackages = {"com.atguigu"})
@EnableDiscoveryClient
@EnableFeignClients
public class CommentApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CommentApplication.class,args);
    }
}

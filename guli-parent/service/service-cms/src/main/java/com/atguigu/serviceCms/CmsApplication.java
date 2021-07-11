package com.atguigu.serviceCms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Super
 * @date 2020-04-08 9:51
 */
@SpringBootApplication
@ComponentScan({"com.atguigu"})
@MapperScan("com.atguigu.serviceCms.mapper")
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}

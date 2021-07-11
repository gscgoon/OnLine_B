package com.atguigu.serviceBase.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Super
 * @date 2020-03-29 9:34
 */
//使用swagger2
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket adminApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();

    }

    private ApiInfo adminApiInfo(){

        return new ApiInfoBuilder()
                .title("网站-admin中心API文档")
                .description("本文档描述了后台中心微服务接口定义")
                .version("1.0")
                .contact(new Contact("Super", "http://atguigu.com", "2713757376@qq.com"))
                .build();
    }

    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/web/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();

    }

    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("网站-web中心API文档")
                .description("本文档描述了前台中心微服务接口定义")
                .version("1.0")
                .contact(new Contact("Super", "http://atguigu.com", "2713757376@qq.com"))
                .build();
    }
}

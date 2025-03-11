package com.healthmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * 健康管理小程序后端服务主应用类
 */
@SpringBootApplication
@MapperScan("com.healthmanager.mapper")
@EnableOpenApi
public class HmServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmServerApplication.class, args);
    }
}   
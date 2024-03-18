package com.ace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Classname: ${NAME}
 * @Date: 19/3/2024 12:58 am
 * @Author: garlam
 * @Description:
 */


@SpringBootApplication
@MapperScan("com.ace.mapper") //import tk.mybatis.spring.annotation.MapperScan;
@EnableDiscoveryClient
@RefreshScope //动态更新
public class AceProvider02Application {
    public static void main(String[] args) {
        SpringApplication.run(AceProvider02Application.class, args);
    }

}

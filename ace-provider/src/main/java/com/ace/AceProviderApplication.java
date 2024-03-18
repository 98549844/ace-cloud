package com.ace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Classname: ${NAME}
 * @Date: 18/3/2024 5:44 am
 * @Author: garlam
 * @Description:
 */

@SpringBootApplication
@MapperScan("com.ace.mapper") //import tk.mybatis.spring.annotation.MapperScan;
@EnableDiscoveryClient
public class AceProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(AceProviderApplication.class,args);
    }

}

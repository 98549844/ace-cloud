package com.ace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Classname: ${NAME}
 * @Date: 19/3/2024 2:47 pm
 * @Author: garlam
 * @Description:
 */

@SpringBootApplication
@EnableDiscoveryClient //向consul注册this application
@EnableFeignClients //启用openFeign客户端, 定义服务+绑定接口
public class AceOpenfeignClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(AceOpenfeignClientApplication.class, args);


    }
}

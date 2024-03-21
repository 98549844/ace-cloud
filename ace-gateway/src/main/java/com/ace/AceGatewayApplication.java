package com.ace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Classname: Garlam Au
 * @Date: 21/3/2024 12:13 am
 * @Author: garlam
 * @Description:
 */

@SpringBootApplication
@EnableDiscoveryClient //打开consul服务发现
public class AceGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AceGatewayApplication.class, args);
    }
}

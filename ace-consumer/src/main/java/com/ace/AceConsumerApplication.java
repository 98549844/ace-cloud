package com.ace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Classname: ${NAME}
 * @Date: 18/3/2024 8:25 am
 * @Author: garlam
 * @Description:
 */

@SpringBootApplication
@EnableDiscoveryClient
public class AceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AceConsumerApplication.class, args);
    }
}

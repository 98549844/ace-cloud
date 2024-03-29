package com.ace;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @Classname: Garlam Au
 * @Date: 21/3/2024 12:13 am
 * @Author: garlam
 * @Description:
 */

@SpringBootApplication
@ComponentScan({"com", "com.ace"})
@EnableDiscoveryClient //打开consul服务发现
@EnableJpaAuditing //bastEntity启动自动插入LastModifiedDate和CreatedDate
public class AceGatewayApplication {

    private static final Logger log = LogManager.getLogger(AceGatewayApplication.class.getName());

    public static ApplicationContext applicationContext = null;

    public static void main(String[] args) {
        SpringApplication.run(AceGatewayApplication.class, args);

    }


}

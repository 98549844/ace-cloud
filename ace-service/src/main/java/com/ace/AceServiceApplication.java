package com.ace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Classname: ${NAME}
 * @Date: 19/3/2024 12:57 am
 * @Author: garlam
 * @Description:
 */

@SpringBootApplication
@EnableJpaAuditing //for baseEntity using
@EnableJpaRepositories(basePackages = "com.yourpackage.repository")
@EnableDiscoveryClient //consul 服务发现
@RefreshScope //consul 动态更新
public class AceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AceServiceApplication.class,args);
    }

}

package com.ace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Classname: ${NAME}
 * @Date: 18/3/2024 5:44 am
 * @Author: garlam
 * @Description:
 */

@SpringBootApplication
@MapperScan("com.ace.cloud.mapper") //import tk.mybatis.spring.annotation.MapperScan;
public class AceProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(AceProviderApplication.class,args);
    }

}

package com.ace.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Classname: FeignConfig
 * @Date: 20/3/2024 3:55 am
 * @Author: garlam
 * @Description:
 */

@Configuration
public class FeignConfig {


    //设置openFeign retry 三次
    @Bean
    public Retryer aceRetryer() {
         return Retryer.NEVER_RETRY; //default不会重试
        // return new Retryer.Default(100,1,3);
        // period: 相隔时间为100ms
        //macPeriod: 最大重试隔间时间为1s
    }

    //设置feign日志输出级别, default none
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}


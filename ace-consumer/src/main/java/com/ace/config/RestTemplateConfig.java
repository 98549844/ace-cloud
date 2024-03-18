package com.ace.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * @Classname: RestTemplateConfig
 * @Date: 18/3/2024 8:41 am
 * @Author: garlam
 * @Description:
 */

@Configuration
//@LoadBalancerClient(value = "cloud-payment-service",configuration = RestTemplateConfig.class)
public class RestTemplateConfig {
    private static final Logger log = LogManager.getLogger(RestTemplateConfig.class.getName());

    @Bean
//    @LoadBalanced //使用@LoadBalanced注解赋予RestTemplate负载均衡的能力
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

//    @Bean
//    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment,
//                                                            LoadBalancerClientFactory loadBalancerClientFactory) {
//        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//
//        return new RandomLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
//    }
}


package com.ace.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Classname: CircuitBreakerApi
 * @Date: 24/3/2024 10:57 am
 * @Author: garlam
 * @Description:
 */

@FeignClient(value = "ace-gateway", path = "/ace/circuit", contextId = "circuitApi") //调用gateway提供的api, 增强安全性
public interface CircuitBreakerApi {


    // Resilience4j CircuitBreaker
    @GetMapping(value = "/breaker/{id}")
    String myCircuit(@PathVariable("id") Integer id);

    // Resilience4j bulkhead 舱壁
    @GetMapping(value = "/bulkhead/{id}")
    String myBulkhead(@PathVariable("id") Integer id) ;


    // Resilience4j myRateLimit 限流
    @GetMapping(value = "/rateLimit/{id}")
    String myRateLimit(@PathVariable("id") Integer id) ;

}

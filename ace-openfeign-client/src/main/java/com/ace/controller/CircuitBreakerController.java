package com.ace.controller;

import com.ace.apis.CircuitBreakerApi;
import com.ace.response.RespData;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


/**
 * @Classname: CircuitBreakerController
 * @Date: 24/3/2024 11:13 am
 * @Author: garlam
 * @Description:
 */

@RestController
@RequestMapping("/circuit")
@Tag(name = "断路器")
public class CircuitBreakerController {
    private static final Logger log = LogManager.getLogger(CircuitBreakerController.class.getName());
    @Resource
    private CircuitBreakerApi circuitBreakerApi;


    @GetMapping(value = "/breaker/{id}")
    public RespData<String> myCircuit(@PathVariable("id") Integer id) {
        return RespData.success(circuitBreakerApi.myCircuit(id));
    }


    // Resilience4j bulkhead 舱壁
    @GetMapping(value = "/bulkhead/{id}")
    public RespData<String> myBulkhead(@PathVariable("id") Integer id) {
        return RespData.success(circuitBreakerApi.myBulkhead(id));
    }


    // Resilience4j myRateLimit 限流
    @GetMapping(value = "/rateLimit/{id}")
    public RespData<String> myRateLimit(@PathVariable("id") Integer id) {
        return RespData.success(circuitBreakerApi.myRateLimit(id));
    }
}


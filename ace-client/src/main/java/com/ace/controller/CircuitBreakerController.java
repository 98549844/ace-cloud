package com.ace.controller;

import com.ace.apis.CircuitBreakerApi;
import com.ace.response.RespData;
import com.ace.response.ReturnCodeEnum;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    @Operation(summary = "断路器")
    @GetMapping(value = "/{id}")
    @CircuitBreaker(name = "ace-entities-module", fallbackMethod = "myCircuitFallback")
    //myCircuitFallback就是服务降级后的兜底处理方法
    public RespData<String> myCircuit(@PathVariable("id") Integer id) {
        return RespData.success(circuitBreakerApi.myCircuit(id));
    }

    public RespData<String> myCircuitFallback(Integer id, Throwable t) {
        System.out.println("id: " + id);
        System.out.println("Throwable: " + t);
        // 这里是容错处理逻辑，返回备用结果
        String message = "myCircuitFallback，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~";
        return RespData.error(ReturnCodeEnum.RC500.getCode(), message);
    }


    // Resilience4j bulkhead 舱壁隔离
    @Operation(summary = "舱壁隔离")
    @GetMapping(value = "/bulkhead/{id}")
    @Bulkhead(name = "ace-entities-module", fallbackMethod = "myBulkheadFallback", type = Bulkhead.Type.SEMAPHORE)
    public RespData<String> myBulkhead(@PathVariable("id") Integer id) {
        return RespData.success(circuitBreakerApi.myBulkhead(id));
    }

    public RespData<String> myBulkheadFallback(Throwable t) {
        System.out.println("Throwable: " + t);
        String message = "myBulkheadFallback，舱壁隔离超出最大数量限制，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~";
        return RespData.error(ReturnCodeEnum.RC500.getCode(), message);
    }

    // Resilience4j myRateLimit 限流
    @Operation(summary = "限流")
    @GetMapping(value = "/rateLimit/{id}")
    @RateLimiter(name = "ace-entities-module", fallbackMethod = "myRateLimitFallback")
    public RespData<String> myRateLimit(@PathVariable("id") Integer id) {
        return RespData.success(circuitBreakerApi.myRateLimit(id));
    }

    public RespData<String> myRateLimitFallback(Integer id, Throwable t) {
        System.out.println("id: " + id);
        System.out.println("Throwable: " + t);
        String message = "你被限流了，禁止访问/(ㄒoㄒ)/~~";
        return RespData.error(ReturnCodeEnum.RC500.getCode(), message);

    }
}


package com.ace.controller;

import cn.hutool.core.util.IdUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2023-12-26 22:26
 */
@RestController
@RequestMapping(value = "/ace/circuit")
@Tag(name = "断路器")
public class CircuitBreakerController {


    // Resilience4j CircuitBreaker
    @Operation(summary = "断路器")
    @GetMapping(value = "/breaker/{id}")
    public String myCircuit(@PathVariable("id") Integer id) {
        if (id == -4) throw new RuntimeException("circuit id 不能负数");
        if (id == 99) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Hello, circuit! inputId:  " + id + "   " + IdUtil.simpleUUID();
    }

    // Resilience4j bulkhead 舱壁
    @Operation(summary = "舱壁")
    @GetMapping(value = "/bulkhead/{id}")
    public String myBulkhead(@PathVariable("id") Integer id) {
        if (id == -4) throw new RuntimeException("bulkhead id 不能-4");

        if (id == 99) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Hello, bulkhead! inputId:  " + id + "   " + IdUtil.simpleUUID();
    }


    // Resilience4j myRateLimit 限流
    @Operation(summary = "限流")
    @GetMapping(value = "/rateLimit/{id}")
    public String myRateLimit(@PathVariable("id") Integer id) {
        return "Hello, myRateLimit 欢迎到来 inputId:  " + id + "   " + IdUtil.simpleUUID();
    }
}

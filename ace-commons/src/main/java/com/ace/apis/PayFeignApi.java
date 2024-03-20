package com.ace.apis;

import com.ace.entities.PayDto;
import com.ace.response.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Classname: PayFeignApi
 * @Date: 19/3/2024 3:10 pm
 * @Author: garlam
 * @Description:
 */

//@FeignClient(value = "ace-provider")
@FeignClient(value = "ace-gateway")
public interface PayFeignApi {

    /**
     * 新增一条支付记录
     *
     * @param payDto
     * @return
     */
    @PostMapping(value = "/pay/add")
    ResultData addPay(@RequestBody PayDto payDto);


    /**
     * 根据id查询pay info
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/get/{id}")
    ResultData getPayInfo(@PathVariable(value = "id") Integer id);


    /**
     * openfeign 天生支持负载均衡
     *
     * @return
     */
    @GetMapping(value = "/pay/get/info")
    String myLoadBalancer();


    /**
     * Resilience4j CircuitBreaker 的例子
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/circuit/{id}")
    String myCircuit(@PathVariable("id") Integer id);

    /**
     * Resilience4j Bulkhead 的例子
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/bulkhead/{id}")
    String myBulkhead(@PathVariable("id") Integer id);


    /**
     * Resilience4j Ratelimit 的例子
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/ratelimit/{id}")
    String myRatelimit(@PathVariable("id") Integer id);


    /**
     * Micrometer(Sleuth)进行链路监控的例子
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/micrometer/{id}")
    String myMicrometer(@PathVariable("id") Integer id);

    /**
     * GateWay进行网关测试案例01
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/gateway/get/{id}")
    ResultData getById(@PathVariable("id") Integer id);

    /**
     * GateWay进行网关测试案例02
     * @return
     */
    @GetMapping(value = "/pay/gateway/info")
    ResultData<String> getGatewayInfo();
}

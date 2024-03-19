package com.ace.controller;

import cn.hutool.core.date.DateUtil;
import com.ace.apis.PayFeignApi;
import com.ace.entities.PayDto;
import com.ace.response.ResultData;
import com.ace.response.ReturnCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * @Classname: OrderController
 * @Date: 18/3/2024 8:43 am
 * @Author: garlam
 * @Description:
 */

@RestController
public class OrderController {

    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping(value = "/feign/pay/add")
    public ResultData addOrder(@RequestBody PayDto payDto) {
        System.out.println("feign call provider to add pay record");
        ResultData resultData = payFeignApi.addPay(payDto);

        return resultData;
    }

    @GetMapping(value = "/feign/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable(value = "id") Integer id) {
        System.out.println("-------支付微服务远程调用，按照id查询订单支付流水信息");
        ResultData resultData = null;
        try {
            System.out.println("调用开始-----: " + DateUtil.now());
            resultData = payFeignApi.getPayInfo(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用结束-----: " + DateUtil.now());
            ResultData.error(ReturnCodeEnum.RC500.getCode(), e.getMessage());
        }
        return resultData;
    }


    /**
     * openfeign 天生支持负载均衡
     *
     * @return
     */
    @GetMapping(value = "/feign/pay/myLoadBalancer")
    public String myLoadBalancer() {
        System.out.println("access myLoadBalancer !!!");
        return payFeignApi.myLoadBalancer();
    }

}

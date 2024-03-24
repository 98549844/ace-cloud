package com.ace.controller;

import com.ace.apis.PayFeignApi;
import com.ace.response.RespData;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther zzyy
 * @create 2023-12-29 19:00
 */
//@RestController
public class OrderGateWayController
{
   // @Resource
    private PayFeignApi payFeignApi;

    @GetMapping(value = "/feign/pay/gateway/get/{id}")
    public RespData getById(@PathVariable("id") Integer id)
    {
        return payFeignApi.getById(id);
    }

    @GetMapping(value = "/feign/pay/gateway/info")
    public RespData<String> getGatewayInfo()
    {
        return payFeignApi.getGatewayInfo();
    }
}

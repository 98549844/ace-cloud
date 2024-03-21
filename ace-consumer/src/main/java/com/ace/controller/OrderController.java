package com.ace.controller;

import com.ace.entities.PayDto;
import com.ace.response.RespData;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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
    // public static final String PaymentSrv_URL = "http://localhost:8001";//先写死，硬编码
    public static final String PaymentSrv_URL = "http://ace-provider";//服务注册中心上的微服务名称

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DiscoveryClient discoveryClient;

    // postForEntity().getBody() == getForObject()
    @GetMapping(value = "/consumer/pay/add")
    public RespData addOrder(PayDto payDto) {
        // postForObject(请求地址, 参数 , 返回值); 返回json
        // postForEntity(请求地址, 参数 , 返回值); 返回header+body, 细节更多
        return restTemplate.postForObject(PaymentSrv_URL + "/pay/add", payDto, RespData.class);
    }

    @GetMapping(value = "/consumer/pay/get/{id}")
    public RespData getPayInfo(@PathVariable("id") Integer id) {
        System.out.println("access consumer/pay/get");
        // getForObject(请求地址,返回值, 参数); 返回json
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/" + id, RespData.class, id);
    }

    @GetMapping(value = "/consumer/pay/get/info")
    private String getInfoByConsul() {
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/info", String.class);
    }


    @GetMapping("/consumer/discovery")
    public RespData<List> discovery() {

        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            System.out.println(element);
        }

        System.out.println("===================================");

        List<String> result = new ArrayList<>();
        List<ServiceInstance> instances = discoveryClient.getInstances("ace-provider");
        for (ServiceInstance element : instances) {
            System.out.println(element.getServiceId() + "\t" + element.getHost() + "\t" + element.getPort() + "\t" + element.getUri());
            result.add(element.getServiceId() + ":" + element.getPort());
        }

        return RespData.success(result);
    }

}


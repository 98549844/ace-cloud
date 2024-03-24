//package com.ace.controller;
//
//import cn.hutool.core.date.DateUtil;
//import com.ace.apis.PayFeignApi;
//import com.ace.entities.PayDto;
//import com.ace.response.RespData;
//import com.ace.response.ReturnCodeEnum;
//import jakarta.annotation.Resource;
//import org.springframework.web.bind.annotation.*;
//
//
///**
// * @Classname: OrderController
// * @Date: 18/3/2024 8:43 am
// * @Author: garlam
// * @Description:
// */
//
////@RestController
//public class OrderController {
//
//   // @Resource
//    private PayFeignApi payFeignApi;
//
//    @PostMapping(value = "/feign/pay/add")
//    public RespData addOrder(@RequestBody PayDto payDto) {
//        System.out.println("feign call provider to add pay record");
//        RespData respData = payFeignApi.addPay(payDto);
//
//        return respData;
//    }
//
//    @GetMapping(value = "/feign/pay/get/{id}")
//    public RespData getPayInfo(@PathVariable(value = "id") Integer id) {
//        System.out.println("-------支付微服务远程调用，按照id查询订单支付流水信息");
//        RespData respData = null;
//        try {
//            System.out.println("调用开始-----: " + DateUtil.now());
//            respData = payFeignApi.getPayInfo(id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("调用结束-----: " + DateUtil.now());
//            RespData.error(ReturnCodeEnum.RC500.getCode(), e.getMessage());
//        }
//        return respData;
//    }
//
//
//    /**
//     * openfeign 天生支持负载均衡
//     *
//     * @return
//     */
//    @GetMapping(value = "/feign/pay/myLoadBalancer")
//    public String myLoadBalancer() {
//        System.out.println("access myLoadBalancer !!!");
//        return payFeignApi.myLoadBalancer();
//    }
//
//}

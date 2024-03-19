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

@FeignClient(value = "ace-provider")
public interface PayFeignApi {

    /** 新增一条支付记录
     * @param payDto
     * @return
     */
    @PostMapping(value = "/pay/add")
    ResultData addPay(@RequestBody PayDto payDto);


    /** 根据id查询pay info
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/get/{id}")
    ResultData getPayInfo(@PathVariable(value = "id") Integer id);


    /** openfeign 天生支持负载均衡
     * @return
     */
    @GetMapping(value = "/pay/get/info")
    String myLoadBalancer();

}

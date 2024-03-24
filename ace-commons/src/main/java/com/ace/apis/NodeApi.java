package com.ace.apis;

import com.ace.response.RespData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @Classname: NodeApi
 * @Date: 24/3/2024 1:09 pm
 * @Author: garlam
 * @Description:
 */


@FeignClient(value = "ace-gateway", path = "/ace", contextId = "nodeApi") //调用gateway提供的api, 增强安全性
public interface NodeApi {


    @GetMapping(value = "/gateway/get")
    String getInfo();

    @GetMapping(value = "/node/get")
    RespData getNodeInfo();

}


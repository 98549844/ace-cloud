package com.ace.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Classname: MicrometerApi
 * @Date: 24/3/2024 1:18 pm
 * @Author: garlam
 * @Description:
 */

@FeignClient(value = "ace-gateway", path = "/ace/micrometer", contextId = "micrometerApi") //调用gateway提供的api, 增强安全性
public interface MicrometerApi {

    @GetMapping(value = "/get")
    String myMicrometer();

}

package com.ace.controller;

import com.ace.apis.NodeApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Classname: GatewayController
 * @Date: 24/3/2024 1:11 pm
 * @Author: garlam
 * @Description:
 */

@RestController
@Tag(name="网关")
public class GatewayController {
    private static final Logger log = LogManager.getLogger(GatewayController.class.getName());

    @Resource
    private NodeApi nodeApi;


    @Operation(summary = "网关资料")
    @GetMapping(value = "/get")
    public String get() {
        return nodeApi.getInfo();
    }
}


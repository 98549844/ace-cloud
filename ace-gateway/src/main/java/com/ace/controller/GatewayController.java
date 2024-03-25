package com.ace.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Classname: GatewayController
 * @Date: 24/3/2024 11:30 am
 * @Author: garlam
 * @Description:
 */


@RestController
@RequestMapping("/ace/gateway")
public class GatewayController {
    private static final Logger log = LogManager.getLogger(GatewayController.class.getName());

    @Value("${spring.application.name}")
    private String gatewayName;

    @Value("${server.port}")
    private String port;


    @GetMapping(value = "/get")
    public String get() {
        String info = "Gateway Name:   " + gatewayName + "   port:   " + port;
        return info;
    }


}


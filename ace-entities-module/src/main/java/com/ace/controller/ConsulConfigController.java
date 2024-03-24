package com.ace.controller;

import com.ace.entities.Users;
import com.ace.entities.UsersDto;
import com.ace.response.RespData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Classname: ConsulConfigController
 * @Date: 25/3/2024 12:40 am
 * @Author: garlam
 * @Description:
 */

@RestController
@RequestMapping("/ace/config")
@Tag(name = "Consul配置")
public class ConsulConfigController {
    private static final Logger log = LogManager.getLogger(ConsulConfigController.class.getName());



    @Operation(summary = "读取consul cluster配置")
    @GetMapping(value = "/get/")
    public RespData<String> getByUserAccount(@Value("${aceInfo.version}") String version) {
        System.out.println("Ace config:" +version);
        return RespData.success("Ace config:" +version);
    }

}


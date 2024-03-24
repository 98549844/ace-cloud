package com.ace.controller;

import com.ace.apis.MicrometerApi;
import com.ace.apis.PayFeignApi;
import com.sun.source.doctree.SummaryTree;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Classname: MicrometerController
 * @Date: 24/3/2024 1:21 pm
 * @Author: garlam
 * @Description:
 */

@RestController
@RequestMapping("/micrometer")
@Tag(name = "链路追踪")
public class MicrometerController {
    private static final Logger log = LogManager.getLogger(MicrometerController.class.getName());


    @Resource
    private MicrometerApi micrometerApi;


    @Operation(summary = "链路追踪资料")
    @GetMapping(value = "/get")
    public String myMicrometer() {
        return micrometerApi.myMicrometer();
    }
}


package com.ace.controller;

import cn.hutool.core.util.IdUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther zzyy
 * @create 2023-12-28 16:08
 */
@RestController
@RequestMapping("/ace/micrometer")
@Tag(name = "链路追踪")
public class MicrometerController
{
    /**
     * Micrometer进行链路监控的例子
     * @return
     */
    @GetMapping(value = "/get")
    @Operation(summary = "链路追踪资料")
    public String myMicrometer()
    {
        return "Hello, myMicrometer http://-zipkin-localhost:9411/api/v2/spans: " + IdUtil.simpleUUID();
    }
}

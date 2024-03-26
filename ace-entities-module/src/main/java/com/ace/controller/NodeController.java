package com.ace.controller;

import com.ace.response.RespData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


/**
 * @Classname: NodeController
 * @Date: 24/3/2024 2:27 pm
 * @Author: garlam
 * @Description:
 */

@RestController
@RequestMapping("/ace/node")
@Tag(name = "节点")
public class NodeController {
    private static final Logger log = LogManager.getLogger(NodeController.class.getName());


    @Value("${spring.application.name}")
    private String nodeName;

    @Value("${server.port}")
    private String port;

    @Value("${spring.cloud.client.hostname}")
    private String cloudHostname;





    @Operation(summary = "节点资料")
    @GetMapping("/get")
    public RespData<String> getNodeInfo() throws UnknownHostException {
        String node = "Node instance: " + nodeName;
        String hostname = "hostname: " + cloudHostname;
        String nodePort = "port: " + port;
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ipAddress = inetAddress.getHostAddress();
        String ip = "ip: " + ipAddress;
        String space = "  ";
        long timestamp = System.currentTimeMillis();

        String message = node + space + hostname + space + nodePort + space + ip + space + timestamp;
        System.out.println(message);

        return RespData.success(message);
    }

}


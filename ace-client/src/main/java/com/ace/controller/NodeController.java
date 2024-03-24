package com.ace.controller;

import com.ace.apis.NodeApi;
import com.ace.response.RespData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * @Classname: NodeController
 * @Date: 24/3/2024 2:36 pm
 * @Author: garlam
 * @Description:
 */

@RestController
@RequestMapping("/node")
@Tag(name= "节点")
public class NodeController {
    private static final Logger log = LogManager.getLogger(NodeController.class.getName());

    @Resource
    private NodeApi nodeApi;

    @Operation(summary = "节点资料")
    @GetMapping("/get/{nodes}")
    public RespData<List<String>> getInfo(@PathVariable("nodes") Integer nodes){
        List<String> ls = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            ls.add(nodeApi.getNodeInfo().getData().toString());
        }
        return RespData.success(ls);

    }

}


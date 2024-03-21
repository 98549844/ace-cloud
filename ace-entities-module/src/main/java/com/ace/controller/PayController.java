package com.ace.controller;

import com.ace.response.RespData;
import com.ace.response.ReturnCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @Classname: PayController
 * @Date: 18/3/2024 6:22 am
 * @Author: garlam
 * @Description:
 */

@RestController
@Tag(name = "支付微服务模块", description = "支付CRUD")
public class PayController {
    private static final Logger log = LogManager.getLogger(PayController.class.getName());

   // @Resource
//    private PayService payService;


    @PostMapping(value = "/pay/add")
    @Operation(summary = "新增", description = "新增支付流水方法,json串做参数")
    public RespData<String> addPay() {
        System.out.println("");
        int i = 1;
        return RespData.success("成功插入记录，返回值: " + i);
    }

    @DeleteMapping(value = "/pay/del/{id}")
    @Operation(summary = "删除", description = "删除支付流水方法")
    public RespData<Integer> deletePay(@PathVariable("id") Integer id) {
        int i = 1;
        return RespData.success(i);
    }

    @PutMapping(value = "/pay/update")
    @Operation(summary = "修改", description = "修改支付流水方法")
    public RespData<String> updatePay() {

        int i = 1;
        return RespData.success("成功修改记录，返回值: " + i);
    }

    @GetMapping(value = "/pay/get/{id}")
    @Operation(summary = "按照ID查流水", description = "查询支付流水方法")
    public RespData<String> getById(@PathVariable("id") Integer id) {
        System.out.println("查流水: " + id+"   port:" +port);
        if (id == -4) throw new RuntimeException("id不能为负数");

        //暂停62秒钟线程,故意写bug，测试出feign的默认调用超时时间
        try {
            TimeUnit.SECONDS.sleep(62); //以秒作单位
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return RespData.success("");
    }

    //全部查询getall作为家庭作业

    @GetMapping(value = "/pay/error")
    public RespData<Integer> getPayError() {
        Integer integer = Integer.valueOf(200);
        try {
            System.out.println("come in pay error test");
            int age = 10 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.error(ReturnCodeEnum.RC500.getCode(), e.getMessage());
        }
        return RespData.success(integer);
    }

    @Value("${server.port}")
    private String port;

    //从sonsul拿资料
    @GetMapping(value = "/pay/get/info")
    public RespData<List> getInfoByConsul(@Value("${ace-cloud.info}") String info) {
        String re = "ace-cloud.info: " + info + "    port: " + port;
        System.out.println(re);
        List<String> result = new ArrayList<>();
        result.add("ace-cloud.info: " + info);
        result.add("port: " + port);
        return RespData.success(result);
    }


}


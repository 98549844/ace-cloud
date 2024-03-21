package com.ace.controller;

import com.ace.apis.PayFeignApi;
import com.ace.apis.UsersApi;
import com.ace.entities.UsersDto;
import com.ace.response.RespData;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Classname: UsersController
 * @Date: 21/3/2024 10:49 pm
 * @Author: garlam
 * @Description:
 */

@RestController
public class UsersController {
    private static final Logger log = LogManager.getLogger(UsersController.class.getName());


    @Resource
    private UsersApi usersApi;


    /** 返回所有用户
     * @return
     */
    @GetMapping(value = "/getAll")
    public RespData getAll(){
        RespData respData = usersApi.getAll();

        return respData;
    }

    /** 根据userAccount获取用户资料
     * @param userAccount
     * @return
     */
    @GetMapping(value = "/get/{userAccount}")
    public RespData getByUserAccount(@PathVariable(value = "userAccount") String userAccount){
        RespData respData = usersApi.getAll();

        return respData;
    }


    /** 更新用户料资
     * @param userDto
     * @return
     */
    @PostMapping(value = "/update")
    public RespData updateUser(@RequestBody UsersDto userDto){

        RespData respData = usersApi.getAll();

        return respData;
    }

    /**
     * @param userDto
     * @return
     */
    @PostMapping(value = "/new")
    public RespData insertUser(@RequestBody UsersDto userDto) {
        RespData respData = usersApi.getAll();

        return respData;

    }


    /** 删除用户资资料
     * @param userAccount
     * @return
     */
    @GetMapping(value = "/delete/{userAccount}")
    public RespData deleteByUserAccount(@PathVariable(value = "userAccount") String userAccount){

        RespData respData = usersApi.getAll();

        return respData;
    }

    /** 删除所有用户资料
     * @return
     */
    @GetMapping(value = "/delete/all")
    public RespData deleteAll() {
        RespData respData = usersApi.getAll();

        return respData;
    }

    /** 生成默认用户
     * @return
     */
    @GetMapping(value = "/userGenerator")
    public RespData userGenerator() {
        RespData respData = usersApi.getAll();

        return respData;
    }


}


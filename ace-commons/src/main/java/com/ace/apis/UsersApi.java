package com.ace.apis;

import com.ace.entities.UsersDto;
import com.ace.response.RespData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Classname: UsersApi
 * @Date: 21/3/2024 9:59 pm
 * @Author: garlam
 * @Description:
 */

//@FeignClient(value = "ace-entities-module") 直接调用模组api
@FeignClient(value = "ace-gateway", path = "/ace/users", contextId = "usersApi") //调用gateway提供的api, 增强安全性
public interface UsersApi {


    /**
     * 返回所有用户
     *
     * @return
     */
    @GetMapping(value = "/getAll")
    RespData getAll();

    /**
     * 根据userAccount获取用户资料
     *
     * @param userAccount
     * @return
     */
    @GetMapping(value = "/get/{userAccount}")
    RespData getByUserAccount(@PathVariable(value = "userAccount") String userAccount);


    /**
     * 更新用户料资
     *
     * @param userDto
     * @return
     */
    @PostMapping(value = "/update")
    RespData updateUser(@RequestBody UsersDto userDto);

    /**
     * @param userDto
     * @return
     */
    @PostMapping(value = "/new")
    RespData insertUser(@RequestBody UsersDto userDto);


    /**
     * 删除用户资资料
     *
     * @param userAccount
     * @return
     */
    @GetMapping(value = "/delete/{userAccount}")
    RespData deleteByUserAccount(@PathVariable(value = "userAccount") String userAccount);

    /**
     * 删除所有用户资料
     *
     * @return
     */
    @GetMapping(value = "/delete/all")
    RespData deleteAll();

    /**
     * 生成默认用户
     *
     * @return
     */
    @GetMapping(value = "/userGenerator")
    RespData userGenerator();

}


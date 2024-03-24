package com.ace.controller;

import com.ace.apis.UsersApi;
import com.ace.entities.UsersDto;
import com.ace.response.RespData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;


/**
 * @Classname: UsersController
 * @Date: 21/3/2024 10:49 pm
 * @Author: garlam
 * @Description:
 */

@Tag(name = "用户")
@RestController
@RequestMapping("/ace/users")
public class UsersController {
    private static final Logger log = LogManager.getLogger(UsersController.class.getName());


    @Resource
    private UsersApi usersApi;


    /** 返回所有用户
     * @return
     */
    @Operation(summary = "获取所有用户")
    @GetMapping(value = "/getAll")
    public RespData getAll(){
        RespData respData = usersApi.getAll();

        return respData;
    }

    /** 根据userAccount获取用户资料
     * @param userAccount
     * @return
     */
    @Operation(summary = "根据用户帐号获取资料")
    @GetMapping(value = "/get/{userAccount}")
    public RespData getByUserAccount(@PathVariable(value = "userAccount") String userAccount){
        RespData respData = usersApi.getByUserAccount(userAccount);

        return respData;
    }


    /** 更新用户料资
     * @param userDto
     * @return
     */
    @Operation(summary = "更新用户资料")
    @PostMapping(value = "/update")
    public RespData updateUser(@RequestBody UsersDto userDto){

        RespData respData = usersApi.updateUser(userDto);

        return respData;
    }

    /**
     * @param userDto
     * @return
     */
    @Operation(summary = "新增用户")
    @PostMapping(value = "/new")
    public RespData insertUser(@RequestBody UsersDto userDto) {
        RespData respData = usersApi.insertUser(userDto);

        return respData;

    }


    /** 删除用户资资料
     * @param userAccount
     * @return
     */
    @Operation(summary = "删除用户")
    @GetMapping(value = "/delete/{userAccount}")
    public RespData deleteByUserAccount(@PathVariable(value = "userAccount") String userAccount){

        RespData respData = usersApi.deleteByUserAccount(userAccount);

        return respData;
    }

    /** 删除所有用户资料
     * @return
     */
    @Operation(summary = "删除所有用户")
    @GetMapping(value = "/delete/all")
    public RespData deleteAll() {
        RespData respData = usersApi.deleteAll();

        return respData;
    }

    /** 生成默认用户
     * @return
     */
    @Operation(summary = "生成默认用户")
    @GetMapping(value = "/userGenerator")
    public RespData userGenerator() {
        RespData respData = usersApi.userGenerator();

        return respData;
    }


}


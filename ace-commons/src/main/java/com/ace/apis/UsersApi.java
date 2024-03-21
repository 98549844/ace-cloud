package com.ace.apis;

import com.ace.entities.UsersDto;
import com.ace.response.RespData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * @Classname: UsersApi
 * @Date: 21/3/2024 9:59 pm
 * @Author: garlam
 * @Description:
 */

@FeignClient(value = "ace-gateway")
public interface UsersApi {


    /** 返回所有用户
     * @return
     */
    @GetMapping(value = "/getAll")
    public RespData<List<UsersDto>> getAll();

    @GetMapping(value = "/get/{userAccount}")
    public RespData<UsersDto> getByUserAccount(@PathVariable(value = "userAccount") String userAccount);


    @PostMapping(value = "/update")
    public RespData<String> updateUser(@RequestBody UsersDto info);

    @PostMapping(value = "/new")
    public RespData<String> insertUser(@RequestBody UsersDto info) ;


    @GetMapping(value = "/delete/{userAccount}")
    public RespData<String> deleteByUserAccount(@PathVariable(value = "userAccount") String userAccount);

    @GetMapping(value = "/delete/all")
    public RespData<String> deleteAll() ;

    @GetMapping(value = "/userGenerator")
    public RespData<List<String>> userGenerator() ;

}


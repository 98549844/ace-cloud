package com.ace.controller;

import com.ace.entities.Users;
import com.ace.generator.InsertUsers;
import com.ace.response.RespData;
import com.ace.service.UsersService;
import com.ace.utilities.NullUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname: UserRestController
 * @Date: 5/7/2021 10:49 上午
 * @Author: garlam
 * @Description:
 */

@RestController
@RequestMapping("/ace/users")
@Tag(name = "用户")
public class UsersController {
    private static final Logger log = LogManager.getLogger(UsersController.class.getName());

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersController(UsersService usersService, PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
    }


    @Operation(summary = "获取所有用户")
    @GetMapping(value = "/getAll")
    public RespData<List<Users>> getAll() {
        return RespData.success(usersService.getAll());
    }

    @Operation(summary = "根据用户帐号获取")
    @GetMapping(value = "/get/{userAccount}")
    public RespData<Users> getByUserAccount(@PathVariable(value = "userAccount") String userAccount) {
        Users user = usersService.getByUserAccount(userAccount);
        return RespData.success(user);
    }


    @Operation(summary = "更新用户资料")
    @PostMapping(value = "/update")
    public RespData<String> updateUser(@RequestBody Users user) {
        Users check = usersService.getByUserAccount(user.getUserAccount());
        if (NullUtil.isNull(check)) {
            return RespData.success("用户不存在");
        } else {
            usersService.save(user);
            return RespData.success("更新用户成功");
        }
    }

    @Operation(summary = "新增用户")
    @PostMapping(value = "/new")
    public RespData<String> insertUser(@RequestBody Users user) {
        Users check = usersService.getByUserAccount(user.getUserAccount());
        if (NullUtil.isNull(check)) {
            usersService.save(user);
            return RespData.success("用户已新增");
        } else {
            return RespData.success("新增失败, 用户已存在");
        }
    }


    @Operation(summary = "删除用户")
    @GetMapping(value = "/delete/{userAccount}")
    public RespData<String> deleteByUserAccount(@PathVariable(value = "userAccount") String userAccount) {
        usersService.deleteByUserAccount(userAccount);
        StringBuilder res = new StringBuilder();
        res.append("用户").append(" " + userAccount + " ").append("已删除");
        return RespData.success(res.toString());
    }

    @Operation(summary = "删除所有用户")
    @GetMapping(value = "/delete/all")
    public RespData<String> deleteAll() {
        usersService.deleteAll();
        return RespData.success("所有用户已删除");
    }

    @Operation(summary = "生成默认用户")
    @GetMapping(value = "/userGenerator")
    public RespData<List<String>> userGenerator() {

        String password = passwordEncoder.encode("909394");
        log.info("passwordEncoder matches=>" + passwordEncoder.matches("909394", password));
        List<Users> usersList = InsertUsers.insertUsers();
        usersService.saveAll(usersList);

        List<String> result = new ArrayList<>();
        for (Users user : usersList) {
            String u = user.getUsername() + "   [ 909394 ]";
            result.add(u);
        }
        return RespData.success(result);
    }


}


package com.ace.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Classname: UsersController
 * @Date: 21/3/2024 9:58 pm
 * @Author: garlam
 * @Description:
 */

@RestController
@RequestMapping(value = "/clientSite/")
public class UsersController {
    private static final Logger log = LogManager.getLogger(UsersController.class.getName());


}


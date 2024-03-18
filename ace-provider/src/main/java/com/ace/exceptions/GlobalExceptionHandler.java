package com.ace.exceptions;

import com.ace.response.ResultData;
import com.ace.response.ReturnCodeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Classname: GlobalExceptionHandler
 * @Date: 18/3/2024 8:15 am
 * @Author: garlam
 * @Description:
 */


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<String> exception(Exception e)
    {
        System.out.println("##### access GlobalExceptionHandler");
        log.error("全局异常信息:{}",e.getMessage(),e);

        return ResultData.error(ReturnCodeEnum.RC500.getCode(), e.getMessage());
    }
}


package com.ace.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @auther zzyy
 * @create 2023-12-22 16:18
 */
@Data
@Accessors(chain = true)
public class RespData<T>
{

    private String code;/** 结果状态 ,具体状态码参见枚举类ReturnCodeEnum.java*/
    private String message;
    private T data;
    private long timestamp;

    public RespData()
    {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> RespData<T> success(T data)
    {
        RespData respData = new RespData();

        respData.setCode(ReturnCodeEnum.RC200.getCode());
        respData.setMessage(ReturnCodeEnum.RC200.getMessage());
        respData.setData(data);

        return respData;
    }

    public static <T> RespData<T> error(String code, String message)
    {
        RespData respData = new RespData();

        respData.setCode(code);
        respData.setMessage(message);
        respData.setData(null);

        return respData;
    }
}

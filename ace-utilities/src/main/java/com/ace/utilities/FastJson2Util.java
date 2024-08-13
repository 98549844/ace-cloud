package com.ace.utilities;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @Classname: FastJsonUtil
 * @Date: 2023/3/22 下午 06:38
 * @Author: kalam_au
 * @Description:
 */


public class FastJson2Util {
    private static final Logger log = LogManager.getLogger(FastJson2Util.class.getName());

    //https://blog.csdn.net/qq_33697094/article/details/128114939

    /**
     * 格式化json
     *
     * @param json
     * @return
     */
    public static String formatFastJson2(String json) {
        return JSON.toJSONString(json, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNulls);
    }

    /**
     * 格式化json
     *
     * @param json
     * @return
     */
    public static String formatJson(String json) {
        //在序列化的时候不忽略null值
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        return gson.toJson(JsonParser.parseString(json));
    }


    /**
     * object to json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        String json = JSON.toJSONString(object);
        return json.replace("\\", "");
    }

    /**
     * object to 二進製字節流
     *
     * @param object
     * @return
     */
    public static byte[] toByteArray(Object object) {
        return toJson(object).getBytes();
    }

    /**
     * 二進製字節流 to object
     *
     * @param bytes
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T toObject(byte[] bytes, Class<T> type) {
        return JSON.parseObject(bytes, type);
    }

    /**
     * json to object
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T toObject(String json, Class<T> type) {
        return JSONObject.parseObject(json, type);
    }


    /**
     * json to object
     *
     * @param json
     * @return
     */
    public static JSONObject toObject(String json) {
        return JSONObject.parseObject(json);
    }


    /**
     * 把json 字符串转换成 封装后的对象List<T>
     *
     * @param json
     * @param type
     * @return
     */
    public static <T> List toObjectList(String json, Class<T> type) {
        JSONArray jsonArray = JSON.parseArray(json);
        return jsonArray.toJavaList(type);
    }


}


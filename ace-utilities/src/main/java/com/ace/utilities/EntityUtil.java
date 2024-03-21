package com.ace.utilities;

import com.alibaba.fastjson2.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 */


public class EntityUtil  {
    private static final Logger log = LogManager.getLogger(EntityUtil.class.getName());

    /** json to entity
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> clazz) {
        return JSON.parseObject(JSON.toJSONString(map), clazz);
    }

    /** list of json to entity
     * @param list
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> listMapToEntity(List<Map> list, Class<T> clazz) {
        List<T> tList = new ArrayList<>();
        for (Map stringObjectMap : list) {
            T t = JSON.parseObject(JSON.toJSONString(stringObjectMap), clazz);
            tList.add(t);
        }
        return tList;
    }
}


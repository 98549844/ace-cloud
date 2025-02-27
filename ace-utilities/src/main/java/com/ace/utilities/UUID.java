package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Classname: Uuid
 * @Date: 2022/10/5 下午 02:59
 * @Author: kalam_au
 * @Description:
 */


public class UUID {
    private static final Logger log = LogManager.getLogger(UUID.class.getName());

    public static String randomUUID() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String get() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getOrigin() {
        return java.util.UUID.randomUUID().toString();
    }

    public static String get(String style) {
        String uuid = UUID.get();
        return uuid.replace("-", style == null ? "" : style);
    }

    public static String get(int length) {
        if(length > 16){
            throw new IllegalArgumentException("length must be less than or equal to 16");
        }
        String uuid = UUID.get();
        return uuid.substring(1, length);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            String uuid = UUID.get(16);
            System.out.println(uuid);
        }
    }
}


package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @Classname: ThreadUtil
 * @Date: 10/12/24 PM5:59
 * @Author: garlam
 * @Description:
 */


public class ThreadUtil {
    private static final Logger log = LogManager.getLogger(ThreadUtil.class.getName());


    public static void printStackTrace() {
        StackTraceElement[] a = Thread.currentThread().getStackTrace();
        for (int i = 0; i < a.length; i++) {
            System.out.println(i + ": " + a[i]);
        }
    }

}


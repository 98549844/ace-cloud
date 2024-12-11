package com.ace.utilities;

import com.ace.utilities.datetime.LocalDateTimeUtil;
import com.itextpdf.commons.utils.DateTimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

import static com.itextpdf.commons.utils.DateTimeUtil.getCurrentTimeDate;


/**
 * @Classname: ThreadUtil
 * @Date: 10/12/24 PM5:59
 * @Author: garlam
 * @Description:
 */


public class ThreadUtil {
    private static final Logger log = LogManager.getLogger(ThreadUtil.class.getName());


    public static void printStackTrace() {
        Console.println("[ ====== start to stack trace ====== ]");
        StackTraceElement[] a = Thread.currentThread().getStackTrace();
        for (int i = 0; i < a.length; i++) {
            System.out.println(LocalDateTimeUtil.getCurrentDatetime() + "  " + i + ": " + a[i]);
        }
        Console.println("[ ====== end to stack trace ====== ]");
    }
}


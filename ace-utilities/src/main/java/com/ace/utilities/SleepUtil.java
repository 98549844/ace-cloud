package com.ace.utilities;

import com.ace.utilities.datetime.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
public class SleepUtil {
    static private final Logger log = LogManager.getLogger(SleepUtil.class);

    public static void sleep(Integer second) {
        //休眠一秒钟
        try {
            Long t = second * 1000l;
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ParseException, InterruptedException {
        while (true) {
            DateUtil.printCurrentDate();
            SleepUtil.sleep(1);
        }
    }
}

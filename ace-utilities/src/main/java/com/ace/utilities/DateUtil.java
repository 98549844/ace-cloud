package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;


/**
 * @Classname: DateUtil
 * @Date: 17/9/24 PM11:26
 * @Author: garlam
 * @Description:
 */


public class DateUtil {
    private static final Logger log = LogManager.getLogger(DateUtil.class.getName());

    public static void main(String[] args) {
        System.out.println(getCurrentDate());
        System.out.println(getFirstDayOfCurrentMonth());
        System.out.println(getLastDayOfCurrentMonth());
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public static LocalDate getFirstDayOfCurrentMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()); // 获取当前月的第一天
    }

    public static LocalDate getLastDayOfCurrentMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()); // 获取当前月的最后一天
    }

}


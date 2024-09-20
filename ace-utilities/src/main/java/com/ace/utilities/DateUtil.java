package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

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

    /**
     * 这一刻的localDate
     *
     * @return
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * 当前月的第一天
     *
     * @return
     */
    public static LocalDate getFirstDayOfCurrentMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()); // 获取当前月的第一天
    }

    /**
     * 当前月的最后一天
     *
     * @return
     */
    public static LocalDate getLastDayOfCurrentMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()); // 获取当前月的最后一天
    }

    /**
     * 计算localDate是第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(LocalDate date) {
        // 根据本地化设置（Locale）获取当前周的结构定义
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        // 使用 weekOfYear() 方法获取日期在当前年份中的第几周
        return date.get(weekFields.weekOfYear());
    }

    public static LocalDate getMonday(LocalDate date) {
        // 使用 with() 和 DayOfWeek.MONDAY 将日期调整为当前周的星期一
        return date.with(DayOfWeek.MONDAY);
    }

    public static LocalDate getTuesday(LocalDate date) {
        // 使用 with() 和 DayOfWeek.MONDAY 将日期调整为当前周的星期二
        return date.with(DayOfWeek.TUESDAY);
    }

    public static LocalDate getWednesday(LocalDate date) {
        // 使用 with() 和 DayOfWeek.MONDAY 将日期调整为当前周的星期三
        return date.with(DayOfWeek.WEDNESDAY);
    }

    public static LocalDate getThursday(LocalDate date) {
        // 使用 with() 和 DayOfWeek.FRIDAY 将日期调整为当前周的星期四
        return date.with(DayOfWeek.THURSDAY);
    }

    public static LocalDate getFriday(LocalDate date) {
        // 使用 with() 和 DayOfWeek.FRIDAY 将日期调整为当前周的星期五
        return date.with(DayOfWeek.FRIDAY);
    }

    public static LocalDate getSaturday(LocalDate date) {
        // 使用 with() 和 DayOfWeek.FRIDAY 将日期调整为当前周的星期六
        return date.with(DayOfWeek.SATURDAY);
    }

    public static LocalDate getSunday(LocalDate date) {
        // 使用 with() 和 DayOfWeek.FRIDAY 将日期调整为当前周的星期日
        return date.with(DayOfWeek.SUNDAY);
    }

    /**
     * 计算任意两个localDate的共有多少个星期, 除去星期六和日
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Map<Integer, List<LocalDate>> countWeeks(LocalDate startDate, LocalDate endDate) {
        LocalDate monday = getMonday(startDate);
        final LocalDate friday = getFriday(endDate);
        Integer key = 1;
        Map<Integer, List<LocalDate>> result = new HashMap<>();
        while (getFriday(monday).isBefore(friday) || getFriday(monday).isEqual(friday)) {
            List<LocalDate> week = new ArrayList<>();
            week.add(monday);
            week.add(getFriday(monday));
            result.put(key, week);
            ++key;
            monday = monday.plusDays(7);
        }
        return result;
    }
}


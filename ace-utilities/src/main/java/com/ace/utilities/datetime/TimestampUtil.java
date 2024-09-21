package com.ace.utilities.datetime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ofPattern;


/**
 * @Classname: TimestampUtil
 * @Date: 21/9/24 PM4:54
 * @Author: garlam
 * @Description:
 */


public class TimestampUtil {
    private static final Logger log = LogManager.getLogger(TimestampUtil.class.getName());

    public static Timestamp toTimestamp(long time) {
        return new Timestamp(time);
    }

    public static String DATETIME_PATTERN_yyyyMMddHHmmssSSS = "yyyy-MM-dd HH:mm:ss SSSS";


    public static String toTimestampString(Timestamp timestamp) {
        // 使用toLocalDateTime()方法将Timestamp转换为LocalDateTime
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        // 将LocalDateTime格式化为字符串
        DateTimeFormatter formatter = ofPattern(DATETIME_PATTERN_yyyyMMddHHmmssSSS);
        String formattedDateTime = formatter.format(dateTime);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("LocalDateTime: " + formattedDateTime);
        return formattedDateTime;
    }

    public static Date toDate(Timestamp timestamp) {
        // 使用toInstant()方法将Timestamp转换为Instant
        Instant instant = timestamp.toInstant();
        // 使用Date.from()方法将Instant转换为Date
        Date date = Date.from(instant);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Date: " + date);
        return date;
    }

    public static long toLong(Timestamp timestamp) {
        return timestamp.getTime();
    }

}


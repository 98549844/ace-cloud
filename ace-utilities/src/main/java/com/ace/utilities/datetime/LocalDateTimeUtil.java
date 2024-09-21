package com.ace.utilities.datetime;

import com.ace.utilities.NullUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.format.DateTimeFormatter.ofPattern;


/**
 * @Classname: LocalDateTimeUtil
 * @Date: 21/9/24 PM4:53
 * @Author: garlam
 * @Description:
 */


public class LocalDateTimeUtil {
    private static final Logger log = LogManager.getLogger(LocalDateTimeUtil.class.getName());
    public static String DATETIME_PATTERN_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";


    /**
     * timestamp 转换成 localDateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        // 将Timestamp对象转换为LocalDateTime
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return localDateTime;

    }

    /**
     * @param start
     * @param end
     * @return nanos
     */
    public static long differenceNanos(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.nonNull(getDuration(start, end))) {
            return getDuration(start, end).toNanos();
        } else {
            return 0L;
        }
    }

    /**
     * @param start
     * @param end
     * @return millis
     */
    public static long differenceMillis(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.nonNull(getDuration(start, end))) {
            long millis = getDuration(start, end).toMillis();
            return millis;
        } else {
            return 0l;
        }
    }

    /**
     * @param start
     * @param end
     * @return seconds
     */
    public static long differenceSecond(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.nonNull(getDuration(start, end))) {
            long millis = getDuration(start, end).toSeconds();
            return millis;
        } else {
            return 0l;
        }
    }

    /**
     * @param start
     * @param end
     * @return minutes
     */
    public static long differenceMinutes(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.nonNull(getDuration(start, end))) {
            long minutes = getDuration(start, end).toMinutes();
            return minutes;
        } else {
            return 0l;
        }

    }

    /**
     * @param start
     * @param end
     * @return hours
     */
    public static long differenceHours(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.nonNull(getDuration(start, end))) {
            long hours = getDuration(start, end).toHours();
            return hours;
        } else {
            return 0l;
        }
    }

    /**
     * @param start
     * @param end
     * @return days
     */
    public static long differenceDays(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.nonNull(getDuration(start, end))) {
            long days = getDuration(start, end).toDays();
            return days;
        } else {
            return 0l;
        }
    }

    /**
     * @param start
     * @param end
     * @return Years
     */
    public static long differenceYears(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.nonNull(getDuration(start, end))) {
            long days = getDuration(start, end).toDays();
            return days / 365;
        } else {
            return 0l;
        }
    }

    private static Duration getDuration(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.isNull(start, end)) {
            throw new NullPointerException("Start or End is null");
        }
        return Duration.between(start, end);
    }

    public static LocalDateTime toLocalDateTime(String localDateTimeString) {
        DateTimeFormatter formatter = ofPattern(DATETIME_PATTERN_yyyyMMddHHmmss);
        String dateTimeRegex = "\\b\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\b";
        Pattern pattern = Pattern.compile(dateTimeRegex);
        Matcher matcher = pattern.matcher(localDateTimeString);

        if (matcher.find()) {
            LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeString, formatter);
            return localDateTime;
        } else {
            throw new DateTimeParseException("日期时间格式不匹配", localDateTimeString, 0);
        }
    }

    public static String toLocalDateTimeString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DATETIME_PATTERN_yyyyMMddHHmmss));

    }

}


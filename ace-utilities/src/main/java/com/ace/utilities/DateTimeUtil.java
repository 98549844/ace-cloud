package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


public class DateTimeUtil {
    private static final Logger log = LogManager.getLogger(DateTimeUtil.class.getName());
    public static String DATE_PATTERN = "yyyy-MM-dd";
    public static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String DATETIME2_PATTERN = "yyyy-MM-dd HH:mm:ss SSSS";

    public static void main(String[] args) {
        System.out.println(getUsedDateTime(System.currentTimeMillis()));


    }

    /** timestamp 转换成 localDateTime
     * @param timestamp
     * @return
     */
    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        // 将Timestamp对象转换为LocalDateTime
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return localDateTime;

    }

    public static Timestamp toTimestamp(long time) {
        return new Timestamp(time);
    }

    public static Date convertXMLGregorianCalendarToDate(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().getTime();
    }

    public static void printDateTime(Object time) {
        Date date = null;
        if (time instanceof Long) {
            date = new Date((Long) time);
        } else if (time instanceof Integer) {
            Long i = TypeUtil.toLong((Integer) time);
            date = new Date(i);
        } else if (time instanceof Date) {
            date = (Date) time;
        } else {
            System.out.println("input parameter not require");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
        System.out.println(formatter.format(date));
    }

    public static void printCurrentDateTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
        System.out.println(formatter.format(date));
    }

    public static String getCurrentDateTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
        String formattedDate = formatter.format(date);
        System.out.println(formattedDate);
        return formattedDate;
    }

    public static String getCurrentDateTimeAsFileName() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String formattedDate = formatter.format(date);
        System.out.println(formattedDate);
        return formattedDate;
    }

    public static String getDateTime(Timestamp timestamp) {
        // 使用toLocalDateTime()方法将Timestamp转换为LocalDateTime
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        // 将LocalDateTime格式化为字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = formatter.format(dateTime);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("DateTime: " + formattedDateTime);
        return formattedDateTime;
    }

    public static Date getDate(Timestamp timestamp) {
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

    public static void differenceSystemCurrentTimeMillis(Long start, Long end) {
        Long temp;
        if (NullUtil.nonNull(start) || NullUtil.nonNull(end)) {
            if (start > end) {
                temp = end;
                end = start;
                start = temp;
            }
            Long result = end - start;
            printDateTime(result);
        } else {
            log.error("Start time or End time is null , pLease check");
        }
    }


    /**
     * @param time
     * @return
     */
    public static String getTime(Long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss SSSS");
        return formatter.format(date);
    }

    /**
     * @param time
     * @return
     */
    public static String getDateTime(Long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
        return formatter.format(date);
    }


    /**
     * 因为时区关系增加8小时, 所以要扣去8小时
     *
     * @param time
     * @return
     */
    public static String getUsedTime(Long time) {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
        Date date = new Date(time - timeZone.getRawOffset());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss SSSS");
        return formatter.format(date);
    }


    /**
     * 因为时区关系增加8小时, 所以要扣去8小时
     *
     * @param time
     * @return
     */
    public static String getUsedDateTime(Long time) {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
        Date date = new Date(time - timeZone.getRawOffset());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
        return formatter.format(date);
    }


    //获取月份的天数
    public static int getDaysOfCurrentMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static XMLGregorianCalendar convertDateToXMLGregorianCalendar(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gc;
    }


    /**
     * 返回一定时间后的日期
     *
     * @param date   开始计时的时间
     * @param year   增加的年
     * @param month  增加的月
     * @param day    增加的日
     * @param hour   增加的小时
     * @param minute 增加的分钟
     * @param second 增加的秒
     * @return Date
     */
    public static Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        if (NullUtil.isNull(date)) {
            date = new Date();
        }

        Calendar cal = new GregorianCalendar();

        cal.setTime(date);
        if (year != 0) {
            cal.add(Calendar.YEAR, year);
        }
        if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }
        if (day != 0) {
            cal.add(Calendar.DATE, day);
        }
        if (hour != 0) {
            cal.add(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != 0) {
            cal.add(Calendar.MINUTE, minute);
        }
        if (second != 0) {
            cal.add(Calendar.SECOND, second);
        }
        return cal.getTime();
    }


    /**
     * 方法名：getStartDayOfWeekNo
     * 功能：某周的开始日期
     */
    public static String getStartDayOfWeekNo(int year, int weekNo) {
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        String month = (cal.get(Calendar.MONTH) + 1) < 10 ? "0" + (cal.get(Calendar.MONTH) + 1) : String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + cal.get(Calendar.DAY_OF_MONTH) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        return cal.get(Calendar.YEAR) + month + day;
    }

    /**
     * 方法名：getEndDayOfWeekNo
     * 功能：某周的结束日期
     */
    public static String getEndDayOfWeekNo(int year, int weekNo) {
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        String month = (cal.get(Calendar.MONTH) + 1) < 10 ? "0" + (cal.get(Calendar.MONTH) + 1) : String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + cal.get(Calendar.DAY_OF_MONTH) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        return cal.get(Calendar.YEAR) + month + day;
    }

    private static Calendar getCalendarFormYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        return cal;
    }

    /**
     * 方法名：getFirstDayOfMonth
     * 功能：获取某月的第一天
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int firstDay = cal.getMinimum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Console.println(sdf.format(cal.getTime()));
        return sdf.format(cal.getTime());
    }

    /**
     * 方法名：getLastDayOfMonth
     * 功能：获取某月的最后一天
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Console.println(sdf.format(cal.getTime()));
        return sdf.format(cal.getTime());
    }


    public static void printSimpleDate(Date date) {
        if (NullUtil.isNull(date)) {
            date = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateformat = sdf.format(date);
        log.info("时间：{}", dateformat);
    }

    public static void printCurrentDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateformat = sdf.format(date);
        log.info("时间：{}", dateformat);
    }


    /**
     * @param start
     * @param end
     * @return nanos
     */
    public static long differenceNanosByLocalDateTime(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.nonNull(getDuration(start, end))) {
            long nanos = getDuration(start, end).toNanos();
            return nanos;
        } else {
            return 0l;
        }
    }

    /**
     * @param start
     * @param end
     * @return millis
     */
    public static long differenceMillisByLocalDateTime(LocalDateTime start, LocalDateTime end) {
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
    public static long differenceSecondByLocalDateTime(LocalDateTime start, LocalDateTime end) {
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
    public static long differenceMinutesByLocalDateTime(LocalDateTime start, LocalDateTime end) {
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
    public static long differenceHoursByLocalDateTime(LocalDateTime start, LocalDateTime end) {
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
    public static long differenceDaysByLocalDateTime(LocalDateTime start, LocalDateTime end) {
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
    public static long differenceYearsByLocalDateTime(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.nonNull(getDuration(start, end))) {
            long days = getDuration(start, end).toDays();
            return days / 365;
        } else {
            return 0l;
        }
    }

    private static Duration getDuration(LocalDateTime start, LocalDateTime end) {
        if (NullUtil.isNull(start) || NullUtil.isNull(end)) {
            log.warn("LocalDateTime is null");
            return null;
        }
        Duration duration = Duration.between(start, end);
        return duration;
    }

    /**
     * 以今年为标识，获取上N年的年份List列表
     */
    public static List<String> getPreviousYearListByCurrentYear(int n) {
        List<String> years = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        for (int i = year - n; i <= year; i++) {
            years.add(String.valueOf(i));
        }
        return years;
    }

    public static LocalDateTime convertLocalDate(String dateTimeString) throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        LocalDateTime dateTime = null;
        try {
            dateTime = formatter.parse(dateTimeString + " 00:00:00", LocalDateTime::from);
        } catch (DateTimeParseException e) {
            System.out.println("Failed to parse date and time: " + e.getMessage());
        }
        return dateTime;
    }
}

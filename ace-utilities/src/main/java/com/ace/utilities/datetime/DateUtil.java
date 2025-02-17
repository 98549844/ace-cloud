package com.ace.utilities.datetime;

import com.ace.utilities.Console;
import com.ace.utilities.NullUtil;
import com.ace.utilities.TypeUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.*;


public class DateUtil {
    private static final Logger log = LogManager.getLogger(DateUtil.class.getName());
    public static String DATETIME_PATTERN_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static String DATETIME_PATTERN_yyyyMMddHHmmssSSS = "yyyy-MM-dd HH:mm:ss SSSS";
    public static String DATETIME_PATTERN_yyyyMMdd = "yyyy-MM-dd";

    public static String GMT = "GMT";
    public static String GMT_8 = "GMT+8";
    public static String CST = "CST";

    public static void main(String[] args) {
        System.out.println(getUsedDateTime(System.currentTimeMillis()));


    }


    public static Date convertXMLGregorianCalendarToDate(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().getTime();
    }

    /**
     * 打印date
     *
     * @param time
     */
    public static void printDateTime(Object time) {
        Date date;
        if (time instanceof Long) {
            date = new Date((Long) time);
        } else if (time instanceof Integer) {
            Long i = TypeUtil.toLong((Integer) time);
            date = new Date(i);
        } else if (time instanceof Date) {
            date = (Date) time;

        } else if (time instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) time;
            date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } else {
            throw new DateTimeParseException("Invalid date format", time.toString(), 0);
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_PATTERN_yyyyMMddHHmmssSSS);
        System.out.println(formatter.format(date));
    }

    public static void printCurrentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_PATTERN_yyyyMMddHHmmss);
        log.info("时间：{}", formatter.format(date));
    }

    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_PATTERN_yyyyMMddHHmmssSSS);
        String formattedDate = formatter.format(date);
        return formattedDate;
    }

    public static String getCurrentDateAsFileName() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_PATTERN_yyyyMMddHHmmssSSS);
        return formatter.format(date);
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

    
    public static long getLong(String dateTime, String pattern) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        Date date = sf.parse(dateTime);
        return date.getTime();
    }

    public static long getLong(String dateTime) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(DATETIME_PATTERN_yyyyMMddHHmmssSSS);
        Date date = sf.parse(dateTime);
        return date.getTime();
    }
    

    /**
     * @param time
     * @return
     */
    public static String getTimeString(Long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss SSSS");
        return formatter.format(date);
    }

    /**
     * @param time
     * @return
     */
    public static String getDateTimeString(Long time) {
        return getDateTimeString(time, DATETIME_PATTERN_yyyyMMddHHmmssSSS);
    }

    /**
     * @param time
     * @return
     */
    public static String getDateTimeString(Long time, String... param) {
        if (NullUtil.nonNull((Object) param) && param.length > 2) {
            throw new IllegalArgumentException("Illegal param, more than 2 param");
        }

        String result;
        if (param.length == 1) {
            if (param[0].contains("yyyy") || param[0].contains("MM") || param[0].contains("dd")) {
                result = getDateTimeString(time, param[0], null);
            } else {
                result = getDateTimeString(time, null, param[0]);
            }
        } else {
            if (param[0].contains("yyyy") || param[0].contains("MM") || param[0].contains("dd")) {
                result = getDateTimeString(time, param[0], param[1]);
            } else {
                result = getDateTimeString(time, param[1], param[0]);
            }
        }
        return result;
    }

    /**
     * @param time
     * @return
     */
    private static String getDateTimeString(Long time, String pattern, String timezone) {
        if (NullUtil.isNull(pattern)) {
            pattern = DATETIME_PATTERN_yyyyMMddHHmmssSSS;
        }
        if (NullUtil.isNull(timezone)) {
            timezone = GMT_8;
        }

        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setTimeZone(TimeZone.getTimeZone(timezone));
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
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_PATTERN_yyyyMMddHHmmssSSS);
        return formatter.format(date);
    }

    //获取当月月份的天数
    public static int getDaysOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    //获取月份的天数
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static XMLGregorianCalendar oXMLGregorianCalendar(Date date) {
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
     * 加一月
     */
    public static Date addMonths(final Date date, int amount) {
        return DateUtils.addMonths(date, amount);
    }

    /**
     * 减一月
     */
    public static Date subMonths(final Date date, int amount) {
        return DateUtils.addMonths(date, -amount);
    }

    /**
     * 加一周
     */
    public static Date addWeeks(final Date date, int amount) {
        return DateUtils.addWeeks(date, amount);
    }

    /**
     * 减一周
     */
    public static Date subWeeks(final Date date, int amount) {
        return DateUtils.addWeeks(date, -amount);
    }

    /**
     * 加一天
     */
    public static Date addDays(final Date date, final int amount) {
        return DateUtils.addDays(date, amount);
    }

    /**
     * 减一天
     */
    public static Date subDays(final Date date, int amount) {
        return DateUtils.addDays(date, -amount);
    }

    /**
     * 加一小时
     */
    public static Date addHours(final Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    /**
     * 减一小时
     */
    public static Date subHours(final Date date, int amount) {
        return DateUtils.addHours(date, -amount);
    }

    /**
     * 加一分钟
     */
    public static Date addMinutes(final Date date, int amount) {
        return DateUtils.addMinutes(date, amount);
    }

    /**
     * 减一分钟
     */
    public static Date subMinutes(final Date date, int amount) {
        return DateUtils.addMinutes(date, -amount);
    }

    /**
     * 加一秒.
     */
    public static Date addSeconds(final Date date, int amount) {
        return DateUtils.addSeconds(date, amount);
    }

    /**
     * 减一秒.
     */
    public static Date subSeconds(final Date date, int amount) {
        return DateUtils.addSeconds(date, -amount);
    }


    /**
     * 方法名:getStartDayOfWeekNo
     * 功能:某周的开始日期, 以星期一开始
     */
    public static String getStartDayOfWeekNo(int year, int weekNo) {
        Calendar cal = toCalendar(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        String month = (cal.get(Calendar.MONTH) + 1) < 10 ? "0" + (cal.get(Calendar.MONTH) + 1) : String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + cal.get(Calendar.DAY_OF_MONTH) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        return cal.get(Calendar.YEAR) + month + day;
    }

    /**
     * 方法名:getEndDayOfWeekNo
     * 功能:某周的结束日期, 以星期日结束
     */
    public static String getEndDayOfWeekNo(int year, int weekNo) {
        Calendar cal = toCalendar(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        String month = (cal.get(Calendar.MONTH) + 1) < 10 ? "0" + (cal.get(Calendar.MONTH) + 1) : String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + cal.get(Calendar.DAY_OF_MONTH) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        return cal.get(Calendar.YEAR) + month + day;
    }

    private static Calendar toCalendar(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        return cal;
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
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_PATTERN_yyyyMMdd);
        Console.println(sdf.format(cal.getTime()));
        return sdf.format(cal.getTime());
    }


    public static void printSimpleDateTime(Date date) {
        if (NullUtil.isNull(date)) {
            date = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_PATTERN_yyyyMMddHHmmss);
        String dateformat = sdf.format(date);
        log.info("时间：{}", dateformat);
    }


    /**
     * 以今年为标识，获取上N年的年份List列表
     */
    public static List<String> getPreviousYear(int length) {
        List<String> years = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        for (int i = year - length; i <= year; i++) {
            years.add(String.valueOf(i));
        }
        return years;
    }


}

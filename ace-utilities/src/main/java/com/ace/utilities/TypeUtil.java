package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TypeUtil {
    static private final Logger log = LogManager.getLogger(TypeUtil.class);

    public static void main(String[] args) {
        System.out.println(roundUpByDigit(0.2896959, 3));
    }

    /**
     * @param input  输入double数值
     * @param length round长度
     * @return
     */
    public static double roundUpByDigit(double input, int length) {
        return new BigDecimal(input).setScale(length, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 方法名：formatToDouble
     * 功能：double类型数值保留两位小数
     * 描述：返回值为double
     */
    public static Double toDouble(Double number, Integer length) {
        if (NullUtil.isNull(number)) {
            return null;
        }
        if (NullUtil.isNull(length)) {
            length = 2;
        }
        BigDecimal bigDecimal = new BigDecimal(number);
        return bigDecimal.setScale(length, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 方法名：formatToString
     * 功能：double类型的数值保留两位小数
     * 描述：返回值为String
     */
    public static String doubleToString(Double number, Integer length) {
        if (NullUtil.isNull(number)) {
            return null;
        }
        if (NullUtil.isNull(length)) {
            length = 2;
        }
        String place = "";
        for (int i = 0; i < length; i++) {
            place = place + "0";
        }

        DecimalFormat df = new DecimalFormat("#." + place);
        return df.format(number);
    }


    //Integer To BigDecimal
    public static Integer toBigDecimal(BigDecimal b) {
        if (NullUtil.isNull(b)) {
            return null;
        }
        return b.intValue();
    }

    //bigDecimal To Integer
    public static BigDecimal toBigDecimal(Integer i) {
        if (NullUtil.isNull(i)) {
            return null;
        }
        return BigDecimal.valueOf(i);
    }

    //String to Long
    public static Long toLong(String s) {
        try {
            return Long.valueOf(s);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    //Long to Integer
    public static Integer toInteger(Long l) {
        try {
            return l.intValue();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;

    }

    //Long to String
    public static String longToString(Long l) {
        try {
            return String.valueOf(l);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static String integerToString(Integer l) {
        try {

            return String.valueOf(l);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer toInteger(String s) {
        try {
            return Integer.valueOf(s);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static Long toLong(Integer i) {
        try {
            return i.longValue();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static String booleanToString(boolean b) {
        return String.valueOf(b);
    }

    /**
     * 方法名：isNumeric
     * 功能：判断字符串是否是数字
     * 入参：str：字符串
     * 出参：return：true或false
     */
    public static boolean convertToNumeric(String str) {
        if (NullUtil.isNull(str) || str.isEmpty()) {
            Console.println("parameter is null , please check");
            return false;
        }

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e1) {
                try {
                    Float.parseFloat(str);
                    return true;
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                    return false;
                }
            }
        }
    }

    /**
     * 整數 return true, 其他return false
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }


    //检查double是否整数
    public static boolean isInteger(double obj) {
        System.out.println(obj);
        double eps = 1e-10;  // 精度范围
        System.out.println(eps);
        return obj - Math.floor(obj) < eps;
    }

    public static char[] toCharset(String s) {
        // char[] charArray = s.toCharArray();
        return s.toCharArray();
    }

    public static String getType(Object o) {
        String type = o.getClass().getName();
        log.info("Object type: " + type);
        return type;
    }

    public static String getSimpleType(Object o) {
        String type = o.getClass().getSimpleName();
        log.info("Object type: " + type);
        return type;
    }

    /** 保留两位小数
     * @param amount
     * @return
     */
    public static String format(String amount) {
        double number = Double.parseDouble(amount);
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###.00");
        return decimalFormat.format(number);
    }
}

package com.ace.utilities;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//@SuppressWarnings("unchecked")
public class Strings {
    private static final Logger log = LogManager.getLogger(Strings.class);

    public static String EN = "en";
    public static String CN = "cn";
    public static String MIX = "mix";

    /**
     * 带占位符功能的string工具
     * "My name is {} and I am {} years old." , "John" , 25
     *
     * @param content
     * @param param
     * @return
     */
    public static String fmt(StringBuilder content, Object... param) {
        int count = StringUtils.countMatches(content, "{}");
        if (count != param.length) {
            throw new RuntimeException("placeholder not matched");
        }
        int i = 0;
        while (content.toString().contains("{}")) {
            int prefix = content.indexOf("{");
            int suffix = content.indexOf("}");
            if (!(param[i] instanceof String)) {
                param[i] = String.valueOf(param[i]).replace(",", "");
            }
            content.replace(prefix, suffix + 1, param[i].toString());
            ++i;
        }
        return content.toString();
    }

    /**
     * 带占位符功能的string工具
     * "My name is {0} and I am {1} years old." , "John" , 25
     * 同时支持
     * "My name is {} and I am {} years old." , "John" , 25
     * 但两种占位符不能混用
     *
     * @param content
     * @param param
     * @return
     */
    public static String fmt(String content, Object... param) {
        if (content.contains("{}")) {
            return fmt(new StringBuilder(content), param);
        }
        int length = param.length;
        for (int i = 0; i < length; i++) {
            if (!(param[i] instanceof String)) {
                param[i] = String.valueOf(param[i]).replace(",", "");
            }
        }
        return MessageFormat.format(content, param);
    }

    /** input number, return letter
     * @param n
     * @return
     */
    public static String getLetter(int n) {
        StringBuilder result = new StringBuilder();
        while (n > 0) {
            n--; // 因为A对应1，而不是0，所以需要减1
            char ch = (char) (n % 26 + 'A');
            result.insert(0, ch);
            n = n / 26;
        }

        return result.toString();
    }


    /** input letter, return number
     * @param alpha
     * @return
     */
    public static int getNumber(String alpha) {
        int num = 0;
        int len = alpha.length();
        for (int i = 0; i < len; i++) {
            num = num * 26 + (alpha.charAt(i) - 'A' + 1);
        }
        return num;
    }

    /**
     * 带占位符功能的string工具
     * <p>
     * "My name is {0} and I am {1} years old." , "John" , 25
     *
     * @param content
     * @param param
     * @return
     */
    public static String fmt(String content, List<Object> param) {
        int size = param.size();
        Object[] arr = new Object[size];
        for (int i = 0; i < size; i++) {
            arr[i] = param.get(i);
        }
        return MessageFormat.format(content, arr);
    }

    /**
     * 根据字符串长度由短到长排列
     *
     * @param arr
     */
    public static void sortByLength(String[] arr) {
        Arrays.sort(arr, Comparator.comparing(String::length));
    }


    /**
     * 去除句子内的空格
     *
     * @param content
     * @return
     */
    public static String trimAll(String content) {
        return content.replace(" ", "");
    }

    //首字母大寫
    public static String upperFirstChar(String string) {
        char[] charArray = string.toCharArray();
        charArray[0] -= 32;
        return String.valueOf(charArray);
    }

    /**
     * 比较句子
     *
     * @param a
     * @param b
     */
    public static void compareSentences(String a, String b) {
        if (NullUtil.isNull(a) || a.isEmpty() || NullUtil.isNull(b) || b.isEmpty()) {
            System.out.println("Compare data empty, please check !");
            return;
        }
        List<String> aList = toList(a);
        List<String> bList = toList(b);

        //compare string
        if (aList.size() == 1 && bList.size() == 1) {
            compareString(a, b);
            return;
        }

        //compare sentence
        int init;
        if (aList.size() >= bList.size()) {
            init = aList.size();
            System.out.println(b);
            int i = 0;
            try {
                for (i = 0; i < init; i++) {
                    if (aList.get(i).equals(bList.get(i))) {
                        Console.print(aList.get(i), Console.WHITE);
                    } else {
                        Console.print(aList.get(i), Console.RED);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                for (; i < init; i++) {
                    Console.print(aList.get(i), Console.RED);
                }
            }
        } else {
            init = bList.size();
            System.out.println(a);
            int i = 0;
            try {
                for (i = 0; i < init; i++) {
                    if (bList.get(i).equals(aList.get(i))) {
                        Console.print(bList.get(i), Console.WHITE);
                    } else {
                        Console.print(bList.get(i), Console.RED);
                    }
                    System.out.print(" ");
                }
            } catch (IndexOutOfBoundsException e) {
                for (; i < init; i++) {
                    Console.print(bList.get(i), Console.RED);
                    System.out.print(" ");
                }
            }
        }
    }

    /**
     * 比较单词
     *
     * @param a
     * @param b
     */
    public static void compareString(String a, String b) {
        if (NullUtil.isNull(a) || a.isEmpty() || NullUtil.isNull(b) || b.isEmpty()) {
            System.out.println("Compare data empty, please check !");
            return;
        }

        char[] as = a.toCharArray();
        char[] bs = b.toCharArray();

        if (as.length != bs.length) {
            Console.println(a, Console.RED);
            Console.println(b, Console.WHITE);
        } else {
            Console.println(a, Console.WHITE);
            for (int i = 0; i < bs.length; i++) {
                if (String.valueOf(as[i]).equals(String.valueOf(bs[i]))) {
                    Console.print(String.valueOf(bs[i]), Console.WHITE);
                } else {
                    Console.print(String.valueOf(bs[i]), Console.RED);
                }
            }
        }

    }


    /**
     * 检查中英文
     *
     * @param c
     * @return
     */
    public static String isCnOrEn(char c) {
        if (c >= 0x0391 && c <= 0xFFE5) {
            //中文字符
            return CN;
        }
        if (c <= 0x00FF) {
            //英文字符
            return EN;
        }
        return null;
    }

    public static String isMixEnCn(String n) {
        char[] chars = n.toCharArray();
        Set<String> result = new HashSet<>();
        for (char c : chars) {
            if (result.size() == 2) {
                return MIX;
            }
            if (c >= 0x0391 && c <= 0xFFE5) {
                //中文字符
                result.add(CN);
            }
            if (c <= 0x00FF) {
                //英文字符, 包括数字和字符
                result.add(EN);
            }
        }
        return result.iterator().next();
    }


    public static boolean isCn(String n) {
        char[] chars = n.toCharArray();
        for (char c : chars) {
            if (c < 0x0391 || c > 0xFFE5) {
                return false;
            }
        }
        //中文字符
        return true;
    }

    public static boolean isEn(String n) {
        char[] chars = n.toCharArray();
        for (char c : chars) {
            if (c > 0x00FF) {
                return false;
            }
        }
        //英文字符
        return true;
    }


    /**
     * @param source
     * @return
     */
    public static List toList(String source) {
        if (NullUtil.isNull(source) || source.isEmpty()) {
            System.out.println("String is null");
            return null;
        }

        String[] sourceArray = source.split(" ");

        List<String> stringList = new ArrayList<>();
        Collections.addAll(stringList, sourceArray);
        return stringList;
    }


    /**
     * 将数据库col转换成Obj字段
     *
     * @param source
     */
    //public static void reformatColToObjName(String source) {
    //    //print total char
    //    char[] chars = source.toCharArray();
    //    System.out.println("total char: " + chars.length);
    //
    //    //key出现的次数
    //    source = source.toLowerCase();
    //    List<Integer> location = searchAllIndex(source, "_");
    //    System.out.println("key出现的次数: " + location.size() + "\n");
    //
    //    StringBuilder sourceBuild = new StringBuilder(source);
    //    for (int i = 0; i < location.size(); i++) {
    //        Integer integer = location.get(i);
    //        String s = source.substring(integer + 1, integer + 2).toUpperCase();
    //        //charList.add(s);
    //        // StringBuilder sourceBuild = new StringBuilder(source);
    //        sourceBuild.replace(integer + 1, integer + 2, s);
    //
    //    }
    //    String result = sourceBuild.toString();
    //    result = result.replace("_", "");
    //    System.out.println(result);
    //}


    //public static List<Integer> searchAllIndex(String str, String key) {
    //    List<Integer> location = new ArrayList<>();
    //    int a = str.indexOf(key);//*第一个出现的索引位置
    //    while (a != -1) {
    //        location.add(a);
    //        //System.out.print(a + "\t");
    //        a = str.indexOf(key, a + 1);//*从这个索引往后开始第一个出现的位置
    //    }
    //    return location;
    //}


    /**
     * 判断字符串的内容是不是全是数字
     *
     * @param text
     * @return
     */
    public static boolean isNumeric(String text) {
        if (NullUtil.isEmpty(text)) {
            return false;
        }
        int size = text.length();
        for (int i = 0; i < size; i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static char[] toCharset(String s) {
        return s.toCharArray();
    }

    /**
     * 無需要正則表達式或转义符
     * 转义字符splitter
     *
     * @param s
     * @param splitter
     * @return
     */
    public static String[] split(String s, String splitter) {

        String[] results;
        if (s.contains(".")) {
            results = s.split("\\.");
        } else if (s.contains("|")) {
            results = s.split("\\|");

        } else if (s.contains("*")) {
            results = s.split("\\*");

        } else if (s.contains("\\")) {
            results = s.split("\\\\");

        } else if (s.contains("[]")) {
            results = s.split("\\[\\]");

        } else if (s.contains("^")) {
            results = s.split("\\^");

        } /*else if (s.contains(":")) {
            results = s.split("\\:");

        } else if (s.contains(".")) {
            results = s.split("\\.");

        } else if (s.contains(".")) {
            results = s.split("\\.");

        }*/ else {
            results = s.split(splitter);
        }
        return results;
    }

    public static String bytesToString(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }


    /**
     * sorting 字符串版字组排序
     *
     * @param arr
     * @param asc
     * @param left  由左边第N位开始排序
     * @param right 到右边第M位停止排序
     */
    public static void sort(String[] arr, boolean asc, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(arr, asc, left, right);
            sort(arr, asc, left, pivotIndex - 1);
            sort(arr, asc, pivotIndex + 1, right);
        } else {
            log.error("not allow left >= right value");
        }
    }

    private static int partition(String[] arr, boolean order, int left, int right) {
        double pivot = Double.parseDouble(arr[right]); // 将最后一个元素作为基准值
        int i = left - 1;
        for (int j = left; j <= right - 1; j++) {
            double current = Double.parseDouble(arr[j]);
            if (order) {
                if (current <= pivot) {
                    i++;
                    swap(arr, i, j);
                }
            } else {
                if (current >= pivot) {
                    i++;
                    swap(arr, i, j);
                }
            }

        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    private static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 根据"/'/` 截取wording并以list返回
     *
     * @param sentence
     * @param symbol
     * @return
     */
    public static List<String> extractStringByQuotes(String sentence, String symbol) {
        String expression = switch (symbol) {
            case "`" -> "`([^`]*)`"; //截取 `
            case "'" -> "'([^']*)'"; //截取 '
            case "\"" -> "\"([^\"]*)\""; //截取 "
            default -> "illegal symbol !";
        };
        List<String> quotes = new ArrayList<>();
        if (expression.equals("illegal symbol !")) {
            log.error(expression);
            return quotes;
        }
        // Pattern pattern = Pattern.compile("['\"`]+([^'\"]*)['\"`]+"); //同时将 " ' ` 都截取出来
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(sentence);
        while (matcher.find()) {
            String quote = matcher.group(1);
            quotes.add(quote);
        }
        return quotes;
    }


    /**
     * 去除space并比较两句子
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equalsWithIgnoreSpace(String a, String b) {
        String x = a.replace(" ", "");
        String y = b.replace(" ", "");
        return x.equals(y);
    }

}

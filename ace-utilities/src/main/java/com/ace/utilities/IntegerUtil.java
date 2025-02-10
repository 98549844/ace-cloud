package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;

/**
 * @Classname: IntegerUtil
 * @Date: 2023/6/16 下午 12:05
 * @Author: kalam_au
 * @Description:
 */


public class IntegerUtil {
    private static final Logger log = LogManager.getLogger(IntegerUtil.class.getName());


    public static Integer[] sort(Integer[] integers, boolean asc) {
        if (asc) {
            return sortAsc(integers);
        }
        return sortDesc(integers);
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
     * @param integers 该方法会直接修改原始数组, 而不是返回一个新的已排序数组
     *                 由小到大排列
     *                 所以不需要return
     */
    private static Integer[] sortAsc(Integer[] integers) {
        Arrays.sort(integers);
        return integers;
    }


    /**
     * @param integers 由大到小排列
     */
    private static Integer[] sortDesc(Integer[] integers) {
        Arrays.sort(integers, Collections.reverseOrder());
        return integers;
    }

    /**
     * @param integers
     * @param asc      true = 升序 / false = 降序
     * @return
     */
    public static String sortToString(Integer[] integers, boolean asc) {
        Integer[] sortedResult;
        if (asc) {
            sortedResult = sortAsc(integers);
        } else {
            sortedResult = sortDesc(integers);
        }
        return Arrays.toString(sortedResult);
    }

}


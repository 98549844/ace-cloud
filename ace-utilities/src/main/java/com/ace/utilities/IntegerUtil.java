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


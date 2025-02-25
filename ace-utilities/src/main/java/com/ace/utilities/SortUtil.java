package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;


/**
 * @Classname: SortUtil
 * @Date: 25/2/25 PM9:36
 * @Author: garlam
 * @Description:
 */


public class SortUtil {
    private static final Logger log = LogManager.getLogger(SortUtil.class.getName());

    /**
     * 由小到大排序
     *
     * @param list
     * @return
     */
    public static List<Integer> sortAsc(List<Integer> list) {
        Collections.sort(list);
        return list;
    }


    /**
     * 由大到小排序
     *
     * @param list
     * @return
     */
    public static List<Integer> sortDesc(List<Integer> list) {
        Collections.reverse(list);
        return list;
    }
}


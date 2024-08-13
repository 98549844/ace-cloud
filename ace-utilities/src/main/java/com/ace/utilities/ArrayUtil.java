package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname: ArrayUtil
 * @Date: 31/7/2021 1:14 上午
 * @Author: garlam
 * @Description:
 */

@SuppressWarnings("unchecked")
public class ArrayUtil {
    private static final Logger log = LogManager.getLogger(ArrayUtil.class.getName());

    //https://blog.csdn.net/qq_43390235/article/details/106592102
    //Stream流之List、Integer[]、int[]相互转化


    public static void main(String[] args) {
        int[] a = {1, 23, 5, 12, 5, 23, 43, 2, 7, 3, 91};
        Integer[] b = {1, 23, 5, 12, 5, 23, 43, 2, 7, 3, 91};
        System.out.println(toHashSet(a));
        System.out.println(toHashSetAsc(a));
        System.out.println(toHashSetDesc(a));
    }

    public static boolean isArray(Object obj) {
        return NullUtil.nonNull(obj) && obj.getClass().isArray();
    }

    /**
     * 去除重复, 不排序
     *
     * @param nums
     * @return
     */
    public static Set toHashSet(int[] nums) {
        return Arrays.stream(nums).boxed().collect(Collectors.toSet());
    }

    /**
     * 去除重复, 升序
     *
     * @param nums
     * @return
     */
    public static Set toHashSetAsc(int[] nums) {
        int[] arrayInt = Arrays.stream(nums).sorted().toArray();
        return Arrays.stream(arrayInt).boxed().collect(Collectors.toSet());
    }

    /**
     * 去除重复, 降序
     *
     * @param nums
     * @return
     */
    public static Set toHashSetDesc(int[] nums) {
        Set<Integer> sortedSet = new TreeSet<>(Comparator.reverseOrder());
        sortedSet.addAll(toHashSet(nums));
        return sortedSet;
    }

    public static int[] toIntArray(List<Integer> ls) {
        return ls.stream().mapToInt(Integer::valueOf).toArray();
    }

    public static Integer[] listToIntegerArray(List ls) {
        return (Integer[]) ls.toArray(new Integer[0]);
    }

    public static String[] stringToStringArray(String s) {
        char[] c = s.toCharArray();
        int size = c.length;
        String[] ss = new String[size];
        for (int i = 0; i < size; i++) {
            ss[i] = String.valueOf(c[i]);
        }
        return ss;
    }


}


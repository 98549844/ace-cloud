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
    public static Set<Integer> toHashSet(int[] nums) {
        return Arrays.stream(nums).boxed().collect(Collectors.toSet());
    }

    /**
     * 去除重复, 升序
     *
     * @param nums
     * @return
     */
    public static Set<Integer> toHashSetAsc(int[] nums) {
        int[] arrayInt = Arrays.stream(nums).sorted().toArray();
        return Arrays.stream(arrayInt).boxed().collect(Collectors.toSet());
    }

    /**
     * 去除重复, 降序
     *
     * @param nums
     * @return
     */
    public static Set<Integer> toHashSetDesc(int[] nums) {
        Set<Integer> sortedSet = new TreeSet<>(Comparator.reverseOrder());
        sortedSet.addAll(toHashSet(nums));
        return sortedSet;
    }

    // 将List<Integer>转换为int[]数组
    public static int[] toArray(List<Integer> ls) {
        // 使用stream()方法将List转换为流
        // 使用mapToInt()方法将流中的元素转换为int类型
        // 使用toArray()方法将流转换为int[]数组
        return ls.stream().mapToInt(Integer::valueOf).toArray();
    }

    public static Integer[] toIntegerArray(List<Integer> ls) {
        return ls.toArray(new Integer[0]);
    }

    public static String[] toStringArray(String s) {
        // 将字符串转换为字符数组
        char[] c = s.toCharArray();
        // 获取字符数组的长度
        int size = c.length;
        // 创建一个与字符数组长度相同的字符串数组
        String[] ss = new String[size];
        // 遍历字符数组
        for (int i = 0; i < size; i++) {
            // 将字符数组中的每个字符转换为字符串，并存储到字符串数组中
            ss[i] = String.valueOf(c[i]);
        }
        // 返回字符串数组
        return ss;
    }


}


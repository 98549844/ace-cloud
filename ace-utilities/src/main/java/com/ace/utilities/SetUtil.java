package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;

import java.util.*;
import java.util.stream.Collectors;

//@SuppressWarnings(("unchecked"))
public class SetUtil {
    private static final Logger log = LogManager.getLogger(SetUtil.class.getName());


    public static Object getFirst(Set s) {
        if (NullUtil.nonNull(s)) {
            return s.iterator().next();
        }
        return null;
    }


    public static Set arrayIntToHashSet(int[] nums) {
        Set<Integer> set = Arrays.stream(nums).boxed().collect(Collectors.toSet());
        return set;
    }

    public static List toList(Set set) {
        try {
            List ls = new ArrayList();
            ls.addAll(set);
            return ls;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public static <T> Set<T> toSet(List<T> list) {
        return new HashSet<>(list);
    }

    public static <T> void iterateSet(Set<T> tSet) {
        Iterator<T> iterator = tSet.iterator();
        int i = 1;
        while (iterator.hasNext()) {
            log.info("{}: {}", i, iterator.next());
        }
    }

    public static <T> Iterator<T> iterateHashSet(HashSet<T> hashSet) {
        Iterator<T> iterator = hashSet.iterator();
        int i = 1;
        while (iterator.hasNext()) {
            log.info("{}: {}", i, iterator.next());
        }
        return iterator;
    }

    public static Object getSetFirstValue(Set set) throws Exception {
        if (NullUtil.isNull(set)) {
            throw new Exception("Set is null");
        }
        return set.iterator().next();
    }

    public static boolean compareSet(Set<T> set1, Set<T> set2) {
        return Arrays.deepEquals(new Set[]{set1}, new Set[]{set2});
    }

    /**
     * 检查大小写
     *
     * @param arr
     * @param target
     * @return
     */
    public static boolean containsString(String[] arr, String target) {
        for (String str : arr) {
            if (str.equals(target)) {
                return true;
            }
        }
        return false;
    }

}

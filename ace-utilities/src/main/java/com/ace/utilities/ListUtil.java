package com.ace.utilities;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hpsf.Decimal;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
@SuppressWarnings("unchecked")
public class ListUtil {
    static private final Logger log = LogManager.getLogger(ListUtil.class);

    public final static String LIST_1 = "LIST_1";
    public final static String LIST_2 = "LIST_2";


    /**
     * list转换成map
     * 入参格式 (Users::getUserId, users)
     *
     * @param keyExtractor
     * @param list
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> Map<K, T> toMap(Function<T, K> keyExtractor, List<T> list) {
        return list.stream().collect(Collectors.toMap(keyExtractor, obj -> obj));
    }

    /**
     * 所有字符串转换成大写
     *
     * @param in
     * @return
     */
    public static List<String> toUpperCase(List<String> in) {
        if (NullUtil.isNull(in)) {
            throw new NullPointerException("List is null");
        }
        List<String> out = new ArrayList<>();
        for (String s : in) {
            out.add(s.toUpperCase());
        }
        return out;
    }

    /**
     * 所有字符串转换成小写
     *
     * @param in
     * @return
     */
    public static List<String> toLowerCase(List<String> in) {
        if (NullUtil.isNull(in)) {
            throw new NullPointerException("List is null");
        }
        List<String> out = new ArrayList<>();
        for (String s : in) {
            out.add(s.toLowerCase());
        }
        return out;
    }

    public static int getMax(List<Integer> integerList) {
        return NullUtil.isNull(integerList) ? 0 : Collections.max(integerList);
    }

    public static int getMin(List<Integer> integerList) {
        return NullUtil.isNull(integerList) ? 0 : Collections.min(integerList);
    }

    /**
     * int set convert to list
     *
     * @param nums
     * @return
     */
    public static List<Integer> toList(int[] nums) {
        return Arrays.asList(ArrayUtils.toObject(nums));
    }

    /**
     * int set convert to list
     *
     * @param i
     * @return
     */
    public static List<Integer> intToList(int[] i) {
        return Arrays.stream(i).boxed().collect(Collectors.toList());
    }


    /**
     * 检查元素有没有重复
     *
     * @param ls
     * @return
     */
    public static <T> boolean isDuplicate(List<T> ls) {
        HashSet<T> hashSet = new HashSet<>(ls);
        return ls.size() != hashSet.size();
    }


    /**
     * 比较两个list是否完全相同, 兼容位置不一样
     *
     * @param
     * @return
     */
    public static <T extends Comparable<? super T>> boolean compareList(List<T> ls1, List<T> ls2) {
        boolean result = false;
        int size1 = ls1.size();
        int size2 = ls2.size();

        Collections.sort(ls1); //升序排序
        Collections.sort(ls2); //升序排序

        if (size1 == size2) {
            for (int i = 0; i < size1; i++) {
                if (ls1.get(i).equals(ls2.get(i))) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }


    //根据长度把list拆分
    public static <T> List<List<T>> split(List<T> list, int len) {
        if (NullUtil.isNull(list) || list.isEmpty() || len < 1) {
            return null;
        }
        List<List<T>> result = new ArrayList<>();
        int size = list.size();
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, (Math.min((i + 1) * len, size)));
            result.add(subList);
        }
        return result;
    }


    /**
     * 去除list里重复元素
     *
     * @param list
     * @return
     */
    public static <T> List<T> removeDuplicate(List<T> list) {
        if (NullUtil.isNull(list)) {
            throw new NullPointerException("list is empty !");
        }
        Set<T> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }


    public static List<String> getResult(List<String> ls, String criteria, boolean include) {
        List<String> result = new ArrayList();
        if (include) {
            // list 内容含有criteria
            if (!ls.contains(criteria)) {
                return result;
            }
            for (String s : ls) {
                if (s.contains(criteria)) {
                    result.add(s);
                }
            }
        } else {
            // criteria含有list 内容
            for (String s : ls) {
                if (criteria.contains(s)) {
                    result.add(s);
                }
            }
        }
        return result;
    }


    /**
     * 比较两个list, 把重复的元素找出来, 并再过filter掉重复的元素, return list
     *
     * @param ls1
     * @param ls2
     * @param <T>
     * @return
     */
    public static <T> List<Object> getDeduplicatedList(List<T> ls1, List<T> ls2) {
        if (NullUtil.isNull(ls1, ls2)) {
            throw new NullPointerException("list is null");
        }

        List<T> t = ls1;
        if (ls2.size() > ls1.size()) {
            //保证ls1的长度大于ls2
            ls1 = ls2;
            ls2 = t;
        }
        Set<T> result = new HashSet<>();
        for (T t2 : ls2) {
            for (T t1 : ls1) {
                if (t2.equals(t1)) {
                    result.add(t2);
                }
            }
        }
        return Arrays.asList(result.toArray());
    }

    /**
     * 比较两个list, 把不相同的找出来
     * 返回独立list
     *
     * @param ls1
     * @param ls2
     * @return
     */
    public static <T> Map<String, List<T>> getNonDeduplicateElements(List<T> ls1, List<T> ls2) {
        Map<String, List<T>> map = new HashMap<>();
        List<T> ls = new ArrayList<>();
        for (T t : ls1) {
            if (!ls2.contains(t)) {
                log.info("List1 独立元素: " + t.toString());
                ls.add(t);
            }
        }
        if (!ls.isEmpty()) {
            map.put(ListUtil.LIST_1, ls);
        }
        ls = new ArrayList<>();
        for (T t : ls2) {
            if (!ls1.contains(t)) {
                log.info("List2 独立元素: " + t.toString());
                ls.add(t);
            }
        }
        if (!ls.isEmpty()) {
            map.put(ListUtil.LIST_2, ls);
        }

        if (!map.isEmpty()) {
            System.out.println("Compare result: NOT EQUAL !");
        } else {
            System.out.println("Compare result: EQUAL !");
        }
        return map;
    }


    /**
     * 比较两个list, 把不相同的找出来, 忽略空格" "
     *
     * @param ls1
     * @param ls2
     * @return
     */
    public static Map getNonDeduplicateElementsIgnoreSpace(List<String> ls1, List<String> ls2) {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for (Object o : ls1) {
            list1.add(o.toString().replace(" ", ""));
        }
        for (Object o : ls2) {
            list2.add(o.toString().replace(" ", ""));
        }
        return getNonDeduplicateElements(list1, list2);
    }


    /**
     * @param list
     * @return 重复元素list
     */
    public static <T> List<T> getDuplicated(List<T> list) {
        Set<T> listSet = new HashSet<>(list);
        Collection<T> sub = CollectionUtils.subtract(list, listSet);
        HashSet<T> hSet = new HashSet<>(sub);
        return new ArrayList<>(hSet);
    }


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

    /**
     * 将list转换成线性安全
     *
     * @param ls
     * @return
     */
    public static <T> List<T> getSynchronizedList(List<T> ls) {
        return Collections.synchronizedList(ls);
    }

    /**
     * 将list转换成不可以修改list
     *
     * @param ls
     * @return
     */
    public static <T> List<T> getUnmodifiableList(List<T> ls) {
        return Collections.unmodifiableList(ls);
    }

    public static void main(String[] args) {
        List<Integer> ls1 = new ArrayList<>();
        List ls2 = new ArrayList<>();
        // List<String> ls3 = new ArrayList<>();

        ls1.add(2);
        ls1.add(4);
        ls1.add(1);
        ls1.add(3);
        ls1.add(9);
        ls1.add(5);
        Collections.sort(ls1);

        ls2.add(4);
        ls2.add(9);
        ls2.add(9);
        ls2.add(9);
        ls2.add(2);
        ls2.add(5);
        ls2.add(5);
        ls2.add(7);
        ls2.add(8);


        Map a = getNonDeduplicateElements(ls1, ls2);

        List ls3 = getDuplicated(ls2);

        for (int i = 0; i < ls3.size(); i++) {
            System.out.println(ls3.get(i));
        }


    }

    /**
     * 随机选取list的元素(重覆)
     * percentage => 0% ~ 100%
     *
     * @param ls
     * @param percentage
     * @return
     */
    public static List<Integer> getRandomListByPercent(List<Integer> ls, Integer percentage) {
        if (NullUtil.isNull(percentage)) {
            return null;
        }
        int percentItems = ls.size() * percentage / 100;

        List<Integer> resultLs = new ArrayList<>();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < percentItems; i++) {
            int n = random.nextInt(ls.size());
            resultLs.add(ls.get(n));
        }
        return resultLs;
    }

    /**
     * 随机选取list里面的元素(不重覆)
     * percentage => 0% ~ 100%
     *
     * @param ls
     * @param percentage
     * @return
     */
    public static List<Integer> getRandomListByPercentNonRepeatable(List<Integer> ls, Integer percentage) {
        if (NullUtil.isNull(percentage)) {
            return null;
        }

        int percentItems = ls.size() * percentage / 100;

        List<Integer> resultLs = new ArrayList<>();
        SecureRandom random = new SecureRandom();
        while (resultLs.size() < percentItems) {
            int n = random.nextInt(ls.size());
            if (!resultLs.contains(ls.get(n))) {
                resultLs.add(ls.get(n));
            }
        }
        return resultLs;
    }


    //--------------------------------- 下面方法没有double check -----------------------------------------------

    /*

     */
/**
 * just a sample
 * need override
 *//*

    public static void sortList(List<Users> ls) {
        if (ls == null) {
            ls = DataGenerator.generateUsers();
        }
        int size = ls.size();
        System.out.println("********before sorting");
        for (int i = 0; i < size; i++) {
            if (i % 10 != 0) {
                System.out.print(ls.get(i).getUserId() + " ; ");
            } else {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("********after sorting");

        Ordering<Users> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Users, Integer>() {
            @Override
            public Integer apply(Users user) {
                return Math.toIntExact(user.getUserId());
            }
        });
        ls.sort(ordering);

        for (int i = 0; i < size; i++) {
            if (i % 10 != 0) {
                System.out.print(ls.get(i).getUserId() + " ; ");
            } else {
                System.out.println();
            }
        }
    }

    */

    /**
     * just a sample
     * need override
     *//*

    public static void sortListByCollections(List<Users> ls) {
        if (ls == null) {
            ls = DataGenerator.generateUsers();
        }
        int size = ls.size();
        System.out.println("********before sorting");
        for (int i = 0; i < size; i++) {
            if (i % 10 != 0) {
                System.out.print(ls.get(i).getUserId() + "(" + ls.get(i).getUsername() + ")" + " ; ");
            } else {
                System.out.println();
            }
        }
        System.out.println("");
        System.out.println("********after sorting");


        //compare函数的返回值-1, 1, 0
        //-1表示两个数位置交换，1表示不交换，0岂不是没有什么存在意义
        Collections.sort(ls, new Comparator<Users>() {
            @Override
            public int compare(Users o1, Users o2) {
                int i = 0;
                if (i == 0) {
                    i = o1.getUsername().compareTo(o2.getUsername());
                }

                if (i == 0) {
                    i = o1.getUserId().compareTo(o2.getUserId());
                }
                return i;
            }
        });
        for (int i = 0; i < size; i++) {
            if (i % 10 != 0) {
                System.out.print(ls.get(i).getUserId() + "(" + ls.get(i).getUsername() + ")" + " ; ");
            } else {
                System.out.println();
            }
        }
    }
*/
    public static <T> String listToString(List<T> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (T t : list) {
            sb.append(t).append(separator);
        }
        String result = list.isEmpty() ? "" : sb.substring(0, sb.toString().length() - 1);
        System.out.println(result);
        return result;
    }

    public static <T> List<T> removeElementByIterator(List<T> ls, T removalVal) {
        if (NullUtil.isNull(ls)) {
            log.error("List is null");
        }
        Iterator<T> iterator = ls.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(removalVal)) {
                iterator.remove();
            }
        }
        return ls;
    }

    public static <T> List<T> removeElementByLooping(List<T> ls, T removalVal) {
        if (NullUtil.isNull(ls)) {
            log.error("List is null");
        } else {
            for (int i = 0; i < ls.size(); i++) {
                if (ls.get(i).equals(removalVal)) {
                    ls.remove(i);
                    i = i - 1;
                }
            }
        }
        return ls;
    }

    public static <T> List<T> removeElementByLoop(List<T> ls, T removalVal) {
        if (NullUtil.isNull(ls)) {
            log.error("List is null");
        } else {
            for (int i = ls.size() - 1; i >= 0; i--) {
                if (ls.get(i).equals(removalVal)) {
                    ls.remove(i);
                }
            }
        }
        return ls;
    }


    public static List<String> arrayToList(String[] array) {
        List<String> list = Arrays.asList(array);
        return list;
    }


    public static void printListObjectSet(List<Object[]> list) {
        if (NullUtil.isNull(list)) {
            throw new NullPointerException("List is null");
        }
        log.info("List<Object[]> printing ...");
        for (Object[] objs : list) {
            for (Object o : objs) {
                if (o instanceof String result) {
                    log.info("type: String ; value: " + result);
                } else if (o instanceof Long result) {
                    log.info("type: Long ; value: " + result);
                } else if (o instanceof BigInteger result) {
                    log.info("type: BigInteger ; value: " + result);
                } else if (o instanceof Decimal result) {
                    log.info("type: Decimal ; value: " + result);
                } else if (o instanceof Integer result) {
                    log.info("type: Integer ; value: " + result);
                } else if (o instanceof Double result) {
                    log.info("type: Double ; value: " + result);
                } else if (o instanceof Float result) {
                    log.info("type: Float ; value: " + result);
                } else if (o instanceof Boolean result) {
                    log.info("type: Boolean ; value: " + result);
                } else if (o instanceof Date result) {
                    log.info("type: Date ; value: " + result);
                } else if (o instanceof LocalDate result) {
                    log.info("type: LocalDate ; value: " + result);
                } else if (o instanceof LocalDateTime result) {
                    log.info("type: LocalDateTime ; value: " + result);
                } else if (o == null) {
                    log.info("Object value is NULL !");
                } else {
                    log.info("type: UNKNOWN ; value: " + o);
                }
            }
            log.info(Console.LINE);
        }
        log.info("List<Object[]> print completed !");
    }

    /**
     * List删除target的object并返回删除后的list
     *
     * @param source
     * @param target
     * @return
     */
    public static List<Object> removeElement(List<Object> source, Object target) {
        source.removeIf(o -> o.equals(target));
        return source;
    }
}



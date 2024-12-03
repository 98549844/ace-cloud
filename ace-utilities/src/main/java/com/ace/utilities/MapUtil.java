package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class MapUtil {
    private static final Logger log = LogManager.getLogger(MapUtil.class.getName());


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


    public static Map<Object, List<Map.Entry<String, String>>> groupElement(Map<String, String> m) {
        Map<Object, List<Map.Entry<String, String>>> result = m.entrySet().stream().collect(Collectors.groupingBy(c -> c.getValue()));
        return result;
    }


    public static Map getDuplicatedMap(List list) {
        List temp1 = new ArrayList();
        List temp2 = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (!temp1.contains(list.get(i))) {
                temp1.add(list.get(i));
            } else {
                temp2.add(list.get(i));
            }
        }
        Map map = new HashMap();
        map.put("nonDuplicate", temp1);
        map.put("duplicate", temp2);
        return map;
    }


    /**
     * 历遍所有map元素
     *
     * @param map
     */
    public static void iterateMapKeySet(Map map) {
        List<String> header = new ArrayList<>();
        header.add("key:");
        header.add("value:");
        List<String[]> body = new ArrayList<>();

        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            body.add(new String[]{key.toString(), val.toString()});
        }
        ConsoleTable.println(header, body);
        Console.println("The Map size is : " + map.size(), Console.MAGENTA);
        Console.println("keyset : " + map.keySet(), Console.BLUE);
    }


    public static List getKeySet(Map map) {
        Iterator iter = map.entrySet().iterator();
        List ls = new ArrayList();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            ls.add(key);
        }
        return ls;
    }

    public static List getValueSet(Map map) {
        Iterator iter = map.entrySet().iterator();
        List ls = new ArrayList();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getValue();
            ls.add(key);
        }
        return ls;
    }


    public void printKeyValuesOnMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            //System.out.println("Key: " + k + "   " + "value: " + v.toString());
            System.out.print("Key: ");
            Console.print(k, Console.BOLD, Console.BLUE);
            System.out.print("   ");
            System.out.print("value: ");
            Console.println(v.toString(), Console.BOLD, Console.BLUE);
        }
    }

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (NullUtil.isNull(map)) return null;
        Object obj = beanClass.getDeclaredConstructor().newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        return obj;
    }

    public static Map<?, ?> objectToMap(Object obj) {
        if (NullUtil.isNull(obj)) {
            return null;
        }
        return new org.apache.commons.beanutils.BeanMap(obj);
    }

    public static <T> T flushObject(T t, Map<String, Object> params) {
        if (NullUtil.isNull(params) || NullUtil.isNull(t)) {
            return t;
        }

        Class<?> clazz = t.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    // 获取属性的名字
                    String name = field.getName();
                    Object value = params.get(name);
                    if (NullUtil.nonNull(value) && !"".equals(value)) {
                        // 注意下面这句，不设置true的话，不能修改private类型变量的值
                        field.setAccessible(true);
                        field.set(t, value);
                    }
                }
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        return t;
    }

}

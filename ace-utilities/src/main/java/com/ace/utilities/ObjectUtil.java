package com.ace.utilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectUtil {
    static private final Log log = LogFactory.getLog(ObjectUtil.class);


    private static final String GET_METHOD_PREFIX = "get";
    private static final String SET_METHOD_PREFIX = "set";

    public static String getType(Object object) {
        Class<?> objClass = object.getClass();
        String type = objClass.getName();
        log.info("Object type: " + type);
        return type;
    }

    public static String getSimpleType(Object o) {
        String type = o.getClass().getSimpleName();
        log.info("Object type: " + type);
        return type;
    }


    public static <T, E> boolean compareFieldValue(T t, E e, String fieldName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (NullUtil.isNull(t) || NullUtil.isNull(e)) {
            String val1 = String.valueOf(ObjectUtil.getFieldValue(t, fieldName));
            String val2 = String.valueOf(ObjectUtil.getFieldValue(e, fieldName));
            return StringUtils.hasText(val1) && StringUtils.hasText(val2) && val1.equals(val2);
        }
        return false;
    }

    public static byte[] toByteArray(Object obj) {
        return FastJson2Util.toJson(obj).getBytes();
    }


    public static <T> Object getFieldValue(T t, String fieldName) throws IllegalArgumentException, NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException, InvocationTargetException {
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
        String methodName = getMethodNameStartsWithGet(fieldName);
        if (NullUtil.isNull(methodName)) {
            throw new IllegalArgumentException("Class: " + t.getClass().getName() + ".  Method not found, please check field name");
        }
        Class<?> clazz = t.getClass();
        Method getMethod = clazz.getMethod(methodName);
        return getMethod.invoke(t);
    }

    public static List<String> getGetterMethodsName(Class<?> clazz) {
        List<String> getterNames = new ArrayList<>();

        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if (isGetter(method)) {
                getterNames.add(method.getName());
            }
        }
        return getterNames;
    }

    private static boolean isGetter(Method method) {
        return method.getName().startsWith("get") && method.getParameterCount() == 0 && !void.class.equals(method.getReturnType()) && Modifier.isPublic(method.getModifiers());
    }

    private static String getMethodNameStartsWithGet(String fieldName) {
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
        if (NullUtil.isNull(fieldName) || fieldName.isEmpty()) {
            log.error("Can not find fieldName: " + fieldName);
            return null;
        }
        String getMethodRealName;
        if (fieldName.startsWith("get")) {
            getMethodRealName = GET_METHOD_PREFIX + fieldName.substring(3, 4).toUpperCase() + fieldName.substring(4);
        } else {
            getMethodRealName = GET_METHOD_PREFIX + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

        }
        return getMethodRealName;
    }

    public static Boolean isArray(Object obj) {
        if (NullUtil.isNull(obj)) {
            log.warn("object is NULL !!!");
            return false;
        }
        return obj.getClass().isArray();
    }

    public static Integer getObjectArraySize(Object obj) {
        if (NullUtil.isNull(obj)) {
            return null;
        }
        int size = 0;
        if (NullUtil.nonNull(obj) && Boolean.TRUE.equals(isArray(obj))) {
            size = Array.getLength(obj);
        }
        return size;
    }

    public static Object[] getObjectArray(Object obj) {
        if (NullUtil.nonNull(obj) && Boolean.TRUE.equals(isArray(obj))) {
            Object[] os = (Object[]) obj;
            return os;
        }
        return null;
    }

    public static void printListObjectArray(List<Object[]> os) {
        if (NullUtil.isNull(os)) {
            Console.println("Object NullException", Console.RED, Console.BOLD);
        }
        for (Object[] objects : os) {
            for (int i = 0; i < objects.length; i++) {
                System.out.print(objects[i] + " | ");
            }
            System.out.println();
        }
    }

    public static void printObjectArray(Object[] obj) {
        if (NullUtil.isNull(obj)) {
            Console.println("Object NullException", Console.RED, Console.BOLD);
        }
        for (int i = 0; i < obj.length; i++) {
            System.out.print(obj[i] + " | ");
        }
        System.out.println();
    }

    public static void printListObject(List<Object> obj) {
        if (NullUtil.isNull(obj)) {
            Console.println("Object NullException", Console.RED, Console.BOLD);
        }

        for (Object objects : obj) {
            if (isArray(objects)) {
                Object[] os = (Object[]) objects;
                for (int i = 0; i < os.length; i++) {
                    System.out.print(os[i] + " | ");
                }
            } else {
                System.out.println(objects + " | ");
            }
        }
    }


    public static List toList(Object os) {
        List result = new ArrayList();
        if (os instanceof List<?>) {
            for (Object o : (List<?>) os) {
                result.add(o);
            }
        }
        log.info("Result.size: " + result.size());
        return result;
    }


    /**
     * 获取对像内存地址hashCode
     *
     * @param object
     * @return
     */
    public static int getObjectHashAddress(Object object) {
        return System.identityHashCode(object);
    }

}

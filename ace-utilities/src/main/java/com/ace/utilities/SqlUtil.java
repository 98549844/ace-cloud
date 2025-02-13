package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Classname: SqlUtil
 * @Date: 2022/9/16 下午 03:21
 * @Author: kalam_au
 * @Description:
 */


public class SqlUtil {
    private static final Logger log = LogManager.getLogger(SqlUtil.class.getName());


    public static String like(String s) {
        return "%" + s + "%";
    }

    public static String likeLeft(String s) {
        return "%" + s;
    }

    public static String likeRight(String s) {
        return s + "%";
    }

    public static Set<String> getDistinct(String target) throws IOException {
        List<String> targetList = (List<String>) FileUtil.read(target).get(FileUtil.LIST);
        return SetUtil.toSet(targetList);
    }

    /** 
     * 写入集合到文件
     * @param filePath 
     * @param fileName
     * @param set
     * @param <T>
     * @throws IOException
     */
    public static <T> void writeDistinct(String filePath, String fileName, Set<T> set) throws IOException {
        FileUtil.write(filePath, fileName, set, false);
    }

    /** 
     * 写入集合到文件, 并拼入单引号
     * @param filePath 
     * @param fileName
     * @param set
     * @param <T>
     * @throws IOException
     */
    public static <T> void writeDistinctString(String filePath, String fileName, Set<T> set) throws IOException {
        Set<String> setString = new HashSet<>();
        for (T t : set) {
            setString.add("'" + t.toString().trim() + "'");
        }
        FileUtil.write(filePath, fileName, setString, false);
    }


}


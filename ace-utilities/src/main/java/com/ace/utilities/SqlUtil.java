package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

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

    @SuppressWarnings("unchecked")
    public static Set<String> getDistinct(String path) throws IOException {
        List<String> targetList = (List<String>) FileUtil.read(path).get(FileUtil.LIST);
        return SetUtil.toSet(targetList);
    }

    /**
     * 写入集合到文件
     */
    public static void writeDistinct(String path, String targetPath, String fileName) throws IOException {
        Set<String> set = getDistinct(path);
        FileUtil.write(targetPath, fileName, set, false);
    }

    /**
     * 写入集合到文件, 并拼入单引号
     */
    public static void writeDistinctString(String targetPath, String destPath, String fileName) throws IOException {
        Set<String> result = getDistinctString(targetPath);
        FileUtil.write(destPath, fileName, result, false);
    }

    /**
     * 写入集合到文件, 并拼入单引号
     */
    public static void writeDistinctString(String targetPath, String destPath) throws IOException {
        Set<String> result = getDistinctString(targetPath);
        String destination = FileUtil.getParent(destPath);
        String fileName = FileUtil.getNameWithExt(destPath);
        FileUtil.write(destination, fileName, result, false);
    }


    public static Set<String> getDistinctString(String path) throws IOException {
        Set<String> set = getDistinct(path);
        Set<String> result = new HashSet<>();
        for (String s : set) {
            if (!s.startsWith("'") || !s.endsWith("'")) {
                s = s.replace("'", "");
                s = s.replace(",", "");
                s = "'" + s + "'";
            }
            result.add(s);
        }
        return result;
    }


    /**
     * @param path
     * @return
     * @throws IOException
     */
    public static Set<String> getDistinctNumber(String path) throws IOException {
        Set<String> ss = getDistinct(path);
        Set<String> result = new HashSet<>();
        for (String s : ss) {
            if (s.contains("'")) {
                s = s.replace("'", "");
            }
            if (!s.contains(",")) {
                s = s + ",";
            }
            result.add(s);
        }
        return result;
    }

    /**
     * 去除重复并写入到文件
     *
     * @param fileName
     * @param set
     * @param <T>
     * @throws IOException
     */
    public static <T> void writeDistinctNumber(String filePath, String fileName, Set<T> set) throws IOException {
        Set<String> result = getDistinctNumber(fileName);
        FileUtil.write(filePath, fileName, set, false);
    }


    public static List<String> listJoin(String path) throws IOException {
        List<String> targetList = (List<String>) FileUtil.read(path).get(FileUtil.LIST);
        boolean isNumeric = false;
        for (String s : targetList) {
            if (isNumeric) {
                break;
            }
            isNumeric = Strings.isNumeric(s);
        }
        List<String> result = new ArrayList<>();
        if (isNumeric) {
            for (String s : targetList) {
                result.add("'" + s + "',");
            }
        } else {
            for (String s : targetList) {
                result.add(s + ",");
            }
        }
        return result;
    }
}


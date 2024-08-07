package com.ace.utilities;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Classname: UnicodeUtil
 * @Date: 6/14/2024 2:29 PM
 * @Author: garlam.au
 * @Description:
 */


public class UnicodeUtil {
    private static final Logger log = LogManager.getLogger(UnicodeUtil.class.getName());

    public static void main(String[] args) throws IOException {
        String p = "C:\\Users\\Garlam.Au\\IdeaProjects\\bj_mgmt_portal\\src\\main\\resources\\properties\\";
        String f = "jeeplus.properties";
        System.out.println(unicodeToCNBuilder(p + f));
    }

    /**
     * 原文Unicode轉換
     *
     * @param fullPath
     * @return
     * @throws IOException
     */
    public static String unicodeToCNBuilder(String fullPath) throws IOException {
        String content = (String) Objects.requireNonNull(FileUtil.read(fullPath)).get(FileUtil.ORIGINAL);
        return unicodeToCN(content);
    }


    /**
     * 将Unicode编码转换为中文字符串
     *
     * @param str Unicode编码
     * @return
     */
    public static String unicodeToCN(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    /**
     * 中文转Unicode编码
     *
     * @param string 中文内容
     * @return
     */
    public static String cnToUnicode(String string) {
        char[] utfBytes = string.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

}


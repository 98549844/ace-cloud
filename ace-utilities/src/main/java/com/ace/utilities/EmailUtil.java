package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {
    static private final Logger log = LogManager.getLogger(EmailUtil.class);

    public static boolean isValid(String email) {
        // 判断email是否为空
        if (NullUtil.isNull(email)) {
            // 如果为空，抛出NullPointerException异常
            throw new NullPointerException("email address is null");
        }
        // 判断email是否包含分号
        if (email.contains(";")) {
            // 如果包含分号，调用validator方法，传入email.split(";")作为参数
            return isValid(email.split(";"));
        } else {
            // 定义email地址的正则表达式
            String checkPattern = "^([a-zA-Z0-9_])+@(([a-zA-Z0-9])+\\.)+([a-zA-Z0-9]{2,4})+$";
            // 编译正则表达式
            Pattern regex = Pattern.compile(checkPattern);
            // 创建Matcher对象
            Matcher matcher = regex.matcher(email);
            // 判断email是否匹配正则表达式
            String isValid = matcher.matches() ? "valid" : "invalid";
            // 打印email地址是否有效
            log.info(email + " is " + isValid + " Email address");
            // 返回匹配结果
            return matcher.matches();
        }
    }

    public static boolean isValid(String[] emails) {
        boolean isValid = false;
        for (String email : emails) {
            isValid = isValid(email);
            if (!isValid) {
                break;
            }
        }
        return isValid;
    }


    public static void main(String[] args) {
        boolean isEmail = EmailUtil.isValid("sxgkwei@16375.org");
        System.out.println(isEmail);
    }


}

package com.ace.utilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailUtil {
    static private final Log log = LogFactory.getLog(EmailUtil.class);

    public static boolean emailAddressValidator(String email) {
        if (NullUtil.isNull(email)) {
            log.info("Email address not exist");
            return false;
        }
        if (email.contains(";")) {
            return emailAddressValidator(email.split(";"));
        } else {
            String checkPattern = "^([a-zA-Z0-9_])+@(([a-zA-Z0-9])+\\.)+([a-zA-Z0-9]{2,4})+$";
            Pattern regex = Pattern.compile(checkPattern);
            Matcher matcher = regex.matcher(email);
            String isValid = matcher.matches() ? "valid" : "invalid";
            log.info(email + " is " + isValid + " Email address");
            return matcher.matches();
        }
    }

    public static boolean emailAddressValidator(String[] emails) {
        boolean isValid = false;
        for (String email : emails) {
            isValid = emailAddressValidator(email);
            if (!isValid) {
                break;
            }
        }
        return isValid;
    }


    public static void main(String[] args) {
        boolean isEmail = EmailUtil.emailAddressValidator("sxgkwei@16375.org");

        System.out.println(isEmail);
    }


}

package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NullUtil {
    private static final Logger log = LogManager.getLogger(NullUtil.class);


    public static boolean isEmpty(Object object) {
        if (object == null) {
            throw new NullPointerException("Object is null");
        }
        return isNull(object);
    }

    public static boolean notEmpty(Object object) {
        return !isNull(object);
    }


    public static boolean isNull(Object... objects) {
        for (Object object : objects) {
            if (isNull(object)) {
                return true;
            }
        }
        return false;
    }

    public static boolean nonNull(Object... objects) {
        for (Object object : objects) {
            if (nonNull(object)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNull(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String s) {
            return s.isEmpty();
        } else if (object instanceof List<?> ls) {
            return ls.isEmpty();
        } else if (object instanceof Map<?, ?> m) {
            return m.isEmpty();
        } else if (object instanceof Set<?> s) {
            return s.isEmpty();
        }
        return false;
    }

    public static boolean nonNull(Object object) {
        if (object == null) {
            return false;
        } else if (object instanceof String s) {
            return !s.isEmpty();
        } else if (object instanceof List<?> ls) {
            return !ls.isEmpty();
        } else if (object instanceof Map<?, ?> m) {
            return !m.isEmpty();
        } else if (object instanceof Set<?> s) {
            return !s.isEmpty();
        }
        return true;
    }

    public static boolean hasLength(String text) {
        return text != null && !text.isEmpty();
    }

    public static boolean hasText(String text) {
        if (!hasLength(text)) {
            return false;
        }
        int strLen = text.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isContain(String textToSearch, String substring) {
        return hasLength(textToSearch) && hasLength(substring) && textToSearch.contains(substring);
    }

    public static boolean isAssignable(Class<?> superType, Class<?> subType) {
        return nonNull(superType) && nonNull(subType) && superType.isAssignableFrom(subType);
    }

    public static boolean isInstanceOf(Class<?> type, Object object) {
        return nonNull(type) && type.isInstance(object);
    }


}

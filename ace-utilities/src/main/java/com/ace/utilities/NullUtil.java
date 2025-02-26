package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NullUtil {
    private static final Logger log = LogManager.getLogger(NullUtil.class);

    public static <T> boolean isEmpty(T object) {
        if (object == null) {
            throw new NullPointerException("Object is null");
        }
        return isNull(object);
    }

    public static <T> boolean notEmpty(T object) {
        return !isNull(object);
    }


    public static <T> boolean isNull(T... objects) {
        for (Object object : objects) {
            if (isNull(object)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean nonNull(T... objects) {
        for (Object object : objects) {
            if (nonNull(object)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean isNull(T object) {
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

    public static <T> boolean nonNull(T object) {
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

    public static boolean isAssignable(Class<?> superType, Class<?> subType) {
        return nonNull(superType) && nonNull(subType) && superType.isAssignableFrom(subType);
    }

    public static boolean isInstanceOf(Class<?> type, Object object) {
        return nonNull(type) && type.isInstance(object);
    }


}

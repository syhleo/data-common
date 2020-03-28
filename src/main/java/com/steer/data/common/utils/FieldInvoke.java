package com.steer.data.common.utils;

import java.lang.reflect.Method;

public class FieldInvoke {
    public static String getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            Object value = method.invoke(o);
            if (value == null) {
                return "";
            }
            return value.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String setFieldValueByName(String fieldName, Object o, String value) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String setter = "set" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(setter);
            method.invoke(o, value);
            if (value == null) {
                return "";
            }
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public static String setFieldValueByName(String fieldName, Object o, Long value) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String setter = "set" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(setter);
            method.invoke(o, value);
            if (value == null) {
                return "";
            }
            return value.toString();
        } catch (Exception e) {
            return null;
        }
    }
}

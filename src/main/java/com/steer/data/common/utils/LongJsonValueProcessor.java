package com.steer.data.common.utils;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class LongJsonValueProcessor implements JsonValueProcessor {

    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return null;
    }

    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        //如果vlaue为null，就返回"",不为空就返回他的值，
        if (value == null) {
            return "";
        }
        //return value.toString(); 
        return value;
    }
}

package com.steer.data.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用fastjson对数据进行序列化与反序列化，非常简单
 * 序列化为json格式的字符串序列，  将对象序列化为JSON字符串
 * java 对象转JSON（JSON序列化）
 * JSON转Java类[JSON反序列化]
 */
public class SerializeUtil {
    private  static Logger logger = LoggerFactory.getLogger("SerializeUtil");
    public static String serializeObject(Object obj){//对象序列化为JSON字符串
        logger.info("serialize object ："+obj);
        String jsonObj = JSON.toJSONString(obj);
        return jsonObj;
    }
    public static JSONObject unserializeObject(String serobj){//字符串反序列化为JSONObject对象
        logger.info("unserialize object ："+serobj);
        JSONObject jsonObj = JSON.parseObject(serobj);
        return jsonObj;
    }

    public static JSONArray unserializeArray(String arrStr) {
        logger.info("unserialize Array ："+arrStr);
        JSONArray jsonArray= JSON.parseArray(arrStr);
        return jsonArray;
    }

    public static void main(String[] args){
        String str1=serializeObject("leo");
        unserializeObject(str1);
        unserializeArray(str1);
    }

}


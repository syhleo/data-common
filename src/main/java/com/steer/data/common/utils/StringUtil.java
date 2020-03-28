package com.steer.data.common.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * 字符串工具类
 *
 * @author
 */
public class StringUtil {

    /**
     * 判断是否是空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 判断是否不是空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return (str != null) && !"".equals(str.trim());
    }

    /**
     * 格式化模糊查询
     *
     * @param str
     * @return
     */
    public static String formatLike(String str) {
        if (isNotEmpty(str)) {
            return "%" + str + "%";
        } else {
            return null;
        }
    }

    /**
     * 过滤掉集合里的空格
     *
     * @param list
     * @return
     */
    public static List<String> filterWhite(List<String> list) {
        List<String> resultList = new ArrayList<String>();
        for (String l : list) {
            if (isNotEmpty(l)) {
                resultList.add(l);
            }
        }
        return resultList;
    }

    public static List<String> filterWhite(String str) {
        String[] list = str.split(" ");
        //对空格进行处理
        List<String> customList = new ArrayList<>();
        for (String strf : list) {
            if (!strf.trim().equals("")) {
                customList.add(strf);
            }
        }
        return customList;
    }

    //获取List参数值
    public static String getListString(List<String> list) {
        StringBuilder result = new StringBuilder();
        for (String s : list) {
            result.append(s).append(" ");
        }
        return result.toString();
    }

    /**
     * 主键生成器，未处理
     *
     * @return 32位的UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 主键生成器，已处理
     *
     * @return 32位的UUID
     */
    public static String getUUIDDispose() {
        return StringUtil.getUUID().replace("-", "");
    }

    /**
     * jsonArray转List<HashMap<String,String>>
     * @param json格式字符串;
     * */
    //jsonArray格式

    /**
     * [ {"pic":"http://address4","order":"4"},
     * {"pic":"http://address3","order":"3"},
     * {"pic":"http://address2","order":"2"},
     * {"pic":"http://address1","order":"1"}]
     */
    public static List<HashMap<String, String>> jsonArrayStrToHashMapList(String pics) {
        List<HashMap<String, String>> hashMapList = new ArrayList<HashMap<String, String>>();
        if (pics == null || pics.equals("") || !pics.startsWith("[") || !pics.endsWith("]")) {
            return hashMapList;
        }
        JSONArray jsons = JSONArray.fromObject(pics);
        JSONObject json = null;
        HashMap<String, String> map = null;
        for (int i = 0; i < jsons.size(); i++) {
            json = jsons.getJSONObject(i);
            map = new HashMap<String, String>();
            map = JsonObjectToHashMap(json);
            hashMapList.add(map);
        }
        return hashMapList;
    }

    //1.將JSONObject對象轉換為HashMap<String,String>
    public static HashMap<String, String> JsonObjectToHashMap(JSONObject jsonObj) {
        HashMap<String, String> data = new HashMap<String, String>();
        Iterator it = jsonObj.keys();
        while (it.hasNext()) {
            String key = String.valueOf(it.next().toString());
            String value = jsonObj.get(key).toString();
            data.put(key, value);
        }
        //System.out.println(data);
        return data;
    }

    //2.将json字符串转换成HashMap<String,String>
    public static HashMap<String, String> JsonToHashMap(String JsonStrin) {
        HashMap<String, String> data = new HashMap<String, String>();
        try {
            // 将json字符串转换成jsonObject
            JSONObject jsonObject = JSONObject.fromObject(JsonStrin);
            @SuppressWarnings("rawtypes")
            Iterator it = jsonObject.keys();
            // 遍历jsonObject数据，添加到Map对象
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                String value = jsonObject.get(key).toString();
                data.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null,"ERROR:["+e+"]");
        }
        System.out.println(data);
        return data;
    }


}

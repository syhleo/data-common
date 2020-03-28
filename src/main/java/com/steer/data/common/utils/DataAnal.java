package com.steer.data.common.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//用于数据分析、处理。
public class DataAnal {

    /*
     * 字符串右补空格
     */
    public static String DataEncode(String data, int i, int type) throws Exception {
        if (data == null) {
            data = " ";
        }
        if (data.length() > i) {
            data = data.substring(data.length() - i);
        }
        data = String.format("%1$-" + i + "s", data);
        return data;
    }

    /**
     * @param data 输入数据
     * @param i    填充后的长度
     * @param type 填充方式  0：左补空格，为空时返回空字符串  1：右补0，为空时返回全0
     */
    public static String DataEncode(Long data, int i, int type) throws Exception {
        String temp = "";
        if (type == 0) {
            if (data == null) {
                try {
                    return DataAnal.DataEncode(" ", i, 0);
                } catch (Exception e) {
                    throw new Exception("填充Long数据失败" + e.getMessage());
                }
            }
            temp = String.format("%1$-" + i + "s", data + "");
        } else if (type == 1) {
            if (data == null) {
                data = 0L;
            }
            temp = String.format("%0" + i + "d", data);
        }

        return temp;
    }

    /**
     * @param data 输入数据
     * @param i    填充后的长度
     * @param type 填充方式  0：左补空格，为空时返回空字符串  1：右补0，为空时返回全0
     * @return String 返回后的数据
     */
    public static String DataEncode(Integer data, int i, int type) throws Exception {
        String temp = "";
        if (type == 0) {
            if (data == null) {
                try {
                    return DataAnal.DataEncode(" ", i, 0);
                } catch (Exception e) {
                    throw new Exception("填充Integer失败" + e.getMessage());
                }
            }
            temp = String.format("%1$-" + i + "s", data + "");
        } else if (type == 1) {
            if (data == null) {
                data = 0;
            }
            temp = String.format("%0" + i + "d", data);
        }
        return temp;
    }

    // 数字左补0，返回为字符串
    public static String DataEncode(Double data, int i) {
        if (data == null) {
            data = 0.0;
        }
        String temp = String.format("%0" + i + "f", data);
        return temp;
    }

    /*
     * 解析返回的数据内容
     *
     * @param 参数1：对象的字段名List
     *
     * @param 参数2：对象字段长度List
     *
     * @param 参数3：数据体
     *
     * @return List 格式：字段名:数据
     */
    public static List<String> DataDecode(List<Integer> list2, String body) throws Exception {
        if (list2 == null || body == null) {
            throw new Exception("解析数据不能为空");
        }
        if (body.length() != ListAdd(list2, list2.size())) {
            throw new Exception("解析时数据长度不匹配");
        }
        List<String> resultList = new ArrayList<String>();
        for (int i = 0; i < list2.size(); i++) {
            if (i == 0) {
                String s = body.substring(0, ListAdd(list2, i + 1));
                resultList.add(s);
            } else {
                String s = body.substring(ListAdd(list2, i), ListAdd(list2, i + 1));
                resultList.add(s);
            }
        }
        return resultList;

    }

    public static List<String> MsgDecode(String body) throws Exception {

        try {
            List<String> resultList = new ArrayList<String>();
            resultList.add(body.substring(0, 4));
            resultList.add(body.substring(4, 10));
            resultList.add(body.substring(10, 18));
            resultList.add(body.substring(18, 24));
            resultList.add(body.substring(24, 26));
            resultList.add(body.substring(26, 28));
            resultList.add(body.substring(28, 32));
            resultList.add(body.substring(32, 40));
            resultList.add(body.substring(40, Integer.valueOf(body.substring(0, 4).trim()) - 1));
            return resultList;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // 求List前i个数之和
    public static Integer ListAdd(List<Integer> list, Integer i) {
        List<Integer> newList = list.subList(0, i);
        Integer result = 0;
        for (Integer integer : newList) {
            result = result + integer;
        }
        return result;
    }

    public static List<Integer> getList(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        List<Integer> result = new ArrayList<Integer>();
        String[] list = str.split(",");
        for (String string : list) {
            result.add(Integer.parseInt(string));
        }
        return result;
    }

    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, StandardCharsets.UTF_8);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     *
     */
    public static Double deAccuracy(String s, int i) {
        if (s == null) {
            s = "";
        }
        Long temp = Long.valueOf(s);
        Double dou = Double.valueOf(temp) / Math.pow(10, i);
        return dou;
    }

    public static Long enAccuracy(Double dou, int i) {
        if (dou == null) {
            dou = 0.0;
        }
        Long l = (long) (dou * Math.pow(10, i));
        return l;
    }

    /*
     * 根据反射机制获取类的属性值，和表的字段进行匹配，然后拼接成电文
     *
     * @param obj:类的实例
     *
     * @param list: 表的所有字段名、长度、类型
     *
     * @return String
     */
    public static String match(Object obj, List<HashMap<String, String>> list) throws Exception {
        String[] fields = getFiledName(obj);
        String result = "";
        for (String name : fields) {
            for (HashMap<String, String> hashMap : list) {
                if (!hashMap.get("COLUMN_NAME").toUpperCase().equals(name.toUpperCase())) {
                    continue;
                }
                String temp = FieldInvoke.getFieldValueByName(name, obj) + "";
                Integer length = Integer.valueOf(hashMap.get("COLUMN_LENGTH"));
                result = result + DataAnal.DataEncode(temp, length, 0);
                break;
            }
        }

        return result;
    }

    /*
     * 根据类的实例，获取该类的所有属性名
     */
    public static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /*
     * 根据反射机制获取类的属性名，与表进行匹配，将匹配的字段长度进行拼接
     *
     * @param obj:类的实例
     *
     * @param list: 表的所有字段名、长度、类型
     *
     * @return String
     */
    public static void matchAndSet(String data, Object obj, List<HashMap<String, String>> list) throws Exception {
        String lengths = "";
        String[] fields = getFiledName(obj);
        // 定义一个List存放匹配的字段属性
        List<HashMap<String, String>> fieldNameList = new ArrayList<HashMap<String, String>>();

        for (String name : fields) {
            for (HashMap<String, String> hashMap : list) {
                if (!hashMap.get("COLUMN_NAME").toUpperCase().equals(name.toUpperCase())) {
                    continue;
                }
                fieldNameList.add(hashMap);
                Integer length = Integer.valueOf(hashMap.get("COLUMN_LENGTH"));
                lengths = lengths + length;
                break;
            }
        }

        // 根据长度获得数据
        List<Integer> lenList = DataAnal.getList(lengths);
        List<String> tempList = DataAnal.DataDecode(lenList, data);
        for (int i = 0; i < tempList.size(); i++) {
            HashMap<String, String> fieldMap = fieldNameList.get(i);
            String fieldName = fieldMap.get("COLUMN_NAME");
            String fieldType = fieldMap.get("COLUMN_TYPE").toLowerCase();
            if (fieldType.equals("NUMBER")) {
                FieldInvoke.setFieldValueByName(fieldName, obj, Long.valueOf(tempList.get(i)));
            } else {
                FieldInvoke.setFieldValueByName(fieldName, obj, tempList.get(i));
            }
        }

    }

    /*
     * 将List转成Json数组
     */
    @SuppressWarnings("rawtypes")
    public static String ToJson(List list) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        jsonConfig.registerJsonValueProcessor(java.lang.Double.class, new DoubleJsonValueProcessor());
        jsonConfig.registerJsonValueProcessor(java.lang.Long.class, new LongJsonValueProcessor());
        jsonConfig.registerJsonValueProcessor(java.lang.Integer.class, new IntegerJsonValueProcessor());
        jsonConfig.registerJsonValueProcessor(java.math.BigDecimal.class, new BigDecimalJsonValueProcessor());
        JSONArray jsonObject = JSONArray.fromObject(list, jsonConfig);
        String body = jsonObject.toString();
        return body;
    }

    @SuppressWarnings("rawtypes")
    public static String ToJson(HashMap map) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        jsonConfig.registerJsonValueProcessor(java.lang.Double.class, new DoubleJsonValueProcessor());
        jsonConfig.registerJsonValueProcessor(java.lang.Long.class, new LongJsonValueProcessor());
        jsonConfig.registerJsonValueProcessor(java.lang.Integer.class, new IntegerJsonValueProcessor());
        jsonConfig.registerJsonValueProcessor(java.math.BigDecimal.class, new BigDecimalJsonValueProcessor());
        JSONObject jsonObject = JSONObject.fromObject(map, jsonConfig);
        String body = jsonObject.toString();
        return body;
    }

    public static Long LongToString(String s) {
        if (s == null || s.trim().equals("")) {
            return null;
        } else {
            return Long.valueOf(s.trim());
        }

    }

    public static void main(String[] args) {
        System.out.println(DataAnal.hexStringToString("0A"));
    }


}

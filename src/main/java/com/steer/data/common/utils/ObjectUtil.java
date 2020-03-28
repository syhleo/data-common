/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.utils.DataUtil.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月15日上午11:17:07
 * <p>
 * 负责人: syhleo
 * <p>
 * 部门: 工程服务部
 * <p>
 * 修改者：（修改者姓名）
 * <p>
 * 修改时间：
 * <p>
 * 说明：
 * <p>
 */


package com.steer.data.common.utils;

import com.steer.data.db.datahandling.model.DataTransView;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class ObjectUtil {

    /**
     * 判定对象是否为空 包括字符串、list集合
     * @param obj 对象
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof List) {
            return ((List) obj).size() == 0;
        }
        if ((obj instanceof String)) {
            return ((String) obj).trim().equals("");
        }
        return false;
    }

    /**
     * 判定对象不为空
     * @param obj 对象
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static DataTransView getKeysValuesOracle(Map<String, Object> map) throws Exception {
        DataTransView dtv = new DataTransView();
        if (ObjectUtil.isEmpty(map)) {
            throw new Exception("数据库Map<String,Object>为空");
        }
        String keys = "";
        String values = "";
        //遍历key-value
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (ObjectUtil.isNotEmpty(entry.getValue())) {
                if (keys.equals("")) {
                    keys = keys + entry.getKey();
                } else {
                    keys = keys + "," + entry.getKey();
                }
                if (values.equals("")) {
                    if ((entry.getValue() instanceof String) || (entry.getValue() instanceof Character)) {
                        values = values + "'" + entry.getValue() + "'";
                    } else if (entry.getValue() instanceof Date) {
                        //排除entry.getValue()为2017-12-25 21:16:05.0的情况
                        String dateStrValue = DateUtil.formatDate((Date) entry.getValue(), "yyyy-MM-dd HH:mm:ss");
                        values = values + "to_date('" + dateStrValue + "','yyyy-MM-dd HH24:MI:SS')";
                    } else if (entry.getValue() instanceof oracle.sql.TIMESTAMP) {
                        //2017-12-25 21:16:05:000000
                        String dateStrValue = null;
                        try {
                            dateStrValue = DateUtil.formatDate((Date) entry.getValue(), "yyyy-MM-dd HH:mm:ss.SSSSSS");
                            values = values + "to_timestamp('" + dateStrValue + "','yyyy-mm-dd hh24:mi:ss.ff6')";
                        } catch (Exception ex) {
                            //排除entry.getValue()为2017-12-25 21:16:05.0的情况
                            dateStrValue = DateUtil.formatDate((Date) entry.getValue(), "yyyy-MM-dd HH:mm:ss");
                            values = values + "to_timestamp('" + dateStrValue + "','yyyy-MM-dd HH24:MI:SS')";
                        }
                    } else {
                        values = values + entry.getValue();
                    }
                } else {
                    if ((entry.getValue() instanceof String) || (entry.getValue() instanceof Character)) {
                        values = values + "," + "'" + entry.getValue() + "'";
                    } else if (entry.getValue() instanceof Date) {
                        //排除entry.getValue()为2017-12-25 21:16:05.0的情况
                        String dateStrValue = DateUtil.formatDate((Date) entry.getValue(), "yyyy-MM-dd HH:mm:ss");
                        values = values + "," + "to_date('" + dateStrValue + "','yyyy-MM-dd HH24:MI:SS')";
                    } else if (entry.getValue() instanceof oracle.sql.TIMESTAMP) {
                        //2017-12-25 21:16:05:000000
                        String dateStrValue = null;
                        try {
                            dateStrValue = DateUtil.formatDate(((oracle.sql.TIMESTAMP) entry.getValue()).dateValue(), "yyyy-MM-dd HH:mm:ss.SSSSSS");
                            values = values + "," + "to_timestamp('" + dateStrValue + "','yyyy-mm-dd hh24:mi:ss.ff6')";
                        } catch (Exception ex) {
                            //排除entry.getValue()为2017-12-25 21:16:05.0的情况
                            dateStrValue = DateUtil.formatDate((Date) entry.getValue(), "yyyy-MM-dd HH:mm:ss");
                            values = values + "," + "to_timestamp('" + dateStrValue + "','yyyy-MM-dd HH24:MI:SS')";
                        }


                    } else {
                        values = values + "," + entry.getValue();
                    }
                }

            }
        }
        dtv.setKeys(keys);
        dtv.setValues(values);
        return dtv;
    }

    public static DataTransView getKeysValuesOracle2(Map<String, String> map) throws Exception {
        DataTransView dtv = new DataTransView();
        if (ObjectUtil.isEmpty(map)) {
            throw new Exception("数据库Map<String,String>为空");
        }
        String keys = "";
        String values = "";
        //遍历key-value
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (ObjectUtil.isNotEmpty(entry.getValue())) {
                if (keys.equals("")) {
                    keys = keys + entry.getKey();
                } else {
                    keys = keys + "," + entry.getKey();
                }
                if (values.equals("")) {
                    if ((entry.getValue() instanceof String && !entry.getValue().startsWith("to_date("))) {
                        values = values + "'" + entry.getValue() + "'";
                    } else {
                        values = values + entry.getValue();
                    }
                } else {
                    if ((entry.getValue() instanceof String && !entry.getValue().startsWith("to_date("))) {
                        values = values + "," + "'" + entry.getValue() + "'";
                    } else {
                        values = values + "," + entry.getValue();
                    }
                }
            }
        }
        dtv.setKeys(keys);
        dtv.setValues(values);
        return dtv;
    }


    public static DataTransView getKeysValuesMysql(Map<String, Object> map) throws Exception {
        DataTransView dtv = new DataTransView();
        if (ObjectUtil.isEmpty(map)) {
            throw new Exception("数据库Map<String,Object>为空");
        }
        String keys = "";
        String values = "";
        //遍历key-value
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (ObjectUtil.isNotEmpty(entry.getValue())) {
                if (keys.equals("")) {
                    keys = keys + entry.getKey();
                } else {
                    keys = keys + "," + entry.getKey();
                }
                if (values.equals("")) {
                    if ((entry.getValue() instanceof String) || (entry.getValue() instanceof Character)) {
                        values = values + "'" + entry.getValue() + "'";
                    } else if (entry.getValue() instanceof Date) {
                        //排除entry.getValue()为2017-12-25 21:16:05.0的情况
                        String dateStrValue = DateUtil.formatDate((Date) entry.getValue(), "yyyy-MM-dd HH:mm:ss");
                        values = values + "str_to_date('" + dateStrValue + "','%Y-%m-%d %H:%i:%s')";
                    } else if (entry.getValue() instanceof oracle.sql.TIMESTAMP) {
                        //2017-12-25 21:16:05:000000
                        String dateStrValue = null;
                        //排除entry.getValue()为2017-12-25 21:16:05.0的情况
                        dateStrValue = DateUtil.formatDate(((oracle.sql.TIMESTAMP) entry.getValue()).dateValue(), "yyyy-MM-dd HH:mm:ss");
                        values = values + "str_to_date('" + dateStrValue + "','%Y-%m-%d %H:%i:%s')";
                    } else {
                        values = values + entry.getValue();
                    }
                } else {
                    if ((entry.getValue() instanceof String) || (entry.getValue() instanceof Character)) {
                        values = values + "," + "'" + entry.getValue() + "'";
                    } else if (entry.getValue() instanceof Date) {
                        //排除entry.getValue()为2017-12-25 21:16:05.0的情况
                        String dateStrValue = DateUtil.formatDate((Date) entry.getValue(), "yyyy-MM-dd HH:mm:ss");
                        values = values + "," + "str_to_date('" + dateStrValue + "','%Y-%m-%d %H:%i:%s')";
                    } else if (entry.getValue() instanceof oracle.sql.TIMESTAMP) {
                        //2017-12-25 21:16:05:000000
                        String dateStrValue = null;
                        //排除entry.getValue()为2017-12-25 21:16:05.0的情况
                        dateStrValue = DateUtil.formatDate(((oracle.sql.TIMESTAMP) entry.getValue()).dateValue(), "yyyy-MM-dd HH:mm:ss");
                        values = values + "," + "str_to_date('" + dateStrValue + "','%Y-%m-%d %H:%i:%s')";
                    } else {
                        values = values + "," + entry.getValue();
                    }
                }

            }
        }
        dtv.setKeys(keys);
        dtv.setValues(values);
        return dtv;
    }

    public static DataTransView getKeysValuesSqlServer(Map<String, Object> map) throws Exception {
        DataTransView dtv = new DataTransView();
        if (ObjectUtil.isEmpty(map)) {
            throw new Exception("数据库Map<String,Object>为空");
        }
        String keys = "";
        String values = "";
        //遍历key-value
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (ObjectUtil.isNotEmpty(entry.getValue())) {
                if (keys.equals("")) {
                    keys = keys + entry.getKey();
                } else {
                    keys = keys + "," + entry.getKey();
                }
                if (values.equals("")) {
                    if ((entry.getValue() instanceof String) || (entry.getValue() instanceof Character)) {
                        values = values + "'" + entry.getValue() + "'";
                    } else if (entry.getValue() instanceof Date) {
                        //排除entry.getValue()为2017-12-25 21:16:05.0的情况
                        String dateStrValue = DateUtil.formatDate((Date) entry.getValue(), "yyyy-MM-dd HH:mm:ss");
                        values = values + "to_date('" + dateStrValue + "','yyyy-MM-dd HH24:MI:SS')";
                    } else if (entry.getValue() instanceof oracle.sql.TIMESTAMP) {
                        //2017-12-25 21:16:05:000000
                        String dateStrValue = null;
                        try {
                            dateStrValue = DateUtil.formatDate((Date) entry.getValue(), "yyyy-MM-dd HH:mm:ss.SSSSSS");
                            values = values + "to_timestamp('" + dateStrValue + "','yyyy-mm-dd hh24:mi:ss.ff6')";
                        } catch (Exception ex) {
                            //排除entry.getValue()为2017-12-25 21:16:05.0的情况
                            dateStrValue = DateUtil.formatDate((Date) entry.getValue(), "yyyy-MM-dd HH:mm:ss");
                            values = values + "to_timestamp('" + dateStrValue + "','yyyy-MM-dd HH24:MI:SS')";
                        }
                    } else {
                        values = values + entry.getValue();
                    }
                } else {
                    if ((entry.getValue() instanceof String) || (entry.getValue() instanceof Character)) {
                        values = values + "," + "'" + entry.getValue() + "'";
                    } else if (entry.getValue() instanceof Date) {
                        //排除entry.getValue()为2017-12-25 21:16:05.0的情况
                        String dateStrValue = DateUtil.formatDate((Date) entry.getValue(), "yyyy-MM-dd HH:mm:ss");
                        values = values + "," + "to_date('" + dateStrValue + "','yyyy-MM-dd HH24:MI:SS')";
                    } else if (entry.getValue() instanceof oracle.sql.TIMESTAMP) {
                        //2017-12-25 21:16:05:000000
                        String dateStrValue = null;
                        try {
                            dateStrValue = DateUtil.formatDate(((oracle.sql.TIMESTAMP) entry.getValue()).dateValue(), "yyyy-MM-dd HH:mm:ss.SSSSSS");
                            values = values + "," + "to_timestamp('" + dateStrValue + "','yyyy-mm-dd hh24:mi:ss.ff6')";
                        } catch (Exception ex) {
                            //排除entry.getValue()为2017-12-25 21:16:05.0的情况
                            dateStrValue = DateUtil.formatDate((Date) entry.getValue(), "yyyy-MM-dd HH:mm:ss");
                            values = values + "," + "to_timestamp('" + dateStrValue + "','yyyy-MM-dd HH24:MI:SS')";
                        }


                    } else {
                        values = values + "," + entry.getValue();
                    }
                }

            }
        }
        dtv.setKeys(keys);
        dtv.setValues(values);
        return dtv;
    }


}


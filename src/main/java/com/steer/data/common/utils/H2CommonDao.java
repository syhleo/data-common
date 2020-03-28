package com.steer.data.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.*;
//import java.sql.Connection;
import java.util.*;

public class H2CommonDao {
    /**
     * 建表方法
     *
     * @param tableName
     * @param items
     * @return
     * @throws SQLException
     */
    public static Boolean crateTable(String tableName, String[] items) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = H2ConnectionPool.getInstance().getConnection();
            DatabaseMetaData meta = conn.getMetaData();

            ResultSet rsTables = meta.getTables(null, null, tableName,
                    new String[]{"TABLE"});
            if (!rsTables.next()) {
                stmt = conn.createStatement();

                StringBuilder sql = new StringBuilder();
                sql.append(" CREATE TABLE ");
                if (StringUtils.isNotEmpty(tableName)) {
                    sql.append(tableName);
                }
                if (items != null && items.length > 0) {
                    sql.append(" ( ");
                    sql.append(" hid VARCHAR(1024), ");
                    for (int i = 0; i < items.length; i++) {
                        sql.append(items[i]);
                        sql.append(" VARCHAR(5000), ");
                    }
                    sql.append("PRIMARY KEY(hid)) ");
                }

                stmt.execute(sql.toString());
            }
            rsTables.close();
            return true;
        } finally {
            releaseConnection(conn, stmt, null);
        }
    }

    /**
     * h2数据库插入数据
     *
     * @param tableName
     * @param items
     * @param values
     * @return
     * @throws SQLException
     */
    public static Boolean insertH2(String tableName, String[] items, String[] values) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = H2ConnectionPool.getInstance().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO ");
            if (StringUtils.isNotEmpty(tableName)) {
                sql.append(tableName);
            }
            if (items != null && items.length > 0) {
                sql.append(" ( ");
                sql.append(" hid, ");
                String strItems = StringUtils.join(items, ",");
                sql.append(strItems);
                sql.append(" ) ");
                sql.append(" VALUES( ?,");
                for (int i = 0; i < items.length; i++) {
                    sql.append("? ");
                    if (i < items.length - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(") ");
            }

            stmt = conn
                    .prepareStatement(sql.toString());
            // values
            stmt.setString(1, StringUtil.getUUID());
            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    stmt.setString(i + 2, values[i]);
                }
            }
            return stmt.execute();
        } finally {
            conn.commit();
            releaseConnection(conn, stmt, rs);
        }
    }

    /**
     * 查询方法
     *
     * @param tableName
     * @param items
     * @param params
     * @return
     * @throws SQLException
     */
    public static List<Map<String, String>> selectH2(String tableName, String[] items, Map<String, String> params) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        try {
            conn = H2ConnectionPool.getInstance().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append(" Select ");
            if (items != null && items.length > 0) {
                String strItems = StringUtils.join(items, ",");
                sql.append(strItems);
            }
            sql.append(" FROM ");
            if (StringUtils.isNotEmpty(tableName)) {
                sql.append(tableName);
            }
            // 存在查询条件
            if (params != null && params.size() > 0) {

                sql.append(" WHERE ");
                Set<String> kSet = params.keySet();
                for (String key : kSet) {
                    sql.append(key);
                    sql.append(" = ? and ");
                }
                sql.append("1 = 1");
            }
            stmt = conn
                    .prepareStatement(sql.toString());

            // 存在查询条件
            if (params != null && params.size() > 0) {

                Set<String> kSet = params.keySet();
                Integer index = 1;
                for (String key : kSet) {
                    stmt.setString(index, params.get(key));
                    index++;
                }
            }
            rs = stmt.executeQuery();
            while (rs.next()) {

                Map<String, String> resultMap = new HashMap<String, String>();
                for (int i = 0; i < items.length; i++) {
                    resultMap.put(items[i], rs.getString(items[i]));
                }
                result.add(resultMap);
            }
            return result;
        } finally {
            releaseConnection(conn, stmt, rs);
        }
    }

    /**
     * 自定义sql
     *
     * @param sql
     * @param items
     * @param params
     * @return
     * @throws SQLException
     */
    public static List<Map<String, String>> selectH2BySql(String sql, List<String> items, List<String> params) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        try {
            conn = H2ConnectionPool.getInstance().getConnection();

            stmt = conn
                    .prepareStatement(sql);

            // 存在查询条件
            if (params != null && params.size() > 0) {
                for (int i = 0; i < params.size(); i++) {
                    stmt.setString(i + 1, params.get(i));
                }
            }
            rs = stmt.executeQuery();
            while (rs.next()) {

                Map<String, String> resultMap = new HashMap<String, String>();
                for (int i = 0; i < items.size(); i++) {
                    resultMap.put(items.get(i), rs.getString(items.get(i)));
                }
                result.add(resultMap);
            }
            return result;
        } finally {
            releaseConnection(conn, stmt, rs);
        }
    }

    private static void releaseConnection(Connection conn, Statement stmt,
                                          ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}


/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.db.datahandling.model.DataTransView.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月15日下午5:36:44
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


package com.steer.data.db.datahandling.model;


public class DataTransView {

    private String source_db;
    private String source_table_name;
    private String source_table_columns;
    private String source_table_params;
    private String target_db;
    private String target_table_name;

    //关联相应的数据库从表信息
    private String db_relate;

    public String getDb_relate() {
        return db_relate;
    }

    public void setDb_relate(String db_relate) {
        this.db_relate = db_relate;
    }

    private String keys;//字段名称  插入字段名称
    private String values;//字段值   插入字段值


    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getSource_db() {
        return source_db == null ? null : source_db.trim();
    }

    public void setSource_db(String source_db) {
        this.source_db = source_db;
    }

    public String getSource_table_name() {
        return source_table_name == null ? null : source_table_name.trim();
    }

    public void setSource_table_name(String source_table_name) {
        this.source_table_name = source_table_name;
    }

    public String getSource_table_columns() {
        return source_table_columns == null ? null : source_table_columns.trim();
    }

    public void setSource_table_columns(String source_table_columns) {
        this.source_table_columns = source_table_columns;
    }

    public String getSource_table_params() {
        return source_table_params == null ? null : source_table_params.trim();
    }

    public void setSource_table_params(String source_table_params) {
        this.source_table_params = source_table_params;
    }

    public String getTarget_db() {
        return target_db == null ? null : target_db.trim();
    }

    public void setTarget_db(String target_db) {
        this.target_db = target_db;
    }

    public String getTarget_table_name() {
        return target_table_name == null ? null : target_table_name.trim();
    }

    public void setTarget_table_name(String target_table_name) {
        this.target_table_name = target_table_name;
    }


}


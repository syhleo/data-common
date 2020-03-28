/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.db.datahandling.service.DbDataHandlingService.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月15日上午10:56:58
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


package com.steer.data.db.datahandling.service;

import com.steer.data.db.datahandling.model.DataTransView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface DbDataHandlingService {

    /**
     * @param source_db  源数据库名称   示例：datasource_slave1
     * @param table_name 数据库表名称
     * @param columns    数据库表中需要查询出来的字段，为空，表示查询所有，不为空以,分割 比如：coil_no,coil_no_vt
     * @param params     数据库表查询条件
     * @return
     * @throws Exception
     */
    List<HashMap> getDataByTableParams(String source_db, String table_name, String columns, String params) throws Exception;

    /**
     * @param table_name 数据库表名称
     * @param map
     * @param db_relate   关联相应的数据库主表/Map信息，即说明需要替换的字段和禁用的字段。
     * @return
     * @throws Exception
     */
    //public int insertTableData(String table_name,String keys,String values) throws Exception;
    //public int insertTableData(String table_name,Map<String,Object> map, String db_relate) throws Exception;
    int insertTableData(Map<String, Object> map, DataTransView dtView) throws Exception;

    int insertTableData2(Map<String, String> map, DataTransView dtView)
            throws Exception;

}


/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.db.datahandling.mapper.DbDataHandlingMapper.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月15日上午11:36:21
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


package com.steer.data.db.datahandling.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface DbDataHandlingMapper {

    /**
     *本项目配置了多数据源，此处需特别留意是哪个数据源在操作sql语句，在相应的Service实现类中指定，默认是multiple.datasource.master数据源，
     */

    /**
     * @param table_name 数据库表名称
     * @param columns    数据库表中需要查询出来的字段，为空，表示查询所有，不为空以,分割 比如：coil_no,coil_no_vt
     * @param params     数据库表查询条件
     * @return
     * @throws Exception
     */
    List<HashMap> getDataByTableParams(@Param("table_name") String table_name,
                                       @Param("columns") String columns, @Param("parames") String params) throws Exception;


    /**
     * @param table_name 数据库表名称
     * @param keys       数据库表字段
     * @param values     数据库表字段对应数值
     * @return 影响数据库的行数【在这里表示插入的记录数】
     * @throws Exception
     */
    int insertTableData(@Param("table_name") String table_name, @Param("keys") String keys,
                        @Param("parames") String values) throws Exception;

}


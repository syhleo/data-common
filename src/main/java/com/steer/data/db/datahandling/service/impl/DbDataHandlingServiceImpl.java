/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.db.datahandling.service.impl.DbDataHandlingServiceImpl.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月15日上午10:57:52
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


package com.steer.data.db.datahandling.service.impl;

import com.steer.data.common.utils.ObjectUtil;
import com.steer.data.db.config.DataSourceKey;
import com.steer.data.db.config.TargetDataSource;
import com.steer.data.db.datahandling.mapper.DbDataHandlingMapper;
import com.steer.data.db.datahandling.model.DataTransView;
import com.steer.data.db.datahandling.service.DbDataHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
//@Lazy
public class DbDataHandlingServiceImpl implements DbDataHandlingService {

    @Autowired
    private DbDataHandlingMapper dbDataHandlingMapper;

//	@Autowired
//	private DbMetadataDMapper dbMetadataDMapper;

    @Autowired
    DbMetadataDServiceImpl dbMetadataDService;

//	@Autowired
//	private DruidDataSource  druidDataSource;


    @Value("${multiple.datasource.master.driver-class-name}")
    String datasource_master;

    @Value("${multiple.datasource.slave1.driver-class-name}")
    String datasource_slave1;

    @Value("${multiple.datasource.slave2.driver-class-name}")
    String datasource_slave2;

    @Value("${multiple.datasource.slave3.driver-class-name}")
    String datasource_slave3;

    @Value("${multiple.datasource.slave4.driver-class-name}")
    String datasource_slave4;

    @Value("${multiple.datasource.slave5.driver-class-name}")
    String datasource_slave5;

    @Value("${multiple.datasource.slave6.driver-class-name}")
    String datasource_slave6;

    @Value("${multiple.datasource.slave7.driver-class-name}")
    String datasource_slave7;

    @Value("${multiple.datasource.slave8.driver-class-name}")
    String datasource_slave8;

    @Value("${multiple.datasource.other.driver-class-name}")
    String datasource_other;


    //默认datasource_master数据库
    @Override
    public List<HashMap> getDataByTableParams(String source_db, String table_name,
                                              String columns, String params) throws Exception {
        List<HashMap> datas = null;
        if (ObjectUtil.isEmpty(table_name)) {
            throw new Exception("数据库表名为空");
        }
        if (ObjectUtil.isEmpty(columns)) {
            columns = "*";
        }
        if (ObjectUtil.isEmpty(params)) {
            params = " and 1=1 ";
        } else {
            if (params.trim().length() < 3) {
                throw new Exception("查询不满足条件");
            } else {
                if (!params.trim().substring(0, 3).equalsIgnoreCase("and")) {
                    params = "and " + params;
                }
            }
        }
        try {
            datas = dbDataHandlingMapper.getDataByTableParams(table_name, columns, params);
        } catch (Exception ex) {
            throw new Exception("数据源【source_db】查询【" + table_name + "】表出错" + ex.getMessage());
        }
        return datas;
    }


    @TargetDataSource(dataSourceKey = DataSourceKey.DB_SLAVE1)
    public List<HashMap> getDataByTableParams_slave1(String source_db, String table_name,
                                                     String columns, String params) throws Exception {
        List<HashMap> datas = null;
        if (ObjectUtil.isEmpty(table_name)) {
            throw new Exception("数据库表名为空");
        }
        if (ObjectUtil.isEmpty(columns)) {
            columns = "*";
        }
        if (ObjectUtil.isEmpty(params)) {
            params = " and 1=1 ";
        } else {
            if (params.trim().length() < 3) {
                throw new Exception("查询不满足条件");
            } else {
                if (!params.trim().substring(0, 3).equalsIgnoreCase("and")) {
                    params = "and " + params;
                }
            }
        }
        try {
            datas = dbDataHandlingMapper.getDataByTableParams(table_name, columns, params);
        } catch (Exception ex) {
            throw new Exception("数据源【source_db】查询【" + table_name + "】表出错" + ex.getMessage());
        }
        return datas;
    }

    //默认datasource_master数据库
    @Override
    //@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    public int insertTableData(Map<String, Object> map, DataTransView dtView)
            throws Exception {
        int count = 0;
        DataTransView datas = new DataTransView();
        if (ObjectUtil.isEmpty(dtView.getTarget_table_name())) {
            throw new Exception("数据库表名为空");
        }
        if (ObjectUtil.isEmpty(datasource_master)) {
            throw new Exception("请配置master数据源");
        }
        //对需要替换的字段和禁用的字段进行处理。
        Map<String, Object> new_map = dbMetadataDService.changMapData(map, dtView.getDb_relate());


        if (datasource_slave1.contains("mysql")) {
            datas = ObjectUtil.getKeysValuesMysql(new_map);
        } else if (datasource_slave1.contains("oracle")) {
            datas = ObjectUtil.getKeysValuesOracle(new_map);
        } else if (datasource_slave1.contains("sqlserver")) {
            datas = ObjectUtil.getKeysValuesSqlServer(new_map);
        }
        if (ObjectUtil.isNotEmpty(datas.getKeys()) && ObjectUtil.isNotEmpty(datas.getValues())) {
            try {
                count = dbDataHandlingMapper.insertTableData(dtView.getTarget_table_name(), datas.getKeys(), datas.getValues());
            } catch (Exception ex) {
                throw new Exception("数据源【datasource_slave1】插入【" + dtView.getTarget_table_name() + "】表出错" + ex.getMessage());
            }
        }
        return count;
    }

    @Override
    public int insertTableData2(Map<String, String> map, DataTransView dtView)
            throws Exception {
        int count = 0;
        DataTransView datas = new DataTransView();
        if (ObjectUtil.isEmpty(dtView.getTarget_table_name())) {
            throw new Exception("数据库表名为空");
        }
        if (ObjectUtil.isEmpty(datasource_master)) {
            throw new Exception("请配置master数据源");
        }
        //对需要替换的字段和禁用的字段进行处理。
        //Map<String,Object> new_map=dbMetadataDService.changMapData(map,dtView.getDb_relate());
        Map<String, String> new_map = map;

        if (datasource_slave1.contains("mysql")) {
            //datas = ObjectUtil.getKeysValuesMysql(new_map);
        } else if (datasource_slave1.contains("oracle")) {
            datas = ObjectUtil.getKeysValuesOracle2(new_map);
        } else if (datasource_slave1.contains("sqlserver")) {
            //datas = ObjectUtil.getKeysValuesSqlServer(new_map);
        }
        if (ObjectUtil.isNotEmpty(datas.getKeys()) && ObjectUtil.isNotEmpty(datas.getValues())) {
            try {
                count = dbDataHandlingMapper.insertTableData(dtView.getTarget_table_name(), datas.getKeys(), datas.getValues());
            } catch (Exception ex) {
                throw new Exception("数据源【datasource_slave1】插入【" + dtView.getTarget_table_name() + "】表出错" + ex.getMessage());
            }
        }
        return count;
    }


    @TargetDataSource(dataSourceKey = DataSourceKey.DB_SLAVE1)
    public int insertTableData_salve1(Map<String, Object> map, DataTransView dtView)
            throws Exception {
        int count = 0;
        DataTransView datas = new DataTransView();
        if (ObjectUtil.isEmpty(dtView.getTarget_table_name())) {
            throw new Exception("数据库表名为空");
        }
        if (ObjectUtil.isEmpty(datasource_slave1)) {
            throw new Exception("请配置slave1数据源");
        }
        if (datasource_slave1.contains("mysql")) {
            datas = ObjectUtil.getKeysValuesMysql(map);
        } else if (datasource_slave1.contains("oracle")) {
            datas = ObjectUtil.getKeysValuesOracle(map);
        } else if (datasource_slave1.contains("sqlserver")) {
            datas = ObjectUtil.getKeysValuesSqlServer(map);
        }
        if (ObjectUtil.isNotEmpty(datas.getKeys()) && ObjectUtil.isNotEmpty(datas.getValues())) {
            try {
                count = dbDataHandlingMapper.insertTableData(dtView.getTarget_table_name(), datas.getKeys(), datas.getValues());
            } catch (Exception ex) {
                throw new Exception("数据源【datasource_slave1】插入【" + dtView.getTarget_table_name() + "】表出错" + ex.getMessage());
            }
        }
        return count;
    }


}


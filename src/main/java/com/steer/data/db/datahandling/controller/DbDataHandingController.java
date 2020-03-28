/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.db.datahandling.controller.DbDataHandingController.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月15日上午10:52:53
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


package com.steer.data.db.datahandling.controller;

import com.steer.data.common.result.ResultModel;
import com.steer.data.common.utils.ObjectUtil;
import com.steer.data.db.datahandling.model.DataTransView;
import com.steer.data.db.datahandling.service.impl.DbDataHandlingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/${api.version}/DbDataHanding")
public class DbDataHandingController {

    @Autowired
    DbDataHandlingServiceImpl dbDataHandlingService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/datatrans/")
    public ResultModel dataTrans(@RequestBody DataTransView dataTrans) {
        try {
            if (ObjectUtil.isEmpty(dataTrans)) {
                return ResultModel.error("请输入参数");
            }
            if (ObjectUtil.isEmpty(dataTrans.getSource_db())) {
                return ResultModel.error("源数据库名称为空");
            }
            if (ObjectUtil.isEmpty(dataTrans.getTarget_db())) {
                return ResultModel.error("目标数据库名称为空");
            }
            if (ObjectUtil.isEmpty(dataTrans.getSource_table_name())) {
                return ResultModel.error("源数据库表名称为空");
            }
            if (ObjectUtil.isEmpty(dataTrans.getTarget_table_name())) {
                return ResultModel.error("目标数据库表名称为空");
            }
            logger.info("数据传输：from " + dataTrans.getSource_db() + "[" + dataTrans.getSource_table_name() + "] to " + dataTrans.getTarget_db() + "[" + dataTrans.getTarget_table_name() + "]");
            List<HashMap> mapList = new ArrayList<>();
            if (dataTrans.getSource_db().equals("datasource_master")) {
                mapList = dbDataHandlingService.getDataByTableParams(dataTrans.getSource_db(), dataTrans.getSource_table_name(), dataTrans.getSource_table_columns(),
                        dataTrans.getSource_table_params());
            } else if (dataTrans.getSource_db().equals("datasource_slave1")) {
                mapList = dbDataHandlingService.getDataByTableParams_slave1(dataTrans.getSource_db(), dataTrans.getSource_table_name(), dataTrans.getSource_table_columns(),
                        dataTrans.getSource_table_params());
            } else if (dataTrans.getSource_db().equals("datasource_slave2")) {

            } else if (dataTrans.getSource_db().equals("datasource_slave3")) {

            } else if (dataTrans.getSource_db().equals("datasource_slave4")) {

            } else if (dataTrans.getSource_db().equals("datasource_slave5")) {

            } else if (dataTrans.getSource_db().equals("datasource_slave6")) {

            } else if (dataTrans.getSource_db().equals("datasource_slave7")) {

            } else if (dataTrans.getSource_db().equals("datasource_slave8")) {

            } else if (dataTrans.getSource_db().equals("datasource_other")) {

            }

            for (HashMap map : mapList) {
                if (dataTrans.getTarget_db().equals("datasource_master")) {
                    dbDataHandlingService.insertTableData(map, dataTrans);
                } else if (dataTrans.getTarget_db().equals("datasource_slave1")) {
                    dbDataHandlingService.insertTableData_salve1(map, dataTrans);
                } else if (dataTrans.getTarget_db().equals("datasource_slave2")) {

                } else if (dataTrans.getTarget_db().equals("datasource_slave3")) {

                } else if (dataTrans.getTarget_db().equals("datasource_slave4")) {

                } else if (dataTrans.getTarget_db().equals("datasource_slave5")) {

                } else if (dataTrans.getTarget_db().equals("datasource_slave6")) {

                } else if (dataTrans.getSource_db().equals("datasource_slave7")) {

                } else if (dataTrans.getSource_db().equals("datasource_slave8")) {

                } else if (dataTrans.getTarget_db().equals("datasource_other")) {

                }
            }
            return ResultModel.success("数据传输成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.error(e.getMessage());
        }

    }


    @PostMapping("/dataHeart/")
    public ResultModel dataHeart(@RequestBody DataTransView dataTrans) {
        try {
            if (ObjectUtil.isEmpty(dataTrans)) {
                return ResultModel.error("请输入参数");
            }
            if (ObjectUtil.isEmpty(dataTrans.getSource_db())) {
                return ResultModel.error("源数据库名称为空");
            }
            if (ObjectUtil.isEmpty(dataTrans.getSource_table_name())) {
                return ResultModel.error("源数据库表名称为空");
            }
            logger.info("数据库心跳监测： " + dataTrans.getSource_db() + "[" + dataTrans.getSource_table_name() + "]");
            List<HashMap> mapList = null;
            if (dataTrans.getSource_db().equals("datasource_master")) {
                mapList = dbDataHandlingService.getDataByTableParams(dataTrans.getSource_db(), dataTrans.getSource_table_name(), dataTrans.getSource_table_columns(),
                        dataTrans.getSource_table_params());
            } else if (dataTrans.getSource_db().equals("datasource_slave1")) {
                mapList = dbDataHandlingService.getDataByTableParams_slave1(dataTrans.getSource_db(), dataTrans.getSource_table_name(), dataTrans.getSource_table_columns(),
                        dataTrans.getSource_table_params());
            } else if (dataTrans.getSource_db().equals("datasource_slave2")) {

            } else if (dataTrans.getSource_db().equals("datasource_slave3")) {

            } else if (dataTrans.getSource_db().equals("datasource_slave4")) {

            } else if (dataTrans.getSource_db().equals("datasource_slave5")) {

            } else if (dataTrans.getSource_db().equals("datasource_slave6")) {

            } else if (dataTrans.getSource_db().equals("datasource_slave7")) {

            } else if (dataTrans.getSource_db().equals("datasource_slave8")) {

            } else if (dataTrans.getSource_db().equals("datasource_other")) {

            }

            return ResultModel.success("数据库心跳监测成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.error(e.getMessage());
        }

    }


}


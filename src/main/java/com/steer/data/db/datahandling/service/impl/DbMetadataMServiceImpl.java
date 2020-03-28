package com.steer.data.db.datahandling.service.impl;

import com.steer.data.common.utils.ObjectUtil;
import com.steer.data.db.datahandling.mapper.DbMetadataMMapper;
import com.steer.data.db.datahandling.model.DataTransView;
import com.steer.data.db.datahandling.model.DbMetadataM;
import com.steer.data.db.datahandling.service.DbMetadataMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * <p>
 * 数据库-元数据主表信息 服务实现类
 * </p>
 *
 * @author syhleo
 * @since 2019-09-16
 */
@Service
public class DbMetadataMServiceImpl implements DbMetadataMService {

    @Autowired
    DbDataHandlingServiceImpl dbDataHandlingService;

    @Autowired
    private DbMetadataMMapper dbMetadataMMapper;

    public String doWork(List<String> db_ids) throws Exception {
        String str = null;
        //根据db_ids查询DbMetadata对象。
        for (String db_id : db_ids) {
            //DbMetadataM dbMetadataM = dbMetadataMMapper.selectById(Integer.parseInt(db_id));没有排除有效标识VALID_FLG，默认是1，（1:有效，0:无效）

//			QueryWrapper<DbMetadataM> wrapper = new QueryWrapper<DbMetadataM>();
//			wrapper.eq("DB_ID", Integer.parseInt(db_id))
//			.eq("VALID_FLG", "1");
//			DbMetadataM dbMetadataM = dbMetadataMMapper.selectOne(wrapper);
            DbMetadataM dbMetadataM = dbMetadataMMapper.findByIdValid(Integer.parseInt(db_id));


            List<HashMap> mapList = new ArrayList<>();
            //根据db_id获取源头数据
            if (dbMetadataM.getSourceDb().equals("datasource_master")) {
                mapList = dbDataHandlingService.getDataByTableParams(dbMetadataM.getSourceDb(), dbMetadataM.getSourceTableName(), dbMetadataM.getSourceTableColumns(),
                        dbMetadataM.getSourceTableParams());
            } else if (dbMetadataM.getSourceDb().equals("datasource_slave1")) {
                mapList = dbDataHandlingService.getDataByTableParams_slave1(dbMetadataM.getSourceDb(), dbMetadataM.getSourceTableName(), dbMetadataM.getSourceTableColumns(),
                        dbMetadataM.getSourceTableParams());
            } else if (dbMetadataM.getSourceDb().equals("datasource_slave2")) {

            } else if (dbMetadataM.getSourceDb().equals("datasource_slave3")) {

            } else if (dbMetadataM.getSourceDb().equals("datasource_slave4")) {

            } else if (dbMetadataM.getSourceDb().equals("datasource_slave5")) {

            } else if (dbMetadataM.getSourceDb().equals("datasource_slave6")) {

            } else if (dbMetadataM.getSourceDb().equals("datasource_slave7")) {

            } else if (dbMetadataM.getSourceDb().equals("datasource_slave8")) {

            } else if (dbMetadataM.getSourceDb().equals("datasource_other")) {

            }

            /**
             * 根据目标数据库【可能为空】，http_ids，ws_ids等，确定目标对象操作。
             */

            //如果目标数据库不为空
            if (ObjectUtil.isNotEmpty(dbMetadataM.getTargetDb()) && ObjectUtil.isNotEmpty(dbMetadataM.getTargetTableName())) {
                for (HashMap map : mapList) {
                    DataTransView dtv = new DataTransView();
                    dtv.setTarget_db(dbMetadataM.getTargetDb());
                    dtv.setTarget_table_name(dbMetadataM.getTargetTableName());
                    dtv.setDb_relate(dbMetadataM.getDbRelate());
                    if (dbMetadataM.getTargetDb().equals("datasource_master")) {
                        dbDataHandlingService.insertTableData(map, dtv);
                    } else if (dbMetadataM.getTargetDb().equals("datasource_slave1")) {
                        dbDataHandlingService.insertTableData_salve1(map, dtv);
                    } else if (dbMetadataM.getTargetDb().equals("datasource_slave2")) {

                    } else if (dbMetadataM.getTargetDb().equals("datasource_slave3")) {

                    } else if (dbMetadataM.getTargetDb().equals("datasource_slave4")) {

                    } else if (dbMetadataM.getTargetDb().equals("datasource_slave5")) {

                    } else if (dbMetadataM.getTargetDb().equals("datasource_slave6")) {

                    } else if (dbMetadataM.getSourceDb().equals("datasource_slave7")) {

                    } else if (dbMetadataM.getSourceDb().equals("datasource_slave8")) {

                    } else if (dbMetadataM.getTargetDb().equals("datasource_other")) {

                    }
                }
            }
            //如果http_ids不为空
            if (ObjectUtil.isNotEmpty(dbMetadataM.getHttpIds())) {
                List<String> strList = new ArrayList<>();
                if (dbMetadataM.getHttpIds().contains(",")) {
                    strList = Arrays.asList(dbMetadataM.getHttpIds().split(","));
                    //dbMetadataMService.doWork(strList);
                    //http接口方法
                } else {
                    strList.add(dbMetadataM.getHttpIds());
                    //dbMetadataMService.doWork(strList);
                    //http接口方法
                }
            }
            //如果ws_ids不为空
            if (ObjectUtil.isNotEmpty(dbMetadataM.getWsIds())) {
                List<String> strList = new ArrayList<>();
                if (dbMetadataM.getWsIds().contains(",")) {
                    strList = Arrays.asList(dbMetadataM.getWsIds().split(","));
                    //dbMetadataMService.doWork(strList);
                    //WebService接口方法
                } else {
                    strList.add(dbMetadataM.getWsIds());
                    //dbMetadataMService.doWork(strList);
                    //WebService接口方法
                }
            }
            //如果tcp_ids不为空
            if (ObjectUtil.isNotEmpty(dbMetadataM.getTcpIds())) {
                List<String> strList = new ArrayList<>();
                if (dbMetadataM.getTcpIds().contains(",")) {
                    strList = Arrays.asList(dbMetadataM.getTcpIds().split(","));
                    //dbMetadataMService.doWork(strList);
                    //Tcp接口方法
                } else {
                    strList.add(dbMetadataM.getTcpIds());
                    //dbMetadataMService.doWork(strList);
                    //Tcp接口方法
                }
            }


        }
        //

        return str;
    }

    public Object listAll() {
//		//List<DbMetadataM> dbmdList = dbMetadataMMapper.selectList(null);
//		for(DbMetadataM dbmd:dbmdList){
//			System.out.println(dbmd);
//		}
        return null;
    }


}

package com.steer.data.quartz.service.impl;

import com.steer.data.common.annotation.NeedCacheAop;
import com.steer.data.common.utils.ObjectUtil;
import com.steer.data.db.datahandling.service.impl.DbMetadataMServiceImpl;
import com.steer.data.db.datahandling.service.impl.SqlHandlerServiceImpl;
import com.steer.data.quartz.mapper.QuartzJobEntityMapper;
import com.steer.data.quartz.model.QuartzJobEntity;
import com.steer.data.quartz.service.QuartzJobEntityService;
import com.steer.data.udp.CustomLed.LedWriteClient;
import com.steer.data.webservice.datahandling.service.impl.WebServiceDataHandlingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * quartz定时器动态执行的任务信息 服务实现类
 * </p>
 *
 * @author syhleo
 * @since 2019-09-16
 */
@Service
public class QuartzJobEntityServiceImpl implements QuartzJobEntityService {

    @Autowired
    DbMetadataMServiceImpl dbMetadataMService;
    @Autowired
    WebServiceDataHandlingServiceImpl webServiceDataHandlingService;

    @Autowired
    SqlHandlerServiceImpl sqlHandlerService;

    @Autowired
    LedWriteClient ledWriteClient;

    @Autowired
    private QuartzJobEntityMapper jobEntityMapper;

    /**
     * @param db_ids
     * @param tcp_ids
     * @param ws_ids
     * @param http_ids
     * @param udp_ids
     */
    public void doWorkJob(String db_ids, String tcp_ids, String ws_ids,
                          String http_ids, String udp_ids) {
        if (ObjectUtil.isNotEmpty(db_ids)) {//执行DB操作
            try {
                List<String> strList = new ArrayList<>();
                if (db_ids.contains(",")) {
                    strList = Arrays.asList(db_ids.split(","));
                    dbMetadataMService.doWork(strList);
                } else {
                    strList.add(db_ids);
                    dbMetadataMService.doWork(strList);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (ObjectUtil.isNotEmpty(ws_ids)) {//执行WebService操作
            try {
                List<String> strList = new ArrayList<>();
                if (ws_ids.contains(",")) {
                    strList = Arrays.asList(ws_ids.split(","));
                    webServiceDataHandlingService.doWork(strList);
                } else {
                    strList.add(ws_ids);
                    webServiceDataHandlingService.doWork(strList);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (ObjectUtil.isNotEmpty(udp_ids)) {//执行Udp通讯操作
            try {
                List<String> strList = new ArrayList<>();
                if (udp_ids.contains(",")) {
                    strList = Arrays.asList(udp_ids.split(","));
                    ledWriteClient.doWork(strList);
                } else {
                    strList.add(udp_ids);
                    ledWriteClient.doWork(strList);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void doWorkJob(String other_ids) {
        if("1".equals(other_ids)){
            sqlHandlerService.doWork(null);
        }
    }

    //@NeedCacheAop
    @NeedCacheAop(nxxx = "nx",time =1*60)
    @Override
    public List<QuartzJobEntity> list() {
        System.out.println("QuartzJobEntityServiceImpl的list方法。。。");
        //查询所有
        return jobEntityMapper.findAll();
    }
    @Override
    public List<QuartzJobEntity> listForModel(QuartzJobEntity quartzJobEntity) {
        return null;
    }
    @Override
    public List<QuartzJobEntity> listForName(String jobName) {
        return jobEntityMapper.findByJobName(jobName);
    }


}

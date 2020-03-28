package com.steer.data.quartz.jobstore.ram;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class RejectJob implements Job {
    //	ApplicationContextProvider acp = new ApplicationContextProvider();
//	IfSlit01PdoRejectServiceImpl ifSlit01PdoRejectServiceImpl = acp
//			.getBean(IfSlit01PdoRejectServiceImpl.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Log log = LogFactory.getLog(getClass());
            log.info("RejectJob开始" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
            //ifSlit01PdoRejectServiceImpl.selectIfSlit01PdoReject();
            String leo = "";
            for (int i = 0; i < 134; i++) {
                for (int j = 0; j < 400; j++) {
                    leo = leo + "leoleo" + i + j;
                }
            }
            log.info("RejectJob任务执行" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
            log.info("RejectJob结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

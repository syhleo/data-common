package com.steer.data.quartz.jobstore.ram;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CoilJob implements Job {
//	ApplicationContextProvider acp = new ApplicationContextProvider();
//	IfSlit01PdoCoilsServiceImpl iIfSlit01PdoCoilsService = acp
//			.getBean(IfSlit01PdoCoilsServiceImpl.class);

    @Override
    public void execute(JobExecutionContext executorContext) throws JobExecutionException {
        try {
            //iIfSlit01PdoCoilsService.selectIfSlit01PdoCoils();
            Log log = LogFactory.getLog(getClass());
            log.info("CoilJob任务执行" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
//		    while(true){
//		    	
//		    }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

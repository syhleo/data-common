package com.steer.data.quartz.jobstore.ram;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ShiftJob implements Job {
    //	ApplicationContextProvider acp = new ApplicationContextProvider();
//	IfSlit01PdoShiftServiceImpl ifSlit01PdoShiftServiceImpl = acp
//			.getBean(IfSlit01PdoShiftServiceImpl.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            //ifSlit01PdoShiftServiceImpl.selectIfSlit01PdoShift();
            Log log = LogFactory.getLog(getClass());
            log.info("ShiftJob任务执行" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
            //int i=1/0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

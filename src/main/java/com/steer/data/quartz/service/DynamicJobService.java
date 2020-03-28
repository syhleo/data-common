package com.steer.data.quartz.service;

import com.steer.data.quartz.job.DynamicJob;
import com.steer.data.quartz.mapper.QuartzJobEntityMapper;
import com.steer.data.quartz.model.QuartzJobEntity;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//方式一
//方式二
//import org.quartz.*;

/**
 * Created by syhleo on 2019/9/16
 */
@Service
public class DynamicJobService {

    @Autowired
    private QuartzJobEntityMapper quartzJobEntityMapper;

    //通过Id获取Job
    public QuartzJobEntity getQuartzJobEntityById(Integer id) {
        return quartzJobEntityMapper.findByJobId(id);
    }

    //从数据库中加载获取到所有Job
    public List<QuartzJobEntity> loadJobs() {
        return quartzJobEntityMapper.findAll();
    }

    //获取JobDataMap.(Job参数对象)
    public JobDataMap getJobDataMap(QuartzJobEntity job) {
        JobDataMap map = new JobDataMap();
        map.put("name", job.getName());
        map.put("jobGroup", job.getJobGroup());
        map.put("cronExpression", job.getCron());
        map.put("job_param", job.getJobParam());
        map.put("jobDescription", job.getJobDesc());
        map.put("vmParam", job.getVmParam());
        map.put("jarPath", job.getJarPath());
        map.put("status", job.getStatus());
        map.put("db_ids", job.getDbIds());
        map.put("tcp_ids", job.getTcpIds());
        map.put("ws_ids", job.getWsIds());
        map.put("http_ids", job.getHttpIds());
        map.put("udp_ids", job.getUdpIds());
        map.put("other_ids", job.getOtherIds());
        map.put("job_times", job.getJobTimes() + "");

        return map;
    }

    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
    public JobDetail getJobDetail(JobKey jobKey, String description, JobDataMap map) {
        return JobBuilder.newJob(DynamicJob.class)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }

    //获取Trigger (Job的触发器,执行规则)
    public Trigger getTrigger(QuartzJobEntity job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getName(), job.getJobGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();
    }

    //获取JobKey,包含Name和Group
    public JobKey getJobKey(QuartzJobEntity job) {
        return JobKey.jobKey(job.getName(), job.getJobGroup());
    }
}

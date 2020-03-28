package com.steer.data.quartz.web;

import ch.qos.logback.classic.Logger;
import com.steer.data.quartz.mapper.QuartzJobEntityMapper;
import com.steer.data.quartz.model.QuartzJobEntity;
import com.steer.data.quartz.service.DynamicJobService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

/**
 * Created by EalenXie on 2018/6/4 16:12
 * PostConstruct 注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化。此方法必须在将类放入服务之前调用。
 */
@RestController
@RequestMapping("/${api.version}/Job")
@ConditionalOnExpression("#{'true'.equals(environment['quartz.enabled'])}")
public class JobController {

    Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private DynamicJobService jobService;

    @Autowired
    private QuartzJobEntityMapper jobEntityMapper;

    //初始化启动所有的Job
    @PostConstruct
    public void initialize() {
        try {
            log.info("初始化启动所有的Job ... ");
            reStartAllJobs();
            log.info("init success");
        } catch (SchedulerException e) {
            log.error("printStackTrace ", e);
        }
    }

    //根据ID重启某个Job
    @RequestMapping("/refresh/{id}")
    public String refresh(@PathVariable @NotNull Integer id) throws SchedulerException {
        String result;
        QuartzJobEntity entity = jobService.getQuartzJobEntityById(id);
        if (Objects.isNull(entity)) return "error: id is not exist ";
        synchronized (log) {
            log.info("根据ID为{" + id + "}重启指定Job");
            JobKey jobKey = jobService.getJobKey(entity);
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.pauseJob(jobKey);
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
            scheduler.deleteJob(jobKey);
            JobDataMap map = jobService.getJobDataMap(entity);
            JobDetail jobDetail = jobService.getJobDetail(jobKey, entity.getJobDesc(), map);
            if (entity.getStatus().equals("OPEN")) {
                scheduler.scheduleJob(jobDetail, jobService.getTrigger(entity));
                result = "Refresh Job : " + entity.getName() + "\t job_id: " + entity.getJobId() + " success !";
            } else {
                result = "Refresh Job : " + entity.getName() + "\t job_id: " + entity.getJobId() + " failed ! , " +
                        "Because the Job status is " + entity.getStatus();
            }
        }
        return result;
    }


    //重启数据库中所有的Job
    @RequestMapping("/refresh/all")
    public String refreshAll() {
        String result;
        try {
            reStartAllJobs();
            result = "success";
        } catch (SchedulerException e) {
            result = "exception : " + e.getMessage();
        }
        return "refresh all jobs : " + result;
    }

    /**
     * 重新启动所有的job
     */
    private void reStartAllJobs() throws SchedulerException {
        synchronized (log) {                                                         //只允许一个线程进入操作   未验证
            log.info("重启数据库中所有的Job");
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //会提示错误，Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
            Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
            //会提示错误，scheduler.pauseJobs(GroupMatcher.anyGroup());                     //暂停所有JOB
            scheduler.pauseJobs(GroupMatcher.anyJobGroup());                               //暂停所有JOB
            for (JobKey jobKey : set) {                                                 //删除从数据库中注册的所有JOB
                scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                scheduler.deleteJob(jobKey);
            }
            for (QuartzJobEntity job : jobService.loadJobs()) {                               //从数据库中注册的所有JOB
                log.info("Job register name : {} , group : {} , cron : {}", job.getName(), job.getJobGroup(), job.getCron());
                JobDataMap map = jobService.getJobDataMap(job);
                JobKey jobKey = jobService.getJobKey(job);
                JobDetail jobDetail = jobService.getJobDetail(jobKey, job.getJobDesc(), map);
                if (job.getStatus().equals("OPEN")) {
                    scheduler.scheduleJob(jobDetail, jobService.getTrigger(job));
                } else {
                    log.info("Job jump name : {} , Because {} status is {}", job.getName(), job.getName(), job.getStatus());
                }

            }
        }
    }

    //修改某个Job执行的Cron，可以扩展为修改Job的Cron,job_times等
    @PostMapping("/modifyJob")
    public String modifyJob(@RequestBody QuartzJobEntity dto) {
        if (!CronExpression.isValidExpression(dto.getCron())) return "cron is invalid !";
        synchronized (log) {
            QuartzJobEntity job = jobService.getQuartzJobEntityById(dto.getJobId());
            if (job.getStatus().equals("OPEN")) {
                try {
                    JobKey jobKey = jobService.getJobKey(job);
                    TriggerKey triggerKey = new TriggerKey(jobKey.getName(), jobKey.getGroup());
                    Scheduler scheduler = schedulerFactoryBean.getScheduler();
                    CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                    String oldCron = cronTrigger.getCronExpression();
                    if (!oldCron.equalsIgnoreCase(dto.getCron())) {
                        job.setCron(dto.getCron());
                        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(dto.getCron());
                        CronTrigger trigger = TriggerBuilder.newTrigger()
                                .withIdentity(jobKey.getName(), jobKey.getGroup())
                                .withSchedule(cronScheduleBuilder)
                                .usingJobData(jobService.getJobDataMap(job))
                                .build();
                        scheduler.rescheduleJob(triggerKey, trigger);
                        jobEntityMapper.updateByJobId(job);
                    }
                } catch (Exception e) {
                    log.error("printStackTrace", e);
                }
            } else {
                log.info("Job jump name : {} , Because {} status is {}", job.getName(), job.getName(), job.getStatus());
                return "modify failure , because the job is closed";
            }
        }
        return "modify success";
    }


}

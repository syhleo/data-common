package com.steer.data.quartz.jobstore.ram;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.DirectSchedulerFactory;

/**
 * 创建任务调度，并执行
 *
 * @author
 */
public class Task {
    //创建调度器
    public static Scheduler getScheduler() throws SchedulerException {
        DirectSchedulerFactory.getInstance().createVolatileScheduler(1);
        SchedulerFactory schedulerFactory = DirectSchedulerFactory.getInstance();
        return schedulerFactory.getScheduler();
    }

    public static void schedulerJob() throws SchedulerException {

        Scheduler scheduler = getScheduler();
        // 创建任务
        JobBuilder coilJob = JobBuilder.newJob(CoilJob.class);
        //创建触发器，每一分钟执行一次
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/59 * * * * ?")).build();
        JobDetail detail = coilJob.withIdentity("job1", "group1").build();

        // 将任务及其触发器放入调度器
        scheduler.scheduleJob(detail, trigger);

        // 调度器开始调度任务
        scheduler.start();

    }

}

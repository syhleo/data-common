package com.steer.data.quartz.jobstore.ram;

import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

/**
 * https://www.jb51.net/article/132983.htm
 *
 * @author syhleo
 */
@Component
public class SchedulerAllJob {

    // 创建调度器
    public static Scheduler getScheduler1() throws SchedulerException {
        StdSchedulerFactory sf = new StdSchedulerFactory();
        Properties props = new Properties();
        props.put("org.quartz.scheduler.instanceName", "COIL：");
        props.put("org.quartz.threadPool.threadCount", "1");//#必填
        sf.initialize(props);
        return sf.getScheduler();
        //System.out.println(scheduler.getSchedulerName());
        //scheduler.shutdown();
    }

    // 创建调度器
    public static Scheduler getScheduler2() throws SchedulerException {
        StdSchedulerFactory sf = new StdSchedulerFactory();
        Properties props = new Properties();
        props.put("org.quartz.scheduler.instanceName", "REJECT：");
        props.put("org.quartz.threadPool.threadCount", "1");//#必填
        sf.initialize(props);
        return sf.getScheduler();
        //System.out.println(scheduler.getSchedulerName());
        //scheduler.shutdown();
    }

    // 创建调度器
    public static Scheduler getScheduler3() throws SchedulerException {
        StdSchedulerFactory sf = new StdSchedulerFactory();
        Properties props = new Properties();
        props.put("org.quartz.scheduler.instanceName", "SHIFT：");
        props.put("org.quartz.threadPool.threadCount", "1");//#必填
        sf.initialize(props);
        return sf.getScheduler();
        //System.out.println(scheduler.getSchedulerName());
        //scheduler.shutdown();
    }

    /**
     * 该方法用来启动所有的定时任务
     *
     * @throws SchedulerException
     */
    public void scheduleJobs() throws SchedulerException {

        Scheduler scheduler1 = getScheduler1();
        Scheduler scheduler2 = getScheduler2();
        Scheduler scheduler3 = getScheduler3();
        scheduleJob1(scheduler1);
        scheduleJob2(scheduler2);
        scheduleJob3(scheduler3);
        // 调度器开始调度任务
        scheduler1.start();
        scheduler2.start();
        scheduler3.start();
    }

    /**
     * 配置Job1 此处的任务可以配置可以放到properties或者是放到数据库中
     * 如果此时需要做到动态的定时任务，请参考：http://blog.csdn.net/liuchuanhong1/article/details/
     * 60873295 博客中的ScheduleRefreshDatabase类
     *
     * @param scheduler
     * @throws SchedulerException
     */
    private void scheduleJob1(Scheduler scheduler) throws SchedulerException {
        /*
         * 此处可以先通过任务名查询数据库，如果数据库中存在该任务，则按照ScheduleRefreshDatabase类中的方法，
         * 更新任务的配置以及触发器 如果此时数据库中没有查询到该任务，则按照下面的步骤新建一个任务，并配置初始化的参数，并将配置存到数据库中
         */
        JobDetail jobDetail = JobBuilder.newJob(CoilJob.class).withIdentity("job1", "group1").build();
        // 每5s执行一次
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    /**
     * 配置Job
     *
     * @param scheduler
     * @throws SchedulerException
     */
    private void scheduleJob2(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(RejectJob.class).withIdentity("job2", "group2").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group2")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    /**
     * 配置Job
     *
     * @param scheduler
     * @throws SchedulerException
     */
    private void scheduleJob3(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ShiftJob.class).withIdentity("job3", "group3").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger3", "group3")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

}
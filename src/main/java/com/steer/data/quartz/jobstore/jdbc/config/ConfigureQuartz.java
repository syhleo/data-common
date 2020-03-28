package com.steer.data.quartz.jobstore.jdbc.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Created by syhleo
 * Quartz的核心配置类
 * 分布式定时任务管理配置
 */
@Configuration
//@ConditionalOnExpression("${quartz.enabled:true}")     //当配置文件没有quartz.enabled说明时，也会执行下面的代码。
@ConditionalOnExpression("#{'true'.equals(environment['quartz.enabled'])}")
//当配置文件没有quartz.enabled说明时，不会执行下面的代码。即只有quartz.enabled=true才会执行下面的代码
public class ConfigureQuartz {


//	@Autowired
//	DataSource dataSource; 

    private static final String QUARTZ_DATASOURCE_PREFIX = "spring.datasource.druid.quartz";

    //配置JobFactory
    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    /**
     * SchedulerFactoryBean这个类的真正作用提供了对org.quartz.Scheduler的创建与配置，并且会管理它的生命周期与Spring同步。
     * org.quartz.Scheduler: 调度器。所有的调度都是由它控制。
     *
     * @param dataSource 为SchedulerFactory配置数据源
     * @param jobFactory 为SchedulerFactory配置JobFactory
     *                   注意，此方法可以用来启动quartz数据源和应用的数据源一致
     *                   即数据源还是用的我们应用的数据源，springboot自动将我们应用的数据源配置给了quartz。
     */
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory) throws IOException {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        //可选,QuartzScheduler启动时更新己存在的Job,这样就不用每次修改targetObject后删除qrtz_job_details表对应记录
//        factory.setOverwriteExistingJobs(true);
//        factory.setAutoStartup(true); //设置自行启动
//        factory.setDataSource(dataSource);
//        factory.setJobFactory(jobFactory);
//        //决定是否启用quartz.properties文件里面配置信息
//        factory.setQuartzProperties(quartzProperties());
//        return factory;
//    }

//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
//    	System.out.println("SchedulerFactoryBean初始化...");
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        //可选,QuartzScheduler启动时更新己存在的Job,这样就不用每次修改targetObject后删除qrtz_job_details表对应记录
//        factory.setOverwriteExistingJobs(true);
//        factory.setAutoStartup(true); //设置自行启动
//        //factory.setJobFactory(jobFactory);
//        //决定是否启用quartz.properties文件里面配置信息
//        //factory.setQuartzProperties(quartzProperties());
//        return factory;
//    }

//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setQuartzProperties(quartzProperties());
//        return factory;
//    }
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) throws IOException {
        System.out.println("SchedulerFactoryBean初始化...");
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //用于quartz集群,可选,QuartzScheduler启动时更新己存在的Job,这样就不用每次修改targetObject后删除qrtz_job_details表对应记录
        factory.setOverwriteExistingJobs(true);
        factory.setStartupDelay(2); //延时启动，应用启动2秒后,即项目启动完成后，等待2秒后开始执行调度器初始化
        factory.setAutoStartup(true); //设置自行启动，即设置调度器自动运行
        factory.setJobFactory(jobFactory);
        factory.setDataSource(quartzDataSource());
        //决定是否启用quartzProperties文件里面配置信息
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    /**
     * @QuartzDataSource 注解则是配置Quartz独立数据源的配置
     */
    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = QUARTZ_DATASOURCE_PREFIX)
    public DataSource quartzDataSource() {
        System.out.println("quartzDruidDataSource初始化...");
        return new DruidDataSource();
    }


    //从quartz.properties文件中读取Quartz配置属性
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/application-quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }


    //配置JobFactory,为quartz作业添加自动连接支持
    public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements
            ApplicationContextAware {
        private AutowireCapableBeanFactory beanFactory;

        @Override
        public void setApplicationContext(final ApplicationContext context) {
            beanFactory = context.getAutowireCapableBeanFactory();
        }

        @Override
        protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
            final Object job = super.createJobInstance(bundle);
            // 进行注入 ，这一步解决不能spring注入bean的问题  
            beanFactory.autowireBean(job);
            return job;
        }
    }

}

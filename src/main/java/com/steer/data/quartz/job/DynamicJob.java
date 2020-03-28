package com.steer.data.quartz.job;

import ch.qos.logback.classic.Logger;
import com.steer.data.common.utils.StringUtil;
import com.steer.data.quartz.service.impl.QuartzJobEntityServiceImpl;
import org.quartz.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by syhleo on 2019/9/10 14:29    Quartz 线程处理有并行执行与串行执行
 * :@DisallowConcurrentExecution : 此标记用在实现Job的类上面,意思是不允许并发执行，即禁止并发执行.
 * :注意org.quartz.threadPool.threadCount线程池中线程的数量至少要多个,否则@DisallowConcurrentExecution不生效
 * :假如Job的设置时间间隔为3秒,但Job执行时间是5秒,设置@DisallowConcurrentExecution以后程序会等任务执行完毕以后再去执行,否则会在3秒时再启用新的线程执行
 */
@DisallowConcurrentExecution
@Component
public class DynamicJob implements Job {

    Logger log = (Logger) LoggerFactory.getLogger(this.getClass());

    @Autowired
    QuartzJobEntityServiceImpl quartzJobService;


    /**
     * 核心方法,Quartz Job真正的执行逻辑.
     *
     * @param executorContext executorContext JobExecutionContext中封装有Quartz运行所需要的所有信息
     * @throws JobExecutionException execute()方法只允许抛出JobExecutionException异常
     */
    @Override
    public void execute(JobExecutionContext executorContext) throws JobExecutionException {
        //JobDetail中的JobDataMap是共用的,从getMergedJobDataMap获取的JobDataMap是全新的对象
        //DynamicJobService的获取JobDataMap.(Job参数对象)方法中map
        JobDataMap map = executorContext.getMergedJobDataMap();
        String jarPath = map.getString("jarPath");
        String parameter = map.getString("parameter");
        String vmParam = map.getString("vmParam");
        String db_ids = map.getString("db_ids");
        String tcp_ids = map.getString("tcp_ids");
        String ws_ids = map.getString("ws_ids");
        String http_ids = map.getString("http_ids");
        String udp_ids = map.getString("udp_ids");
        String other_ids = map.getString("other_ids");
        int job_times = Integer.valueOf(map.getString("job_times"));
        log.info("Running Job name : {} ", map.getString("name"));
        log.info("Running Job description : {}", map.getString("jobDescription"));
        log.info("Running Job group: {} ", map.getString("group"));
        log.info(String.format("Running Job cron : %s", map.getString("cronExpression")));
        //log.info("Running Job jar path : {} ", jarPath);
        log.info("Running Job parameter : {} ", parameter);
        //log.info("Running Job vmParam : {} ", vmParam);
//        int i=1;
//        while(i==1){
//        	
//        }
        if (job_times > 0) {
            //进行job_times--操作。
            log.info("此任务剩余运行次数: {} ", --job_times);
            map.put("job_times", job_times + "");
        } else if (job_times == 0) {
            log.info("此任务剩余运行次数 : {} ，不处理业务逻辑", job_times);
            return;
        }
        long startTime = System.currentTimeMillis();
        //进行定时器业务处理逻辑
//        try {
//			quartzJobService.doWorkJob(db_ids,tcp_ids,ws_ids,http_ids);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
        quartzJobService.doWorkJob(db_ids, tcp_ids, ws_ids, http_ids, udp_ids);
        quartzJobService.doWorkJob(other_ids);

        if (!StringUtils.isEmpty(jarPath)) {
            File jar = new File(jarPath);
            if (jar.exists()) {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.directory(jar.getParentFile());
                List<String> commands = new ArrayList<>();
                commands.add("java");
                if (!StringUtils.isEmpty(vmParam)) commands.add(vmParam);
                commands.add("-jar");
                commands.add(jarPath);
                if (!StringUtils.isEmpty(parameter)) commands.add(parameter);
                processBuilder.command(commands);
                log.info("Running Job details as follows >>>>>>>>>>>>>>>>>>>>: ");
                log.info("Running Job commands : {}  ", StringUtil.getListString(commands));
                try {
                    Process process = processBuilder.start();
                    logProcess(process.getInputStream(), process.getErrorStream());
                } catch (IOException e) {
                    throw new JobExecutionException(e);
                }
            } else throw new JobExecutionException("Job Jar not found >>  " + jarPath);
        }
        long endTime = System.currentTimeMillis();
        log.info(">>>>>>>>>>>>> Running Job has been completed , cost time : {}ms\n ", (endTime - startTime));
    }

    //记录Job执行内容    在这里记录jar包执行日志信息，执行内容。
    private void logProcess(InputStream inputStream, InputStream errorStream) throws IOException {
        String inputLine;
        String errorLine;
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
        while (Objects.nonNull(inputLine = inputReader.readLine())) log.info(inputLine);
        while (Objects.nonNull(errorLine = errorReader.readLine())) log.error(errorLine);
    }

}

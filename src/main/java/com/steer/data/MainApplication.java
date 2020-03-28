package com.steer.data;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
//tx的，还是org的
//import tk.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.annotation.MapperScan;

@EnableTransactionManagement                      // mybatis中service实现类中加入事务注解
@RestController                                   // 在启动类中注入HttpMessageConverters
//@MapperScan(basePackages={"com.steer.data.**"})
@SpringBootApplication
//扫描 所有需要的包, 包含一些自用的工具类包 所在的路径
@ComponentScan(basePackages = {"com.steer.**"})
public class MainApplication {

    public static void main(String[] args) throws Exception {
        /**
         * 对spring boot来说，启动时try...catch真的是多此一举。
         */
        SpringApplication.run(MainApplication.class, args);
        try {
            //DataHandlingServiceImpl dataHandling = SpringUtil.getBean(DataHandlingServiceImpl.class);
            //new NettyServerBootstrap(Integer.valueOf(dataHandling.getNettyserver_port()),dataHandling.getTail());//轧机
            //new NettyClientBootstrap(Integer.valueOf(dataHandling.getPort()), dataHandling.getIp());
            //Task.schedulerJob();
            //new SchedulerAllJob().scheduleJobs();
            StringBuffer sb=new StringBuffer();
            StringBuilder sbb=new StringBuilder();
            Thread t1=new Thread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

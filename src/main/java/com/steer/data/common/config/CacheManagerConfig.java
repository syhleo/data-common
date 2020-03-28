package com.steer.data.common.config;

import com.steer.data.common.constants.CommonConstants;
import com.steer.data.common.redis.RedisUtil;
import com.steer.data.quartz.service.impl.QuartzJobEntityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CacheManagerConfig implements ApplicationRunner {

//    @Autowired
//    private ICacheManager cacheManager;
    @Autowired
    private CacheManagerImpl cacheManager;

    private static Logger logger = LoggerFactory.getLogger(CacheManagerConfig.class);

    @Autowired
    QuartzJobEntityServiceImpl quartzJobService;

    @Autowired
    RedisUtil redisService;

//    @Autowired
//    private IService service;
//
//    @Autowired
//    private IConsumerService consumerService;

    public boolean storeCache(){
        logger.info("项目启动加载所有缓存数据...");
//        /**
//         * 项目启动加载所有编码
//         */
//        List<BizCode> bizList = service.getAllBizCode();
//        /**
//         * 项目启动加载所有消费系统
//         */
//        List<Consumer> consumerList = consumerService.queryAllConsumer();

        /**
         * 清空缓存中现有数据
         */
        cacheManager.clearAll();

//        /**
//         * 项目启动由APPID和编码作为联合主键 存放相对应业务场景(从新存放至缓存)
//         */
//        bizList.forEach(bizCode -> {
//            cacheManager.putCache(bizCode.getAppId()+"-"+bizCode.getServiceCode(),
//                    new CacheManagerEntity(bizCode));
//        });
//        /**
//         * 项目启动由APPID 作为key 存放相对应消费系统信息(从新存放至缓存)
//         */
//        consumerList.forEach(consumer -> {
//            cacheManager.putCache(consumer.getAppId(),
//                    new CacheManagerEntity(consumer));
//        });

//        //临时增加缓存，增加创建速度
//        //public boolean contains(Object value) {  contains是看value,     public boolean containsKey(Object key) {  是看key的
//        if (!GlobalMap.conMap.containsKey(targetNamespace + methodName)) {
//            Client client = factory.createClient(wsdlUrl);
//            ClientMap.conMap.put(targetNamespace + methodName, client);
//            logger.info(">>>>>>>>>初始化>>>>>>>>>" + wsdlUrl);
//        }
        try{
//        //从缓存中换取Redis
//        Client client = ClientMap.conMap.get(targetNamespace + methodName);
            if(redisService.getRedisIsOk()){//判断redis是否存活
                logger.info(">>>>>>>>>启用Reids缓存>>>>>>>>>");
                GlobalMap.conMap.put(CommonConstants.REDIS_ENABLED,CommonConstants.ENABLED);
            }else{
                logger.info(">>>>>>>>>Reids缓存失效>>>>>>>>>");
                GlobalMap.conMap.put(CommonConstants.REDIS_ENABLED,CommonConstants.DISABLED);
            }
        }catch (Exception ex){
            logger.info(">>>>>>>>>禁用Reids缓存>>>>>>>>>");
            ex.printStackTrace();
        }


        if (cacheManager.isEmpty()){
            return false;
        }
        return true;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.storeCache();
    }
}


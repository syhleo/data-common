/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.webservice.server.service.impl.CommonServiceImpl.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月21日上午9:09:06
 * <p>
 * 负责人: syhleo
 * <p>
 * 部门: 工程服务部
 * <p>
 * 修改者：（修改者姓名）
 * <p>
 * 修改时间：
 * <p>
 * 说明：
 * <p>
 */

package com.steer.data.webservice.server.service.impl;

import javax.jws.WebService;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Logger;

import com.steer.data.webservice.server.service.CommonService;

/**
 *
 * @author syhleo
 * 注意：需要对客户端发送过来的数据进行系列化，反系列化，一般采用JSON系列化，反系列化
 */
@WebService(serviceName = "CommonService", // 与接口中指定的name一致
        targetNamespace = "http://service.webservice.data.steer.com/", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.steer.data.webservice.server.service.CommonService"// 接口地址
)
@Component
public class CommonServiceImpl implements CommonService {

    public static Logger logger = (Logger) LoggerFactory.getLogger(CommonServiceImpl.class);


    @Override
    public String commonMethodJsonStr(String jsonStr) {
        /**
         * 示例：
         * {"id":"zhangsan","money":23,"memo":"这是测试数据"}
         * [{"id":"zhangsan","money":23},{"id":"李四","money":24}]
         * zhangsan
         */
        logger.info("服务端接收到数据：" + jsonStr);
        return null;
    }


    @Override
    public String commonMethodObject(Object object) {
        /**
         * 示例：
         * 56ter
         * 48
         * 675.34
         * syhleo
         */
        logger.info("服务端接收到数据：" + object);
        return null;
    }

    @Override
    public String commonMethodObjectArray(Object... objects) {
        /**
         * 示例：
         * [56ter, [{"id":"zhangsan","money":23},{"id":"李四","money":24}]]
         * [45.207]
         */
        logger.info("服务端接收到数据：" + objects);
        return null;
    }

    @Override
    public String commonMethodStringArray(String type, String[] stringArray) {
        /**
         * 示例：
         * 48           [56ter, syhleo]
         * 48           [56ter, [{"id":"zhangsan","money":23},{"id":"李四","money":24}]]
         */
        logger.info("服务端接收到数据：" + type + "；" + stringArray);
        return null;
    }


//	@Override
//	public String commonMethodHashMap(HashMap<String,Object> dataMap) {
//		logger.info("服务端接收到数据："+dataMap);
//		return null;
//	}


    @Override
    public String commonMethodBaseString(String param1, String param2,
                                         String param3, String param4, String param5) {

        logger.info("服务端接收到数据：param1:" + param1 + "，param2:" + param2 + "，param3:" + param3 + "，"
                + "param4:" + param4 + "，param5:" + param5);

        return null;
    }


    @Override
    public String commonMethodBaseParams(String param1, String param2,
                                         String param3, String param4, String param5, String param6,
                                         String param7, String param8, String param9, String param10,
                                         Double param11, Double param12, Double param13, Integer param14,
                                         Integer param15) {

        return null;
    }


}


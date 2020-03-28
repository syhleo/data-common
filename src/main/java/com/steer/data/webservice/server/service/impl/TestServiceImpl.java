/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.webservice.server.TestServiceImpl.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月20日下午2:43:22
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

import org.springframework.stereotype.Component;

import com.steer.data.webservice.server.service.TestService;

/**
 * webservice测试接口实现
 *
 *
 * @author：syhleo
 * @date：
 */
@WebService(serviceName = "TestService", // 与接口中指定的name一致
        targetNamespace = "http://service.webservice.data.steer.com/", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.steer.data.webservice.server.service.TestService"// 接口地址
)
@Component
public class TestServiceImpl implements TestService {

    @Override
    public String sendMessage(String username) {

        return "hello " + username;
    }

}

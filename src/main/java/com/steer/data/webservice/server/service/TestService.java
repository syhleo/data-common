/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.webservice.server.TestService.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月20日下午2:42:36
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


package com.steer.data.webservice.server.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * webservice测试接口
 *
 *
 * @author：WangYuanJun
 * @date：2017年12月19日 下午9:36:49
 */
@WebService(name = "TestService", // 与接口中指定的name一致
        targetNamespace = "http://service.webservice.data.steer.com/")
public interface TestService {

    @WebMethod
    @WebResult(name = "String", targetNamespace = "")
    String sendMessage(@WebParam(name = "username") String username);
}

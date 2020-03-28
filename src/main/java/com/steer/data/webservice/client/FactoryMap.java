/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.webservice.client.FactoryMap.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月20日上午10:40:57
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


package com.steer.data.webservice.client;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.cxf.endpoint.Endpoint;


public class FactoryMap {

    public static ConcurrentHashMap<String, Endpoint> conMap = new ConcurrentHashMap<String, Endpoint>();
}


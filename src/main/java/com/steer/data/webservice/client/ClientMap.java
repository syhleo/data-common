/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.webservice.client.ClientMap.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月20日上午10:49:02
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

import org.apache.cxf.endpoint.Client;


public class ClientMap {

    public static ConcurrentHashMap<String, Client> conMap = new ConcurrentHashMap<String, Client>();
}


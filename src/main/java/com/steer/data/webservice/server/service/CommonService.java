/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.webservice.server.service.CommonService.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月21日上午8:50:06
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

@WebService(name = "CommonService", // 与接口中指定的name一致
        targetNamespace = "http://service.webservice.data.steer.com/")
public interface CommonService {

    @WebMethod
    @WebResult(name = "String", targetNamespace = "")
    String commonMethodJsonStr(@WebParam(name = "jsonStr") String jsonStr);


    @WebMethod
    @WebResult(name = "String", targetNamespace = "")
    String commonMethodObject(Object object);

    @WebMethod
    @WebResult(name = "String", targetNamespace = "")
    String commonMethodObjectArray(Object... objects);

    @WebMethod
    @WebResult(name = "String", targetNamespace = "")
    String commonMethodStringArray(String type, String[] stringArray);


//	@WebMethod
//	@WebResult(name = "String", targetNamespace = "")
//	public String commonMethodHashMap(@XmlJavaTypeAdapter(MapAdapter.class)HashMap<String,Object> dataMap);
//	//public String commonMethodHashMap(@XmlJavaTypeAdapter(MapAdapter.class)String dataMap);

    @WebMethod
    @WebResult(name = "String", targetNamespace = "")
    String commonMethodBaseString(String param1, String param2,
                                  String param3, String param4, String param5);


    @WebMethod
    @WebResult(name = "String", targetNamespace = "")
    String commonMethodBaseParams(String param1, String param2,
                                  String param3, String param4, String param5, String param6,
                                  String param7, String param8, String param9, String param10,
                                  Double param11, Double param12, Double param13, Integer param14,
                                  Integer param15);

}


/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.webservice.server.CxfConfig.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月20日下午2:44:34
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


package com.steer.data.webservice.server.config;


import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.steer.data.webservice.server.service.impl.CommonServiceImpl;

/**
 * cxf配置
 * @author：syhleo
 * @date：2017年12月19日 下午9:38:24
 */
@Configuration
public class CxfConfig {

    @Autowired
    private Bus bus;

    @Bean
    public Endpoint endpoint() {
//        EndpointImpl endpoint = new EndpointImpl(springBus(), new TestServiceImpl());// 绑定要发布的服务实现类
//        endpoint.publish("/TestService");// 接口访问地址 
        //绑定要发布的服务
        EndpointImpl endpoint = new EndpointImpl(springBus(), new CommonServiceImpl());// 绑定要发布的服务实现类
        //显示要发布的名称
        endpoint.publish("/CommonService");// 接口访问地址

        return endpoint;
    }

    /**
     *  解决Spring boot 通过Servlet发布Web Service后无法正常访问Controlle的问题。
     * public ServletRegistrationBean dispatcherServlet() 把默认映射覆盖掉了，把这个名字改掉，控制类方法就能访问了。
     更改此方法明后可以正常其他请求url，webservice服务也正常。
     即查之后发现原因是springboot默认注册的是 dispatcherServlet，当手动配置 ServletRegistrationBean后springboot不会再去注册默认的dispatcherServlet
     * @return
     */
//    @Bean
//	public ServletRegistrationBean dispatcherServlet() {
//		return new ServletRegistrationBean(new CXFServlet(), "/cxf/*");// 发布服务名称 localhost:8080/cxf
//																		
//	}
    @Bean
    public ServletRegistrationBean disServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/cxf/*");// 发布服务名称 localhost:8080/cxf

    }


    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }


}



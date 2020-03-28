/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.webservice.client.ClientInvoke.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月20日上午10:50:58
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

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.steer.data.common.utils.StringUtil;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientImpl;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.*;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientInvoke {

    public static Logger logger = (Logger) LoggerFactory.getLogger(ClientInvoke.class);

    /**
     * 支持SOAP1.1
     * @param wsdlUrl  wsdl的地址：http://172.16.81.200:8003/Ws-Servers/service/mesPlmL2Wgt?wsdl
     * @param methodName 调用的方法名称 insertACCEPT
     * @param targetNamespace 命名空间 http://impl.service.wgt.plm.com/
     * @param name  name MesPlmL2WgtServiceImplService
     * @param paramList 参数集合
     * @throws Exception
     */
    public static String dynamicCallWebServiceByCXF(String wsdlUrl, String methodName, String targetNamespace, String subTargetNamespace, String name, List<Object> paramList) throws Exception {
        //临时增加缓存，增加创建速度
        //public boolean contains(Object value) {  contains是看value,     public boolean containsKey(Object key) {  是看key的
        if (!FactoryMap.conMap.containsKey(targetNamespace + methodName)) {
            // 创建动态客户端
            JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
            /**
             * org.apache.cxf.bus.extension.ExtensionException: Could not create object of extension class org.apache.cxf.binding.xml.wsdl11.XMLWSDLExtensionLoader.
             * Client client = factory.createClient(wsdlUrl);会提示此信息，但无伤大雅。问题已解决：改为cglib3.2.5
             */
            // 创建客户端连接
            Client client = factory.createClient(wsdlUrl);
//			// 输入报文拦截器
//			client.getInInterceptors().add(new InInterceptor());
//			// 输出报文拦截器
//			client.getOutInterceptors().add(new OutInterceptor());

            ClientImpl clientImpl = (ClientImpl) client;
            Endpoint endpoint = clientImpl.getEndpoint();
            FactoryMap.conMap.put(targetNamespace + methodName, endpoint);
            ClientMap.conMap.put(targetNamespace + methodName, client);
            logger.info(">>>>>>>>>初始化>>>>>>>>>" + wsdlUrl);
        }
        //从缓存中换取 endpoint、client
        Endpoint endpoint = FactoryMap.conMap.get(targetNamespace + methodName);
        Client client = ClientMap.conMap.get(targetNamespace + methodName);
        // Make use of CXF service model to introspect the existing WSDL
        ServiceInfo serviceInfo = endpoint.getService().getServiceInfos().get(0);
        // 创建QName来指定NameSpace和要调用的service
        String localPart = name + "SoapBinding";
        QName bindingName = new QName(targetNamespace, localPart);
        BindingInfo binding = serviceInfo.getBinding(bindingName);

        //创建QName来指定NameSpace和要调用的方法绑定方法
        QName opName = new QName(subTargetNamespace, methodName);//selectOrderInfo
        //QName opName = new QName(targetNamespace, localPart);//selectOrderInfo

        //可以通过debug调试看对象里面的参数，看参数找到如何赋值法...
//		for(BindingOperationInfo bbb:binding.getOperations()){
//			System.out.println(bbb);
//		}
        //http://service.wgt.plm.com/}insertACCEPT

        BindingOperationInfo boi = binding.getOperation(opName);
//		BindingMessageInfo inputMessageInfo = boi.getInput();
        BindingMessageInfo inputMessageInfo = null;
        if (!boi.isUnwrapped()) {
            //OrderProcess uses document literal wrapped style.
            inputMessageInfo = boi.getWrappedOperation().getInput();
        } else {
            inputMessageInfo = boi.getUnwrappedOperation().getInput();
        }

        List<MessagePartInfo> parts = inputMessageInfo.getMessageParts();

        /***********************以下是初始化参数，组装参数；处理返回结果的过程******************************************/
        Object[] parameters = new Object[parts.size()];
        for (int m = 0; m < parts.size(); m++) {
            MessagePartInfo part = parts.get(m);
            // 取得对象实例
            Class<?> partClass = part.getTypeClass();//OrderInfo.class;

            //'[' 表示数组，一个代表一维数组，比如 '[[' 代表二维数组
            logger.info("CanonicalName:" + partClass.getCanonicalName() + "，Name:" + partClass.getName() + "，"
                    + "SimpleName:" + partClass.getSimpleName()); //


            //实例化对象
            Object initDomain = null;
            //普通参数的形参，不需要fastJson转换直接赋值即可
            if ("java.lang.String".equalsIgnoreCase(partClass.getCanonicalName())
//					||"int".equalsIgnoreCase(partClass.getCanonicalName())
//					|| "float".equals(partClass.getCanonicalName())
//					|| "double".equals(partClass.getCanonicalName())
            ) {
                initDomain = paramList.get(m).toString();
            }
            //如果是数组
            else if (partClass.getCanonicalName().indexOf("[]") > -1) {
                //转换数组
                try {
                    initDomain = JSON.parseArray(paramList.get(m).toString(), partClass.getComponentType());
                } catch (Exception ex) {
                    Object json = JSONObject.toJSON(paramList.get(m));
                    try {
                        initDomain = JSON.parseObject(json.toString(), partClass);
                    } catch (Exception e2) {
                        initDomain = JSON.parseObject(json.toString(), String.class);
                    }
                }
            } else {
                try {
                    initDomain = JSON.parseObject(paramList.get(m).toString(), partClass);
                } catch (Exception ex) {
                    Object json = JSONObject.toJSON(paramList.get(m));
                    try {
                        initDomain = JSON.parseObject(json.toString(), partClass);
                    } catch (Exception e2) {
                        //initDomain=JSON.parseObject(json.toString(),String.class);
                        initDomain = json.toString();
                    }
                }
            }
            parameters[m] = initDomain;

        }
        //定义返回结果集
        Object[] result = null;
        //普通参数情况 || 对象参数情况  1个参数 ||ArryList集合
        try {
            result = client.invoke(opName, parameters);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("WebService客户端请求服务端失败，wsdl：" + wsdlUrl + "，方法名：" + methodName);
            return "参数异常" + ex.getMessage();
        }
        //返回调用结果
        if (result.length > 0) {
            if (result[0] != null) {
                try {
                    logger.info("WebService客户端请求服务端成功，返回值：" + JSON.toJSON(result[0]).toString() + "，wsdl：" + wsdlUrl + "，方法名：" + methodName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return JSON.toJSON(result[0]).toString();
            }
        }
        logger.info("WebService客户端请求服务端成功，wsdl：" + wsdlUrl + "，方法名：" + methodName);
        return "invoke success, but is void ";
    }

    /**
     * 支持SOAP1.1----简略版本
     * @param wsdlUrl  wsdl的地址：http://172.16.81.200:8003/Ws-Servers/service/mesPlmL2Wgt?wsdl
     * @param methodName 调用的方法名称 insertACCEPT
     * @throws Exception
     */
    public static String dynamicCallWebServiceByCXF(String wsdlUrl, String targetNamespace, String methodName, Object... objparams) throws Exception {
        //临时增加缓存，增加创建速度
        //public boolean contains(Object value) {  contains是看value,     public boolean containsKey(Object key) {  是看key的
        if (!ClientMap.conMap.containsKey(targetNamespace + methodName)) {
            // 创建动态客户端
            JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
            // 创建客户端连接
            Client client = factory.createClient(wsdlUrl);
            ClientMap.conMap.put(targetNamespace + methodName, client);
            logger.info(">>>>>>>>>初始化>>>>>>>>>" + wsdlUrl);
        }
        //从缓存中换取 endpoint、client
        Client client = ClientMap.conMap.get(targetNamespace + methodName);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        Object[] objects = new Object[100];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            //objects = client.invoke("Get_RFID_Data_XML_String", "");//可以
            objects = client.invoke(methodName, objparams);//可以
            //System.out.println("返回数据:" + objects[0]);
            logger.info("WebService客户端请求服务端成功，wsdl：" + wsdlUrl + "，方法名：" + methodName);
            logger.info("返回数据:" + objects[0]);
            return objects[0].toString();
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            logger.error("WebService客户端请求服务端失败，wsdl：" + wsdlUrl + "，方法名：" + methodName);
            return "WebService客户端请求异常" + ex.getMessage();
        }
    }


    /**
     * 支持SOAP1.2
     * @param wsdlUrl  wsdl的地址：http://172.16.81.200:8003/Ws-Servers/service/mesPlmL2Wgt?wsdl
     * @param methodName 调用的方法名称 insertACCEPT
     * @param targetNamespace 命名空间 http://impl.service.wgt.plm.com/
     * @param name  name MesPlmL2WgtServiceImplService
     * @param paramList 参数集合
     * @throws Exception
     */
    public static String dynamicCallWebServiceByCXF12(String wsdlUrl, String methodName, String targetNamespace, String subTargetNamespace, String name, List<Object> paramList) throws Exception {
        //临时增加缓存，增加创建速度
        if (!FactoryMap.conMap.containsKey(targetNamespace + methodName)) {
            //new Bus().set
            // 创建动态客户端
            JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
            /**
             * org.apache.cxf.bus.extension.ExtensionException: Could not create object of extension class org.apache.cxf.binding.xml.wsdl11.XMLWSDLExtensionLoader.
             * Client client = factory.createClient(wsdlUrl);会提示此信息，但无伤大雅。问题已解决：改为cglib3.2.5
             */

            // 创建客户端连接
            Client client = factory.createClient(wsdlUrl);
//			// 输入报文拦截器
//			client.getInInterceptors().add(new InInterceptor());
//			// 输出报文拦截器
//			client.getOutInterceptors().add(new OutInterceptor());

            ClientImpl clientImpl = (ClientImpl) client;
            Endpoint endpoint = clientImpl.getEndpoint();
            FactoryMap.conMap.put(targetNamespace + methodName, endpoint);
            ClientMap.conMap.put(targetNamespace + methodName, client);
            logger.info(">>>>>>>>>初始化>>>>>>>>>" + wsdlUrl);
        }
        //从缓存中换取 endpoint、client
        Endpoint endpoint = FactoryMap.conMap.get(targetNamespace + methodName);
        Client client = ClientMap.conMap.get(targetNamespace + methodName);
        // Make use of CXF service model to introspect the existing WSDL
        ServiceInfo serviceInfo = endpoint.getService().getServiceInfos().get(0);
        // 创建QName来指定NameSpace和要调用的service
        String localPart = name + "SoapBinding";
        QName bindingName = new QName(targetNamespace, localPart);
        BindingInfo binding = serviceInfo.getBinding(bindingName);
        //创建QName来指定NameSpace和要调用的方法绑定方法
        QName opName = new QName(subTargetNamespace, methodName);//selectOrderInfo
        //QName opName = new QName(targetNamespace, localPart);//selectOrderInfo

        BindingOperationInfo boi = binding.getOperation(opName);
//		BindingMessageInfo inputMessageInfo = boi.getInput();
        BindingMessageInfo inputMessageInfo = null;
        if (!boi.isUnwrapped()) {
            //OrderProcess uses document literal wrapped style.
            inputMessageInfo = boi.getWrappedOperation().getInput();
        } else {
            inputMessageInfo = boi.getUnwrappedOperation().getInput();
        }

        List<MessagePartInfo> parts = inputMessageInfo.getMessageParts();

        /***********************以下是初始化参数，组装参数；处理返回结果的过程******************************************/
        Object[] parameters = new Object[parts.size()];
        for (int m = 0; m < parts.size(); m++) {
            MessagePartInfo part = parts.get(m);
            // 取得对象实例
            Class<?> partClass = part.getTypeClass();//OrderInfo.class;

            //'[' 表示数组，一个代表一维数组，比如 '[[' 代表二维数组
            logger.info("CanonicalName:" + partClass.getCanonicalName() + "，Name:" + partClass.getName() + "，"
                    + "SimpleName:" + partClass.getSimpleName()); //


            //实例化对象
            Object initDomain = null;
            //普通参数的形参，不需要fastJson转换直接赋值即可
            if ("java.lang.String".equalsIgnoreCase(partClass.getCanonicalName())
//					||"int".equalsIgnoreCase(partClass.getCanonicalName())
//					|| "float".equals(partClass.getCanonicalName())
//					|| "double".equals(partClass.getCanonicalName())
            ) {
                initDomain = paramList.get(m).toString();
            }
            //如果是数组
            else if (partClass.getCanonicalName().indexOf("[]") > -1) {
                //转换数组
                try {
                    initDomain = JSON.parseArray(paramList.get(m).toString(), partClass.getComponentType());
                } catch (Exception ex) {
                    Object json = JSONObject.toJSON(paramList.get(m));
                    try {
                        initDomain = JSON.parseObject(json.toString(), partClass);
                    } catch (Exception e2) {
                        initDomain = JSON.parseObject(json.toString(), String.class);
                    }
                }
            } else {
                try {
                    initDomain = JSON.parseObject(paramList.get(m).toString(), partClass);
                } catch (Exception ex) {
                    Object json = JSONObject.toJSON(paramList.get(m));
                    try {
                        initDomain = JSON.parseObject(json.toString(), partClass);
                    } catch (Exception e2) {
                        //initDomain=JSON.parseObject(json.toString(),String.class);
                        initDomain = json.toString();
                    }
                }
            }
            parameters[m] = initDomain;

        }
        //定义返回结果集
        Object[] result = null;
        //普通参数情况 || 对象参数情况  1个参数 ||ArryList集合
        try {
            result = client.invoke(opName, parameters);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("WebService客户端请求服务端失败，wsdl：" + wsdlUrl + "，方法名：" + methodName);
            return "参数异常" + ex.getMessage();
        }
        //返回调用结果
        if (result.length > 0) {
            if (result[0] != null) {
                try {
                    logger.info("WebService客户端请求服务端成功，返回值：" + JSON.toJSON(result[0]).toString() + "，wsdl：" + wsdlUrl + "，方法名：" + methodName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return JSON.toJSON(result[0]).toString();
            }
        }
        logger.info("WebService客户端请求服务端成功，wsdl：" + wsdlUrl + "，方法名：" + methodName);
        return "invoke success, but is void ";
    }


    public static void main1(String[] args) {
        //wsdl的地址        和Ws-Servers项目结合起来看
        String wsdlUrl = "http://172.16.81.200:8003/Ws-Servers/service/mesPlmL2Wgt?wsdl";
        //String wsdlUrl="http://localhost:8080/Ws-Servers/service/mesPlmL2Wgt?wsdl";
        //String wsdlUrl="http://172.16.81.200:8003/Ws-Servers/service/mesPlmL2Wgt?wsdl=MesPlmL2WgtService.wsdl";

        //调用的方法名称
        String methodName = "insertACCEPT";
        //命名空间       刚进入wsdl看到的targetNamespace名称        切记仔细查看服务端wsdl中targetNamespace是否是/结尾
        String targetNamespace = "http://impl.service.wgt.plm.com/";
        //具体方法里面命名空间，此处一般和主targetNamespace赋值是一样的  切记仔细查看服务端wsdl中targetNamespace是否是/结尾
        String subTargetNamespace = "http://service.wgt.plm.com/";

        String name = "MesPlmL2WgtServiceImplService";
        //参数集合
        List<Object> paramList = new ArrayList<>();
        paramList.add("zhangsan");
        paramList.add("lisi");
        paramList.add(4.3);
        paramList.add(7.8);
        paramList.add("2019-08-20 12:12:12");
        paramList.add("测试ws");
        paramList.add(1);
        paramList.add(0);
        //wsdl的地址
//		String wsdlUrl="http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl";
//		//调用的方法名称
//		String methodName="getSupportCity";
//		//命名空间
//		String targetNamespace="http://WebXml.com.cn/";
//		String name="WeatherWebServiceSoap";
//		//参数集合
//		List<Object> paramList=new ArrayList<>();
//		paramList.add("zhangsan");
//		paramList.add("lisi");
//		paramList.add(4.3);
//		paramList.add(7.8);
//		paramList.add("2019-08-20 12:12:12");
//		paramList.add("测试ws");
//		paramList.add(1);
//		paramList.add(0);

        try {
            dynamicCallWebServiceByCXF(wsdlUrl, methodName, targetNamespace, subTargetNamespace, name, paramList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//		//wsdl的地址        和Ws-Servers项目结合起来看
//		String wsdlUrl="http://172.16.21.166:8730/RfidService?wsdl";
//		//String wsdlUrl="http://localhost:8080/Ws-Servers/service/mesPlmL2Wgt?wsdl";
//		//String wsdlUrl="http://172.16.81.200:8003/Ws-Servers/service/mesPlmL2Wgt?wsdl=MesPlmL2WgtService.wsdl";
//		//调用的方法名称
//		String methodName="Get_RFID_Data_XML_String";
//		//命名空间       刚进入wsdl看到的targetNamespace名称        切记仔细查看服务端wsdl中targetNamespace是否是/结尾
//		String targetNamespace="http://tempuri.org/";
//		//参数集合
//		List<Object> paramList=new ArrayList<>();
//		while (true){
//			try {
//				dynamicCallWebServiceByCXF(wsdlUrl,targetNamespace, methodName, paramList);
//				Thread.sleep(1000);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}


        //wsdl的地址        和Ws-Servers项目结合起来看
        String wsdlUrl = "http://172.16.21.166:8730/RfidService?wsdl";
        //String wsdlUrl="http://localhost:8080/Ws-Servers/service/mesPlmL2Wgt?wsdl";
        //String wsdlUrl="http://172.16.81.200:8003/Ws-Servers/service/mesPlmL2Wgt?wsdl=MesPlmL2WgtService.wsdl";
        //调用的方法名称
        String methodName = "Write_BatchLotLet_1_Current";
        //命名空间       刚进入wsdl看到的targetNamespace名称        切记仔细查看服务端wsdl中targetNamespace是否是/结尾
        String targetNamespace = "http://tempuri.org/";
        //参数集合
        List<Object> paramList = new ArrayList<>();
        long zhapihao = 2907037;
        int steel = 32;
        int jihuazhishu = 20;
        paramList.add(zhapihao);//第一个参数
        paramList.add(steel);//第二个参数      根据服务端程序只接受第一个参数，所以会自动过滤获取第一个参数，此参数不参与
        paramList.add(jihuazhishu);//第三个参数
        try {
            if (!ClientMap.conMap.containsKey(targetNamespace + methodName)) {
                // 创建动态客户端
                JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
                // 创建客户端连接
                Client client = factory.createClient(wsdlUrl);
                ClientMap.conMap.put(targetNamespace + methodName, client);
                logger.info(">>>>>>>>>初始化>>>>>>>>>" + wsdlUrl);
            }
            //从缓存中换取 endpoint、client
            Client client = ClientMap.conMap.get(targetNamespace + methodName);
            // 需要密码的情况需要加上用户名和密码
            // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
            Object[] objects = new Object[100];
            try {
                // invoke("方法名",参数1,参数2,参数3....);
                //objects = client.invoke("Get_RFID_Data_XML_String", "");//可以
                objects = client.invoke(methodName, zhapihao, jihuazhishu, steel);//可以
                //System.out.println("返回数据:" + objects[0]);
                logger.info("WebService客户端请求服务端成功，wsdl：" + wsdlUrl + "，方法名：" + methodName);
                logger.info("返回数据:" + objects[0]);

            } catch (java.lang.Exception ex) {
                ex.printStackTrace();
                logger.error("WebService客户端请求服务端失败，wsdl：" + wsdlUrl + "，方法名：" + methodName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test333(String[] args) {
        //wsdl的地址        和Ws-Servers项目结合起来看
        String wsdlUrl = "http://172.16.21.166:8730/RfidService?wsdl";
        //String wsdlUrl="http://localhost:8080/Ws-Servers/service/mesPlmL2Wgt?wsdl";
        //String wsdlUrl="http://172.16.81.200:8003/Ws-Servers/service/mesPlmL2Wgt?wsdl=MesPlmL2WgtService.wsdl";
        //调用的方法名称
        String methodName = "Write_BatchLotLet_1_Current";
        //命名空间       刚进入wsdl看到的targetNamespace名称        切记仔细查看服务端wsdl中targetNamespace是否是/结尾
        String targetNamespace = "http://tempuri.org/";
        //参数集合
        List<Object> paramList = new ArrayList<>();
        int zhapihao = 2900001;
        int steel = 32;
        int jihuazhishu = 20;
        paramList.add(zhapihao);//第一个参数
        paramList.add(steel);//第二个参数      根据服务端程序只接受第一个参数，所以会自动过滤获取第一个参数，此参数不参与
        paramList.add(jihuazhishu);//第三个参数
        while (true) {
            try {
                dynamicCallWebServiceByCXF(wsdlUrl, targetNamespace, methodName, paramList);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 从Ws-Servers工程拷贝测试内容至此，方便查阅。
     * Ws-Servers\src\com\plm\wgt\test\client
     */
//	@Test
//	public void testCommonMethodJsonStr(){
//
//		//wsdl的地址
//		String wsdlUrl="http://localhost:8682/cxf/CommonService?wsdl";
//
//		//调用的方法名称
//		String methodName="commonMethodJsonStr";
//		//命名空间       刚进入wsdl看到的targetNamespace名称        切记仔细查看服务端wsdl中targetNamespace是否是/结尾
//		String targetNamespace="http://service.webservice.data.steer.com/";
//		//具体方法里面命名空间，此处一般和主targetNamespace赋值是一样的  切记仔细查看服务端wsdl中targetNamespace是否是/结尾
//		String subTargetNamespace="http://service.webservice.data.steer.com/";
//
//		String name="CommonService";
//		//参数集合
//		List<Object> paramList=new ArrayList<>();
//		//String param1="zhangsan";//test ok
//		//String param1="{\"id\":\"zhangsan\",\"money\":23,\"memo\":\"这是测试数据\"}";//test ok
//		String param1="[{\"id\":\"zhangsan\",\"money\":23},{\"id\":\"李四\",\"money\":24}]";//test ok
//
//		paramList.add(param1);//第一个参数
//		paramList.add("lisi");//第二个参数      根据服务端程序只接受第一个参数，所以会自动过滤获取第一个参数，此参数不参与
//		paramList.add("44554");//第三个参数      根据服务端程序只接受第一个参数，所以会自动过滤获取第一个参数，此参数不参与
//
//		try {
//			dynamicCallWebServiceByCXF(wsdlUrl, methodName, targetNamespace, subTargetNamespace,name, paramList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//

    /**
     * 可以
     */
    @Test
    public void testSend1() {

        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://172.16.21.166:8730/RfidService?wsdl");

        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        Object[] objects = new Object[100];
        try {

            // invoke("方法名",参数1,参数2,参数3....);
            //objects = client.invoke("Get_RFID_Data_XML_String", "");//可以
            /**
             * 返回数据:<NewDataSet>
             *   <RFID>
             *     <RFID_ID>1</RFID_ID>
             *     <RFID_ChineseDescr>上卷(222)</RFID_ChineseDescr>
             *     <GouHao>48</GouHao>
             *     <RFID_Count>5424</RFID_Count>
             *     <UpdateTime>2019-11-21T08:44:27+08:00</UpdateTime>
             *     <RFID_IP>172.16.21.222</RFID_IP>
             *   </RFID>
             *   <RFID>
             *     <RFID_ID>2</RFID_ID>
             *     <RFID_ChineseDescr>质检(223)</RFID_ChineseDescr>
             *     <GouHao>37</GouHao>
             *     <RFID_Count>9995</RFID_Count>
             *     <UpdateTime>2019-11-21T08:44:19+08:00</UpdateTime>
             *     <RFID_IP>172.16.21.223</RFID_IP>
             *   </RFID>
             *   <RFID>
             *     <RFID_ID>3</RFID_ID>
             *     <RFID_ChineseDescr>称重(224)</RFID_ChineseDescr>
             *     <GouHao>53</GouHao>
             *     <RFID_Count>1574</RFID_Count>
             *     <UpdateTime>2019-11-21T08:46:03+08:00</UpdateTime>
             *     <RFID_IP>172.16.21.224</RFID_IP>
             *   </RFID>
             *   <RFID>
             *     <RFID_ID>4</RFID_ID>
             *     <RFID_ChineseDescr>斜卷(225)</RFID_ChineseDescr>
             *     <GouHao>70</GouHao>
             *     <RFID_Count>1358</RFID_Count>
             *     <UpdateTime>2019-11-21T08:45:35+08:00</UpdateTime>
             *     <RFID_IP>172.16.21.225</RFID_IP>
             *   </RFID>
             * </NewDataSet>
             */
            objects = client.invoke("Get_RFID_Data_Json_String", "");//可以
            /**
             * 返回数据:[{"RFID_ID":"1","RFID_ChineseDescr":"上卷(222)","GouHao":"","RFID_Count":"","UpdateTime":"2019/11/21 9:06:36","RFID_IP":"172.16.21.222"},{"RFID_ID":"2","RFID_ChineseDescr":"质检(223)","GouHao":"","RFID_Count":"","UpdateTime":"2019/11/21 9:06:36","RFID_IP":"172.16.21.223"},{"RFID_ID":"3","RFID_ChineseDescr":"称重(224)","GouHao":"","RFID_Count":"","UpdateTime":"2019/11/21 9:06:36","RFID_IP":"172.16.21.224"},{"RFID_ID":"4","RFID_ChineseDescr":"斜卷(225)","GouHao":"","RFID_Count":"","UpdateTime":"2019/11/21 9:06:36","RFID_IP":"172.16.21.225"}]
             */
            System.out.println("返回数据:" + objects[0]);
            List<HashMap<String, String>> list = StringUtil.jsonArrayStrToHashMapList(objects[0].toString());

            System.out.println(list);
        } catch (java.lang.Exception e) {
            System.out.println(111);
            e.printStackTrace();
        }
    }

//
//	@Test
//	public void testCommonMethodObject(){
//
//		//wsdl的地址
//		String wsdlUrl="http://localhost:8682/cxf/CommonService?wsdl";
//
//		//调用的方法名称
//		String methodName="commonMethodObject";
//		//命名空间       刚进入wsdl看到的targetNamespace名称        切记仔细查看服务端wsdl中targetNamespace是否是/结尾
//		String targetNamespace="http://service.webservice.data.steer.com/";
//		//具体方法里面命名空间，此处一般和主targetNamespace赋值是一样的  切记仔细查看服务端wsdl中targetNamespace是否是/结尾
//		String subTargetNamespace="http://service.webservice.data.steer.com/";
//
//		String name="CommonService";
//		//参数集合
//		List<Object> paramList=new ArrayList<>();
//		String param0="56ter";//test ok
//		String param1="[{\"id\":\"zhangsan\",\"money\":23},{\"id\":\"李四\",\"money\":24}]";//不可以
//		Integer param2=48;//test ok
//		Double param3=675.34;//test ok
//		String param4="syhleo";//test ok
//
//		paramList.add(param3);//第一个参数   test ok
//
//		paramList.add(param2);//第二个参数      根据服务端程序只接受第一个参数，所以会自动过滤获取第一个参数，此参数不参与
//		paramList.add(param3);//第三个参数      根据服务端程序只接受第一个参数，所以会自动过滤获取第一个参数，此参数不参与
//		paramList.add(param4);//第四个参数      根据服务端程序只接受第一个参数，所以会自动过滤获取第一个参数，此参数不参与
//
//		try {
//			dynamicCallWebServiceByCXF(wsdlUrl, methodName, targetNamespace, subTargetNamespace,name, paramList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//
//	@Test
//	public void testCommonMethodObjectArray(){
//
//		//wsdl的地址
//		String wsdlUrl="http://localhost:8682/cxf/CommonService?wsdl";
//
//		//调用的方法名称
//		String methodName="commonMethodObjectArray";
//		//命名空间       刚进入wsdl看到的targetNamespace名称        切记仔细查看服务端wsdl中targetNamespace是否是/结尾
//		String targetNamespace="http://service.webservice.data.steer.com/";
//		//具体方法里面命名空间，此处一般和主targetNamespace赋值是一样的  切记仔细查看服务端wsdl中targetNamespace是否是/结尾
//		String subTargetNamespace="http://service.webservice.data.steer.com/";
//
//		String name="CommonService";
//		//参数集合
//		List<Object> paramList=new ArrayList<>();
//		String param0="56ter";
//		String param1="[{\"id\":\"zhangsan\",\"money\":23},{\"id\":\"李四\",\"money\":24}]";//test ok
//		Integer param2=48;
//		Double param3=675.34;
//		String param4="syhleo";
//		String[] object=new String[]{param0,param1};
	  /*
      将对象写入到流中（序列化），从流中读取对象（反序列化）
	  通过这些简单的操作(序列化和反序列化，将数据压缩)，可以使数据集等体积庞大的对象在远程传递中的时间大大减少，
	  并且可以减少网络中断等问题对程序的影响。
	  */
//		//序列化为json格式的字符串序列，即数组序列化为json字符串序列       将对象序列化为JSON字符串
//		//paramList.add(JSON.toJSON(object).toString());//第一个参数   test ok
//		//paramList.add(JSON.toJSON(object));//第一个参数   test ok
//		Double[] dou1=new Double[]{45.207};//可以
//		paramList.add(JSON.toJSON(dou1));//第一个参数   test ok
//
//		paramList.add(param2);//第二个参数      根据服务端程序只接受第一个参数，所以会自动过滤获取第一个参数，此参数不参与
//		paramList.add(param3);//第三个参数      根据服务端程序只接受第一个参数，所以会自动过滤获取第一个参数，此参数不参与
//		paramList.add(param4);//第四个参数      根据服务端程序只接受第一个参数，所以会自动过滤获取第一个参数，此参数不参与
//
//		try {
//			dynamicCallWebServiceByCXF(wsdlUrl, methodName, targetNamespace, subTargetNamespace,name, paramList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//
//
//	@Test
//	public void testCommonMethodStringArray(){
//
//		//wsdl的地址
//		String wsdlUrl="http://localhost:8682/cxf/CommonService?wsdl";
//
//		//调用的方法名称
//		String methodName="commonMethodStringArray";
//		//命名空间       刚进入wsdl看到的targetNamespace名称        切记仔细查看服务端wsdl中targetNamespace是否是/结尾
//		String targetNamespace="http://service.webservice.data.steer.com/";
//		//具体方法里面命名空间，此处一般和主targetNamespace赋值是一样的  切记仔细查看服务端wsdl中targetNamespace是否是/结尾
//		String subTargetNamespace="http://service.webservice.data.steer.com/";
//
//		String name="CommonService";
//		//参数集合
//		List<Object> paramList=new ArrayList<>();
//		//String param1="zhangsan";//test ok
//		//String param1="{\"id\":\"zhangsan\",\"money\":23,\"memo\":\"这是测试数据\"}";//test ok
//		String param0="56ter";
//		String param1="[{\"id\":\"zhangsan\",\"money\":23},{\"id\":\"李四\",\"money\":24}]";//test ok
//		Integer param2=48;
//		Double param3=675.34;
//		String param4="syhleo";
//		String[] object=new String[]{param0,param1};
//
//
//		paramList.add(param2+"");
//
//		//序列化为json格式的字符串序列，即数组序列化为json字符串序列
//		//paramList.add(JSON.toJSON(object).toString());//第一个参数   test ok
//		paramList.add(JSON.toJSON(object));//第二个参数   test ok
//		/**
//		 * paramList.add(object);//第二个参数     //注意，非常错误，debug发现object看不到具体的值，这种写法是错误的，因为没有对object进行系列化
//		 * java 对象转JSON（JSON序列化）
//		 * JSON转Java类[JSON反序列化]
//		 */
//		//paramList.add(object);//第二个参数     //注意，非常错误，debug发现object看不到具体的值，这种写法是错误的
//		paramList.add(param3);//第三个参数      根据服务端程序只接受第一个参数，所以会自动过滤获取第一个参数，此参数不参与
//		paramList.add(param4);//第四个参数      根据服务端程序只接受第一个参数，所以会自动过滤获取第一个参数，此参数不参与
//
//		try {
//			dynamicCallWebServiceByCXF(wsdlUrl, methodName, targetNamespace, subTargetNamespace,name, paramList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

}
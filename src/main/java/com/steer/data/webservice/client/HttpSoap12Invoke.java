package com.steer.data.webservice.client;

import com.steer.data.webservice.plm.wgt.service.MesPlmL2WgtService;
import com.steer.data.webservice.rfidsoap.IRfidService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class HttpSoap12Invoke {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        String soapRequestData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""
                + " xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
                + " <soap12:Body>"
                + " <GetTax xmlns=\"http://www.galasystec.net.cn/\">"
                //  + " <GetAPACShippingPackageRequest>"
                + " <sagncode>QDZP001</sagncode>"
                + " <sDate>2018-06-20</sDate>"

                //+ " </GetAPACShippingPackageRequest>"
                + " </GetTax>" + "</soap12:Body>"
                + " </soap12:Envelope>";

        System.out.println(soapRequestData);

//        PostMethod postMethod = new PostMethod(
//                "http://******地址/WDService.asmx");
        PostMethod postMethod = new PostMethod(
                "http://172.16.21.166:8730/RfidService?wsdl");

        // 然后把Soap请求数据添加到PostMethod中
        byte[] b = soapRequestData.getBytes(StandardCharsets.UTF_8);
        InputStream is = new ByteArrayInputStream(b, 0, b.length);
        RequestEntity re = new InputStreamRequestEntity(is, b.length,
                "application/soap+xml; charset=utf-8");
        postMethod.setRequestEntity(re);

        // 最后生成一个HttpClient对象，并发出postMethod请求
        HttpClient httpClient = new HttpClient();
        int statusCode = httpClient.executeMethod(postMethod);
        if (statusCode == 200) {
            System.out.println("调用成功！");
            String soapResponseData = postMethod.getResponseBodyAsString();
            System.out.println(soapResponseData);
            //截取出json数据
            int sub = soapResponseData.indexOf("<GetTaxResult>");
            soapResponseData = soapResponseData.substring(sub).replace("<GetTaxResult>", "");
            sub = soapResponseData.indexOf("</GetTaxResult>");
            soapResponseData = soapResponseData.substring(0, sub);
            System.out.println(soapResponseData);


        } else {
            System.out.println("调用失败！错误码：" + statusCode);
        }

    }

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
        } catch (java.lang.Exception e) {
            System.out.println(111);
            e.printStackTrace();
        }
    }


    @Test
    public void testSend2() {

        // 创建动态客户端
        JaxWsProxyFactoryBean client = new JaxWsProxyFactoryBean();
        client.setAddress("http://172.16.21.166:8730/RfidService?wsdl");
        client.setServiceClass(IRfidService.class);
        IRfidService responseCla = (IRfidService) client.create();
        String jaxstr = responseCla.getRFIDDataXMLString();
        System.out.println(jaxstr);
    }

    @Test
    public void testSend3() {
        //1.代理桩工厂
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//        SoapBindingConfiguration soapBind=new SoapBindingConfiguration();
//        soapBind.setVersion(Soap12.getInstance());
//        factory.setBindingConfig(soapBind);
        //factory.setBindingId("http://www.w3.org/2003/05/soap/bindings/HTTP/");
        //2.设置WebService地址
        factory.setAddress("http://172.16.21.166:8730/RfidService?wsdl");
        //3.根据工厂创建本地代理对象（桩对象）
        IRfidService client = factory.create(IRfidService.class);
        //设置客户端的配置信息，超时等.
        Client proxy = ClientProxy.getClient(client);
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(30000); //连接超时时间
        policy.setReceiveTimeout(180000);//请求超时时间.
        conduit.setClient(policy);
        System.out.println(client.getRFIDDataXMLString());
    }

    /**
     * 可以
     *
     * @throws Exception
     */
    @Test
    public void testSend39() throws Exception {
        java.net.URL url = new java.net.URL("http://172.16.21.166:8730/RfidService?wsdl");
        QName serviceName = new QName("http://tempuri.org/", "RfidService");
        QName portName = new QName("http://tempuri.org/", "IRfidService");

        Service service = Service.create(url, serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, "http://172.16.21.166:8730/RfidService?wsdl");
        IRfidService servicePort = service.getPort(IRfidService.class);
        System.out.println(servicePort.getRFIDDataXMLString());
    }


    @Test
    public void testSend31() {
        /**
         * 以下方法可以调用成功 ：http://172.16.81.200:8003/Ws-Servers/service/mesPlmL2Wgt?wsdl
         */
        //1.代理桩工厂
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        //2.设置WebService地址
        factory.setAddress("http://172.16.81.200:8003/Ws-Servers/service/mesPlmL2Wgt?wsdl");
        //3.根据工厂创建本地代理对象（桩对象）
        MesPlmL2WgtService client = factory.create(MesPlmL2WgtService.class);
        System.out.println(client.insertACCEPT("1", "2", 45.3, 2.3, "2", "3", 6, 2));
    }


    @Test
    public void testSend3333() {
        /**
         * 以下方法可以调用成功 ：http://172.16.81.200:8003/Ws-Servers/service/mesPlmL2Wgt?wsdl
         */
        //1.代理桩工厂
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        //2.设置WebService地址
        factory.setAddress("http://172.16.21.166:8730/RfidService?wsdl");
        //3.根据工厂创建本地代理对象（桩对象）
        IRfidService client = factory.create(IRfidService.class);
        System.out.println(client.getRFIDDataXMLString());
    }

    @Test
    public void test4() {
        // 1). 客户端工厂类
        JaxWsProxyFactoryBean proxy = new JaxWsProxyFactoryBean();
        proxy.setBindingId("http://www.w3.org/2003/05/soap/bindings/HTTP/");
        // 2). 设置了两个属性
        proxy.setAddress("http://172.16.21.166:8730/RfidService");
        proxy.setServiceClass(IRfidService.class);

        // 添加输入&输出日志（可选）
//        proxy.getInInterceptors().add(new InInterceptor());
//        proxy.getOutInterceptors().add(new OutInterceptor());
// 3). 创建会发起一个http请求
        //GetRFIDDataXMLString  staticClient =  (GetRFIDDataXMLString) proxy.create(GetRFIDDataXMLString.class);
        IRfidService staticClient = proxy.create(IRfidService.class);
        //org.apache.cxf.jaxws.JaxWsClientProxy@6105f8a3
// 4). 调用方法，获得数据
        System.out.println(staticClient.getRFIDDataXMLString());
    }

    @Test
    public void test41() {
        // 1). 客户端工厂类
        JaxWsProxyFactoryBean proxy = new JaxWsProxyFactoryBean();
        proxy.setBindingId("http://www.w3.org/2003/05/soap/bindings/HTTP/");
        // 2). 设置了两个属性
        proxy.setAddress("http://172.16.21.166:8730/RfidService?wsdl");
        proxy.setServiceClass(IRfidService.class);

        Object o = proxy.create();
        IRfidService service = (IRfidService) o;
        System.out.println(service.getRFIDDataXMLString());
//        // 添加输入&输出日志（可选）
//        proxy.getInInterceptors().add(new InInterceptor());
//        proxy.getOutInterceptors().add(new OutInterceptor());
//
//// 3). 创建会发起一个http请求
//        IRfidService  staticClient =  (IRfidService) proxy.create();
//        //org.apache.cxf.jaxws.JaxWsClientProxy@6105f8a3
//// 4). 调用方法，获得数据
//        System.out.println(staticClient.getRFIDDataXMLString());
    }

    @Test
    public void test42() throws ClassNotFoundException {

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();

        Client client = dcf.createClient("http://172.16.21.166:8730/RfidService?wsdl");
//        Object person =Thread.currentThread().getContextClassLoader().loadClass("com.acme.Person").newInstance();
//        Method m = person.getClass().getMethod("setName", String.class);
//        m.invoke(person, "Joe Schmoe");
//        client.invoke("addPerson", person);
    }

    @Test
    public void test43() throws ClassNotFoundException {

        String endPointAddress = "http://172.16.21.166:8730/RfidService?wsdl";//服务实际地址
        //此处http://charles.com/为命名空间，默认是包名的倒序。IQueryUserService=服务接口名+service
        javax.xml.ws.Service service = javax.xml.ws.Service.create(new QName("http://tempuri.org/", "IRfidServiceService"));
//IQueryUserPort=服务接口名+Port
        service.addPort(new QName("http://charles.com/", "IQueryUserPort"), javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING, endPointAddress);
        IRfidService queryService1 = service.getPort(IRfidService.class);

        System.out.println(queryService1.getRFIDDataXMLString());

//        这里其实利用wsdl2java工具为我们生成类时已经包含了这种方法。比如我们的服务接口是IQueryUser，那么我们会发现生成的文件下有个IQueryUserService类
//        该类正是继承至javax.xml.ws.Service，并封装以上代码的功能，调用代码如下：
//        IQueryUserService queryUserService=new IQueryUserService();//默认构造函数里使用默认wsdl地址和服务的QName
//        IQueryUser queryService1 =queryUserService.getIQueryUserPort();//方法内部调用了super.getPort(IQueryUserPort, IQueryUser.class);
//        System.out.println(queryService1.query(user));

    }


    @Test
    public void test5() throws Exception {
        // 1). 根据接口定义url，获得Client
        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();

        Client dynamicClient = clientFactory.createClient("http://172.16.21.166:8730/RfidService?wsdl");

// 2). 调用接口，获得数据
//sayHiString是WebMethod中定义的接口名，不是方法名
        Object[] result = dynamicClient.invoke("IRfidService", "Get_RFID_Data_XML_String");
        System.out.println(result[0]);
        //result = dynamicClient.invoke("sayHiUser", new User("Cherry"));
        //System.out.println(result[0]);
    }

    @Test
    public void test6() {

        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
        Client client = clientFactory.createClient("http://172.16.21.166:8730/RfidService?wsdl");
        Object[] result = null;
        try {
            //如果有命名空间的话
//            QName operationName = new QName(namespaceURI,localPort); //如果有命名空间需要加上这个，第一个参数为命名空间名称，第二个参数为WebService方法名称
//            result = client.invoke(operationName,param1, param2);//后面为WebService请求参数数组
//            //如果没有命名空间的话
//            result = client.invoke(operationName, param1); //注意第一个参数是字符串类型，表示WebService方法名称，第二个参数为请求参数
        } catch (Exception e) {
            String errMsg = "WebService发生异常！";
            result = new Object[]{errMsg};
            //logger.error(errMsg, e);
        }


    }

    @Test
    public void test66() {
        HttpURLConnection httpConn;
        BufferedReader reader = null;

        try {
            httpConn = (HttpURLConnection) new URL("http://172.16.21.166:8730/RfidService?wsdl").openConnection();
            httpConn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8"); // 设置在header中
//            httpConn.setRequestProperty("username", params.getUsername());
//            httpConn.setRequestProperty("password", params.getPassword());
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.connect();

            // 获取服务器响应状态
            int code = httpConn.getResponseCode();
            String tempString = null;
            StringBuffer retMsg = new StringBuffer();
            if (code == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), StandardCharsets.UTF_8));
                while ((tempString = reader.readLine()) != null) {
                    retMsg.append(tempString);
                }
                System.out.println(retMsg);
                //return parseXML(retMsg.toString());
            } else {
                // return "";
            }
        } catch (Exception e) {
            //log.error("send soap error: ", e);
            //return "";
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log.error("close BufferedReader error: ", e);
                }
            }
        }

    }

//    /**
//     * 解析XML
//     *
//     * @param input
//     * @return
//     */
//    private String parseXML(String input) {
//        Document doc;
//        try {
//            doc = DocumentHelper.parseText(input);
//            // 获取根节点
//            Element root = doc.getRootElement();
//            return root.getText();
//        } catch (Exception e) {
//            log.error("parse xml error: ", e);
//            return "";
//        }


}
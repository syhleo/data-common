//package com.steer.data.webservice.client;
//
//import org.apache.axiom.om.OMAbstractFactory;
//import org.apache.axiom.om.OMElement;
//import org.apache.axiom.om.OMFactory;
//import org.apache.axiom.om.OMNamespace;
//import org.apache.axiom.soap.SOAP12Constants;
//import org.apache.axis2.AxisFault;
//import org.apache.axis2.Constants;
//import org.apache.axis2.addressing.EndpointReference;
//import org.apache.axis2.client.Options;
//import org.apache.axis2.client.ServiceClient;
//import org.apache.axis2.rpc.client.RPCServiceClient;
//import org.apache.axis2.transport.http.HTTPConstants;
//import org.junit.Test;
//
//import javax.xml.namespace.QName;
//import java.util.ArrayList;
//
//public class Axis2Client {
//
//    /**
//     * 方法一：
//     * 应用rpc的方式调用 这种方式就等于远程调用，
//     * 即通过url定位告诉远程服务器，告知方法名称，参数等， 调用远程服务，得到结果。
//     * 使用 org.apache.axis2.rpc.client.RPCServiceClient类调用WebService
//     *
//     【注】：
//
//     如果被调用的WebService方法有返回值 应使用 invokeBlocking 方法 该方法有三个参数
//     第一个参数的类型是QName对象，表示要调用的方法名；
//     第二个参数表示要调用的WebService方法的参数值，参数类型为Object[]；
//     当方法没有参数时，invokeBlocking方法的第二个参数值不能是null，而要使用new Object[]{}。
//     第三个参数表示WebService方法的 返回值类型的Class对象，参数类型为Class[]。
//
//
//     如果被调用的WebService方法没有返回值 应使用 invokeRobust 方法
//     该方法只有两个参数，它们的含义与invokeBlocking方法的前两个参数的含义相同。
//
//     在创建QName对象时，QName类的构造方法的第一个参数表示WSDL文件的命名空间名，
//     也就是 <wsdl:definitions>元素的targetNamespace属性值。
//     *
//     */
//    @Test
//    public  void testRPCClient() {
//        try {
//            // axis1 服务端
//// String url = "http://localhost:8080/StockQuote/services/StockQuoteServiceSOAP11port?wsdl";
//            // axis2 服务端
//            String url = "http://172.16.21.166:8730/RfidService?wsdl";
//
//            // 使用RPC方式调用WebService
//            RPCServiceClient serviceClient = new RPCServiceClient();
//            // 指定调用WebService的URL
//            EndpointReference targetEPR = new EndpointReference(url);
//            Options options = serviceClient.getOptions();
//            //options.setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
//            options.setManageSession(true);
//            //options.set
//            options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT,true);
//            //确定目标服务地址
//            options.setTo(targetEPR);
//            //确定调用方法
//            options.setAction("http://tempuri.org/IRfidService/Get_RFID_Data_XML_String");
//
//            /**
//             * 指定要调用的getPrice方法及WSDL文件的命名空间
//             * 如果 webservice 服务端由axis2编写
//             * 命名空间 不一致导致的问题
//             * org.apache.axis2.AxisFault: java.lang.RuntimeException: Unexpected subelement arg0
//             */
//            QName qname = new QName("http://tempuri.org/", "Get_RFID_Data_XML_String");
//            // 指定getPrice方法的参数值
//            //Object[] parameters = new Object[] { "13" };
//            Object[] parameters = new Object[] {  };
//            // 指定getPrice方法返回值的数据类型的Class对象
//            Class[] returnTypes = new Class[] { double.class };
//
//            // 调用方法一 传递参数，调用服务，获取服务返回结果集
//            //OMElement element = serviceClient.invokeBlocking(qname, parameters);
//            OMElement element = serviceClient.invokeBlocking(qname, parameters);
//            //值得注意的是，返回结果就是一段由OMElement对象封装的xml字符串。
//            //我们可以对之灵活应用,下面我取第一个元素值，并打印之。因为调用的方法返回一个结果
//            String result = element.getFirstElement().getText();
//            System.out.println(result);
//
////            // 调用方法二 getPrice方法并输出该方法的返回值
////            Object[] response = serviceClient.invokeBlocking(qname, parameters, returnTypes);
////            // String r = (String) response[0];
////            Double r = (Double) response[0];
////            System.out.println(r);
//
//        } catch (AxisFault e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void test3333(){
//         EndpointReference targetEPR = new EndpointReference("http://172.16.21.238:8730/RfidService?wsdl");
//        Options options = new Options();
//        options.setAction("http://tempuri.org/IRfidService/get_RFID_Data_XML_String");// 调用接口方法
//        options.setTo(targetEPR);
//        options.setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
//        options.setProperty(HTTPConstants.CHUNKED, "false");//设置不受限制.
//
//
//        ServiceClient sender = null;
//        try {
//            sender = new ServiceClient();
//            sender.setOptions(options);
//            OMFactory fac = OMAbstractFactory.getOMFactory();
//            OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "");
//            OMElement method = fac.createOMElement("GetSign", omNs);
//            OMElement name = fac.createOMElement("prestr", omNs);// 设置入参名称
//            OMElement name2 = fac.createOMElement("key", omNs);// 设置入参名称
//            name.setText("hawei");// 设置入参值
//            name2.setText("6181a1fb89564b589283ad578baa7d5e");
//            method.addChild(name);
//            method.addChild(name2);
//            method.build();
//            System.out.println("method：" + method.toString());
//            OMElement response = sender.sendReceive(method);
//            System.out.println("response:" + response);
//            OMElement elementReturn = response.getFirstElement();
//            System.out.println("cityCode:" + elementReturn.getText());
//        } catch (AxisFault e) {
//            System.out.println("Error");
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//    /**
//     *
//     */
//    @Test
//    public  void testRPCClient22222() {
//        try {
//            // axis1 服务端
//// String url = "http://localhost:8080/StockQuote/services/StockQuoteServiceSOAP11port?wsdl";
//            // axis2 服务端
//            String url = "http://172.16.21.166:8730/RfidService?wsdl";
//
//            // 使用RPC方式调用WebService
//            RPCServiceClient serviceClient = new RPCServiceClient();
//            // 指定调用WebService的URL
//            EndpointReference targetEPR = new EndpointReference(url);
//            Options options = serviceClient.getOptions();
//            //确定目标服务地址
//            options.setTo(targetEPR);
//            //确定调用方法
//            options.setAction("http://tempuri.org/IRfidService/Get_RFID_Data_XML_String");
//            //options.setAction("234eeafqewrqewd");也可以
//
//            /**
//             * 指定要调用的getPrice方法及WSDL文件的命名空间
//             * 如果 webservice 服务端由axis2编写
//             * 命名空间 不一致导致的问题
//             * org.apache.axis2.AxisFault: java.lang.RuntimeException: Unexpected subelement arg0
//             */
//            //QName qname = new QName("http://service.wgt.plm.com/", "insertACCEPT");
//            QName qname = new QName("http://tempuri.org/", "Get_RFID_Data_XML_String");
//            // 指定getPrice方法的参数值
//            //Object[] parameters = new Object[] { "13" };
//            Object[] parameters = new Object[] { "1","2",45.3,2.3,"2","3",6,2 };
//            // 指定getPrice方法返回值的数据类型的Class对象
//            Class[] returnTypes = new Class[] { double.class };
//
//            // 调用方法一 传递参数，调用服务，获取服务返回结果集
//            //OMElement element = serviceClient.invokeBlocking(qname, parameters);
//            OMElement element = serviceClient.invokeBlocking(qname, null);
//            //值得注意的是，返回结果就是一段由OMElement对象封装的xml字符串。
//            //我们可以对之灵活应用,下面我取第一个元素值，并打印之。因为调用的方法返回一个结果
//            String result = element.getFirstElement().getText();
//            System.out.println(result);
//
////            // 调用方法二 getPrice方法并输出该方法的返回值
////            Object[] response = serviceClient.invokeBlocking(qname, parameters, returnTypes);
////            // String r = (String) response[0];
////            Double r = (Double) response[0];
////            System.out.println(r);
//            System.out.println("调用成功");
//        } catch (AxisFault e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 可以成功
//     */
//    public static void testRPCClient2() {
//        try {
//            // axis1 服务端
//// String url = "http://localhost:8080/StockQuote/services/StockQuoteServiceSOAP11port?wsdl";
//            // axis2 服务端
//            String url = "http://172.16.81.200:8003/Ws-Servers/service/mesPlmL2Wgt?wsdl";
//
//            // 使用RPC方式调用WebService
//            RPCServiceClient serviceClient = new RPCServiceClient();
//            // 指定调用WebService的URL
//            EndpointReference targetEPR = new EndpointReference(url);
//            Options options = serviceClient.getOptions();
//            //确定目标服务地址
//            options.setTo(targetEPR);
//            //确定调用方法
//            options.setAction("");
//            //options.setAction("234eeafqewrqewd");也可以
//
//            /**
//             * 指定要调用的getPrice方法及WSDL文件的命名空间
//             * 如果 webservice 服务端由axis2编写
//             * 命名空间 不一致导致的问题
//             * org.apache.axis2.AxisFault: java.lang.RuntimeException: Unexpected subelement arg0
//             */
//            QName qname = new QName("http://service.wgt.plm.com/", "insertACCEPT");
//            // 指定getPrice方法的参数值
//            //Object[] parameters = new Object[] { "13" };
//            Object[] parameters = new Object[] { "1","2",45.3,2.3,"2","3",6,2 };
//            // 指定getPrice方法返回值的数据类型的Class对象
//            Class[] returnTypes = new Class[] { double.class };
//
//            // 调用方法一 传递参数，调用服务，获取服务返回结果集
//            //OMElement element = serviceClient.invokeBlocking(qname, parameters);
//            OMElement element = serviceClient.invokeBlocking(qname, parameters);
//            //值得注意的是，返回结果就是一段由OMElement对象封装的xml字符串。
//            //我们可以对之灵活应用,下面我取第一个元素值，并打印之。因为调用的方法返回一个结果
//            String result = element.getFirstElement().getText();
//            System.out.println(result);
//
////            // 调用方法二 getPrice方法并输出该方法的返回值
////            Object[] response = serviceClient.invokeBlocking(qname, parameters, returnTypes);
////            // String r = (String) response[0];
////            Double r = (Double) response[0];
////            System.out.println(r);
//            System.out.println("调用成功");
//        } catch (AxisFault e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 方法二： 应用document方式调用
//     * 用ducument方式应用现对繁琐而灵活。现在用的比较多。因为真正摆脱了我们不想要的耦合
//     */
//    public static void testDocument() {
//        try {
//            // String url = "http://localhost:8080/axis2ServerDemo/services/StockQuoteService";
//            String url = "http://172.16.21.238:8730/RfidService?wsdl";
//
//            Options options = new Options();
//            // 指定调用WebService的URL
//            EndpointReference targetEPR = new EndpointReference(url);
//            options.setTo(targetEPR);
//            // options.setAction("urn:getPrice");
//
//            ServiceClient sender = new ServiceClient();
//            sender.setOptions(options);
//
//
//            OMFactory fac = OMAbstractFactory.getOMFactory();
//            //String tns = "http://quickstart.samples/";
//            String tns = "http://172.16.21.238:8730/RfidService?wsdl";
//            // 命名空间，有时命名空间不增加没事，不过最好加上，因为有时有事，你懂的
//            OMNamespace omNs = fac.createOMNamespace(tns, "");
//
//            OMElement method = fac.createOMElement("Get_RFID_Data_XML_String", omNs);
//            //OMElement symbol = fac.createOMElement("symbol", omNs);
//            // symbol.setText("1");
//            //symbol.addChild(fac.createOMText(symbol, "Axis2 Echo String "));
//            //method.addChild(symbol);
//            method.build();
//
//            OMElement result = sender.sendReceive(method);
//
//            System.out.println(result);
//
//        } catch (AxisFault axisFault) {
//            axisFault.printStackTrace();
//        }
//    }
//
//    @Test
//    public  void testDocument2() {
//        try {
//            Options options = new Options();
//            options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
//            options.setAction("http://tempuri.org/" + "Get_RFID_Data_XML_String");
//            options.setTo(new EndpointReference("http://172.16.21.238:8730/RfidService?wsdl"));
//            options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
//
//            OMFactory fac = OMAbstractFactory.getOMFactory();
//            OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "");
//            OMElement data = fac.createOMElement("Get_RFID_Data_XML_String", omNs);
//            OMElement inner = fac.createOMElement("flightinfoid", omNs);
//            inner.setText("1");
//            data.addChild(inner);
//
//            ServiceClient sender = new ServiceClient();
//            sender.setOptions(options);
//            OMElement result = sender.sendReceive(data);
//
//
//        } catch (AxisFault axisFault) {
//            axisFault.printStackTrace();
//        }
//    }
//
//
//
//    /**
//     * 为SOAP Header构造验证信息，
//     * 如果你的服务端是没有验证的，那么你不用在Header中增加验证信息
//     *
//     * @param serviceClient
//     * @param tns 命名空间
//     * @param user
//     * @param passwrod
//     */
//    public void addValidation(ServiceClient serviceClient, String tns , String user, String passwrod) {
//        OMFactory fac = OMAbstractFactory.getOMFactory();
//        OMNamespace omNs = fac.createOMNamespace(tns, "nsl");
//        OMElement header = fac.createOMElement("AuthenticationToken", omNs);
//        OMElement ome_user = fac.createOMElement("Username", omNs);
//        OMElement ome_pass = fac.createOMElement("Password", omNs);
//
//        ome_user.setText(user);
//        ome_pass.setText(passwrod);
//
//        header.addChild(ome_user);
//        header.addChild(ome_pass);
//
//        serviceClient.addHeader(header);
//    }
//
//
//    /**
//     * 方法三：利用axis2插件生成客户端方式调用
//     *
//     */
////    @Test
////    public  void testCodeClient() {
////        try {
////            //String url = "http://172.16.21.238:8730/RfidService?wsdl";
////            String url = "http://172.16.21.238:8730/RfidService";
////            RfidServiceStub stub = new RfidServiceStub(url);
////            stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);
////            stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.REUSE_HTTP_CLIENT, Boolean.TRUE);
////
//////            GetPrice request = new GetPrice();
//////            request.setSymbol("ABCD");
////            //com.steer.data.webservice.rfid.RfidServiceStub.Get_RFID_Data_XML_String ss=new com.steer.data.webservice.rfid.RfidServiceStub.Get_RFID_Data_XML_String();
////            com.steer.data.webservice.rfid.RfidServiceStub.Get_RFID_Data_XML_String requ=new com.steer.data.webservice.rfid.RfidServiceStub.Get_RFID_Data_XML_String();
////            com.steer.data.webservice.rfid.RfidServiceStub.Get_RFID_Data_XML_StringResponse response = stub.get_RFID_Data_XML_String(requ);
////            System.out.println(response);
////        } catch (org.apache.axis2.AxisFault e) {
////            e.printStackTrace();
////        } catch (java.rmi.RemoteException e) {
////            e.printStackTrace();
////        }
////
////    }
//
//    @Test
//    public  void testCodeClient222() throws Exception{
////        String url = "http://172.16.21.238:8730/RfidService?wsdl";
////        String soapaction="http://tempuri.org/";   //域名，这是在server定义的
////        String methodName="Get_RFID_Data_XML_String";   //调用的方法名
////        //get_RFID_Data_XML_String
//////        web service
////        RfidServiceStub stub = new RfidServiceStub();
////        stub._getServiceClient().engageModule("addressing");
////        EndpointReference targetEPR = new EndpointReference(url);
////
////        //use setTo set the target to client instance's options
////        stub._getServiceClient().getOptions().setTo(targetEPR);
////        stub._getServiceClient().getOptions().setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
////        stub._getServiceClient().getOptions().setAction("http://tempuri.org/" + "Get_RFID_Data_XML_String");
////        stub._getServiceClient().getOptions().setTransportInProtocol(Constants.TRANSPORT_HTTP);
////
//////        method
////        RfidServiceStub.Get_RFID_Data_XML_String addressSearch = new RfidServiceStub.Get_RFID_Data_XML_String();
////
////        com.steer.data.webservice.rfid.RfidServiceStub.Get_RFID_Data_XML_StringResponse response=stub.get_RFID_Data_XML_String(addressSearch);
////
////        System.out.println(response);
//
////    	QueryServiceStub.JGetGIS getGis = new QueryServiceStub.JGetGIS();
//
//
//////        set parameter
////        addressSearch.setSAddress("武宁路");
////        addressSearch.setM_len(12);
//////		QueryServiceStub.ArrayOfString any = new QueryServiceStub.ArrayOfString();
//////		String[] arcNum = new String[2];
//////		arcNum[0] = "E";
//////		any.setString(arcNum);
//////		getGis.setAlComplex(any);
////
//////        call web service method
////        Service1Stub.AddressSearchResponse res = stub.addressSearch(addressSearch);
//////		QueryServiceStub.JGetGISResponse res = stub.jGetGIS(getGis);
////
//////        get result
////        Service1Stub.ArrayOfAddressR data = res.getAddressSearchResult();
//////		QueryServiceStub.ArrayOfJGisData data = res.getJGetGISResult();
////
////        Service1Stub.AddressR[] arrayAddressR = data.getAddressR();
//////		TODO
//////		QueryServiceStub.JGisData[] jgdArray = data.getJGisData();
////
////        for(int i = 0; i < arrayAddressR.length; i++){
////            Service1Stub.AddressR jgd = arrayAddressR[i];
////
//////			get data
////            String district = jgd.getM_District();
////            System.out.println(district);
////            String POIName = jgd.getM_POIName();
////            System.out.println(POIName);
////            String DoorPlate = jgd.getM_DoorPlate();
////            System.out.println(DoorPlate);
////            double Lon = jgd.getM_Lon();
////            System.out.println(Lon);
////            double Lat = jgd.getM_Lat();
////            System.out.println(Lat);
////
////
//////			TODO
////            XMLStreamReader xmlReader = jgd.getPullParser(new QName(soapaction, methodName));
////
////            /*
////             * ref: 深入剖析Axis2中参数为复杂自定义类型值
////             * http://blog.csdn.net/yuebinghaoyuan/article/details/8445885
////             */
////            StreamWrapper parser = new StreamWrapper(xmlReader);
////            StAXOMBuilder stAXOMBuilder = (StAXOMBuilder) OMXMLBuilderFactory.createStAXOMBuilder(OMAbstractFactory. getOMFactory(), parser);
////            OMElement element = stAXOMBuilder.getDocumentElement();//得到xml字符串
////            System. out.println(element);
////
////            /*
////             * ref
////             * http://blog.csdn.net/is_zhoufeng/article/details/8309392
////             */
////            while(xmlReader.hasNext()){
////                int point = xmlReader.next();
////
////                String localName = xmlReader.getLocalName();
////                String value = xmlReader.getAttributeValue(null, localName);
//////	            String eleName = xmlReader.getName().toString();
//////	            QName name = xmlReader.getName();
//////	            value = (String) xmlReader.getProperty(localName);
////                String a = "";
////
//////	            switch(point){
//////	            case XMLStreamReader.START_ELEMENT :{   //开始节点
//////	                eleName = xmlReader.getName().toString();
//////	                if("property".equals(eleName)){
//////	                    int attCount = xmlReader.getAttributeCount() ;
//////	                    for (int j = 0; j < attCount; j++) {
//////	                        System.out.printf("%s:%s\t" ,
//////	                        		xmlReader.getAttributeName(j) ,
//////	                        		xmlReader.getAttributeValue(j));
//////	                    }
//////	                    System.out.println();
//////	                }
//////	            }
//////	            case XMLStreamReader.END_ELEMENT :{   //结束节点
//////
//////	            }
//////	            case XMLStreamReader.CHARACTERS :{  //文本节点
//////	            }
//////
//////	            }
////            }
////        }
//
//    }
//
////    @Test
////    public void test555() throws Exception{
////
////        //change the authpolicy use the JCIFS_NTLMScheme.class---httpclient
//////        AuthPolicy.registerAuthScheme(AuthPolicy.NTLM, JCIFS_NTLMScheme.class);
//////
////        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
////        params.setDefaultMaxConnectionsPerHost(20); //根据您的要求/负载测试等设置值
////
////        MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();
////        multiThreadedHttpConnectionManager.setParams(params);
////        HttpClient httpClient =new HttpClient (multiThreadedHttpConnectionManager);
//////
//////        ConfigurationContext configurationContext = ConfigurationContextFactory.createConfigurationContextFromFileSystem("http://172.16.21.238:8730/RfidService?wsdl");
//////        configurationContext.setProperty(HTTPConstants.CACHED_HTTP_CLIENT,httpClient);
//////        PostMethod postMethod = new PostMethod(
//////                "http://172.16.21.238:8730/RfidService?wsdl");
////
////        // 然后把Soap请求数据添加到PostMethod中
//////        byte[] b = soapRequestData.getBytes("utf-8");
//////        InputStream is = new ByteArrayInputStream(b, 0, b.length);
//////        RequestEntity re = new InputStreamRequestEntity(is, b.length,
//////                "application/soap+xml; charset=utf-8");
////        //postMethod.setRequestEntity(re);
//////        int statusCode = httpClient.executeMethod(postMethod);
//////        //create basicAuth
//////        Authenticator basicAuth = new HttpTransportProperties.Authenticator();
//////        basicAuth.setDomain(".");
//////        basicAuth.setHost("192.168.60.204");
//////        basicAuth.setUsername("administrator");
//////        basicAuth.setPassword("password,1");
//////        basicAuth.setPreemptiveAuthentication(false);
////        //end create basicAuth
////
////        //create webservices client instance
////        RfidServiceStub stub = new RfidServiceStub();
////        //Not need this..scs._getServiceClient().getOptions().setUserName("administrator");
////        //Not need this..scs._getServiceClient().getOptions().setPassword("password,1");
////
////        //create EndpointReference
////        //this url 'http://192.168.60.204/ServerAPI/ServerCommandService.asmx?wsdl' is the webservie url that u want to conn
////        EndpointReference targetEPR = new EndpointReference("http://172.16.21.238:8730/RfidService?wsdl");
////
////        //use setTo set the target to client instance's options
////        stub._getServiceClient().getOptions().setTo(targetEPR);
////
////        //use setProperty set the basicAuth that u created just now for authentiction
////        //stub._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, basicAuth);
////        //*********>>>begin to call the function
////        /***************call login function in web service*******************/
////        stub._getServiceClient().getOptions().setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
////        //stub._getServiceClient().getOptions().setAction("http://tempuri.org/" + "Get_RFID_Data_XML_String");
////        stub._getServiceClient().getOptions().setTransportInProtocol(Constants.TRANSPORT_HTTP);
////        stub._getServiceClient().getOptions().setExceptionToBeThrownOnSOAPFault(false);
////        stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(600000L);
////        stub._getServiceClient().getOptions().setProperty(HTTPConstants.CHUNKED, false);
//////        method
////        RfidServiceStub.Get_RFID_Data_XML_String addressSearch = new RfidServiceStub.Get_RFID_Data_XML_String();
////
////        com.steer.data.webservice.rfid.RfidServiceStub.Get_RFID_Data_XML_StringResponse response=stub.get_RFID_Data_XML_String(addressSearch);
////
////        System.out.println(response);
////
////    }
//
//
//
//
//    @Test
//    public  void test4() throws Exception{
//
//        // Generate Client
//        RPCServiceClient serviceClient = new RPCServiceClient();
//        Options options = serviceClient.getOptions();
//
//// Generate Endpoint
//        String webserviceurl = "http://172.16.21.166:8730/RfidService?wsdl"; // for example.
//        EndpointReference targetEPR = new EndpointReference(webserviceurl);
//        options.setSoapVersionURI(org.apache.axiom.soap.SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
//        options.setTo(targetEPR);
//
//// Auto release transport.
//        options.setCallTransportCleanup(true);
//
//// Generate Action
//        String ns = "http://tempuri.org/";
//        String action = "Get_RFID_Data_XML_String";
//        QName opAction = new QName(ns, action);
//
//// Generate Reqest parameters
////        ReqBean reqObj = new ReqBean();
////        reqObj.setParam1("param1");
////        reqObj.setParam2("param2");
//        //Object[] opArgs = new Object[] { reqObj };
//        Object[] opArgs =null;
//        Class[] returnTypes = new Class[] { ArrayList.class };
//        Object[] response = null;
//        try {
//            response = serviceClient.invokeBlocking(opAction, opArgs, returnTypes);
//        } catch (AxisFault af) {
//            // Process exception.
//            af.printStackTrace();
//        }
//
//        ArrayList res = (ArrayList) response[0];
//
//
//    }
//
//
//
//
//    public static void main(String[] args) throws Exception{
//        //Axis2Client.testRPCClient();
//        //Axis2Client.test4();
//        //Axis2Client.testRPCClient2();//可以
//        //Axis2Client.testDocument();
//// StockQuoteClient.testDocument();
//        // StockQuoteClient.testCodeClient();
//
//    }
//}
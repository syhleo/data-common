//package com.steer.data.webservice.client;
//
//import org.apache.axis.Message;
//import org.apache.axis.MessageContext;
//import org.apache.axis.encoding.XMLType;
//import org.apache.axis.message.SOAPHeaderElement;
//import org.apache.axis.soap.SOAPConstants;
//
//import javax.xml.namespace.QName;
//import java.net.URL;
//
//public class AxisClient {
//
//    public static void test1() throws  Exception{
//
//        String url = "http://172.16.21.238:8730/RfidService?wsdl";
//
//        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
//        org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();
//        call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
//        call.setTargetEndpointAddress(new URL(url));
//        call.setUseSOAPAction(true);
//        call.setSOAPActionURI("http://tempuri.org/IRfidService/Get_RFID_Data_XML_String");
//        //call.setSOAPActionURI("http://tempuri.org/IRfidService");
//        String targetNamespace = "http://tempuri.org/";
//        QName qName = new QName(targetNamespace,"Get_RFID_Data_XML_String");
//
//        call.setOperationName(qName);
//
//
////      QName qName2 = new QName(targetNamespace, "");
//
//
//
//        //call.addParameter("text", null, ParameterMode.IN);
//
//
//        Object[] obj =null;
//        String message = (String) call.invoke( obj);
//
//        System.out.println(message);
//    }
//
//    public static void test2(){
//        try {
//            String url = "http://172.16.21.238:8730/RfidService?wsdl";
//            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
//            org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();
//            call.setTargetEndpointAddress(new java.net.URL(url));
//
//            call.setUseSOAPAction(true);
//            call.setSOAPActionURI("http://tempuri.org/"+"Get_RFID_Data_XML_String");
//            call.setOperationName(new QName("http://tempuri.org/", "Get_RFID_Data_XML_String"));
//            //call.addParameter(new QName("http://tempuri.org/", "Get_RFID_Data_XML_String"), XMLType.XSD_ANY, javax.xml.rpc.ParameterMode.OUT);
//            //call.setReturnType(XMLType.XSD_STRING);
//            call.setUseSOAPAction(true);
//            SOAPHeaderElement soapHeaderElement = new SOAPHeaderElement("http://tempuri.org/", "Header");
//            soapHeaderElement.setPrefix("");
//            soapHeaderElement.setNamespaceURI("http://tempuri.org/");
//            call.addHeader(soapHeaderElement);
//            call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
//            //Object[] obj = new Object[] { "" };
//            Object[] obj = null;
//            String str = (String) call.invoke(obj);
//
//            MessageContext messageContext = call.getMessageContext();
//            //请求报文
//            Message reqMsg = messageContext.getRequestMessage();
//            //返回报文
//            Message repMsg = messageContext.getResponseMessage();
//            System.out.println(str);
////            logger.info(methodName + "请求报文：" + StringEscapeUtils.unescapeHtml(reqMsg.getSOAPPartAsString()));
////            logger.info(methodName + "返回报文：" + obj);
////            logger.info(methodName + "返回报文：" + StringEscapeUtils.unescapeHtml(repMsg.getSOAPPartAsString()));
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
//
//    public static void test3() throws Exception{
//
////添加接口规范字段
//
//
//
//       // Object[] params = new Object[]{100010010, "/user/oop", 3, 100, 13569698990};
//        Object[] params =null;
//        //String url = "http://172.16.21.238:8730/RfidService?wsdl";
//        String url = "http://172.16.21.238:8730/RfidService";
//
//        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
//        org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();
//        call.setTargetEndpointAddress(new java.net.URL(url));
//
//        call.setUseSOAPAction(true);
////这里是targetNamespace + 方法名称
//        call.setSOAPActionURI("http://tempuri.org/" + "Get_RFID_Data_XML_String");
//        //call.setSOAPActionURI("http://tempuri.org/IRfidService/Get_RFID_Data_XML_String");
//        //call.setSOAPActionURI("");
//
//        call.setOperationName(new QName("http://tempuri.org/", "Get_RFID_Data_XML_String"));
//
//        SOAPHeaderElement soapHeaderElement = new SOAPHeaderElement("http://tempuri.org/", "Header");
//        soapHeaderElement.setPrefix("");
//        soapHeaderElement.setNamespaceURI("http://tempuri.org/");
//
////这里是添加SOAP头，否则接口外层校验不过
////        try {
////            soapHeaderElement.addChildElement("UserName").setValue("admin");
////            soapHeaderElement.addChildElement("PassWord").setValue("password");
////            soapHeaderElement.addChildElement("RoleId").setValue(1);
////        } catch (SOAPException e) {
////            e.printStackTrace();
////        }
//
//        call.addHeader(soapHeaderElement);
//        call.setReturnType(XMLType.SOAP_STRING);
//        call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
//
////开始调对方接口
//        Object obj = call.invoke(params);
//        MessageContext messageContext = call.getMessageContext();
////请求报文
//        Message reqMsg = messageContext.getRequestMessage();
////返回报文
//        Message repMsg = messageContext.getResponseMessage();
//
//
//    }
//
//
//    public static void main(String[] args) throws  Exception{
//        //test1();
//        //test2();
//        test3();
//    }
//
//}

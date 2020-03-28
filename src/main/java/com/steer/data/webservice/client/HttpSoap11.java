//package com.steer.data.webservice.client;
//
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.methods.RequestEntity;
//import org.w3c.dom.Document;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//
//public class HttpSoap11 {
//
//    public void initSoap(String arg1,String arg2,String arg3,String arg4,String method,String xmlns) {
//        String soapRequestData = "<?xml version=\"1.0\"       encoding=\"utf-8\"?>"
//                + "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
//                + " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""
//                + " xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
//                + " <soap12:Body>"
//                + "<"+method+""+"xmlns="+"\""+xmlns+"\""
////                + " <Login xmlns=\"我的项目\">"
//                + "<"+arg1+">"+arg2+"</"+arg1+">"
//                + "<"+arg3+">"+arg4+"</"+arg3+">"
////                + " <UserName>"+"YQPIS0670"+"</UserName>"
////                + " <PassWord>"+"YQPIS0670"+"</PassWord>"
//                + " </Login>"
//                + "</soap12:Body>"
//                + " </soap12:Envelope>";
//    }
//
//
//    public static void main(String[] args){
//
//        // TODO Auto-generated method stub
//        PostMethod postMethod = new PostMethod(
//                "http://172.16.21.166:8730/RfidService?wsdl");
//        // 然后把Soap请求数据添加到PostMethod中
//        byte[] b = null;
//        try {
//            b = soapRequestData.getBytes("utf-8");
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        InputStream is = new ByteArrayInputStream(b, 0, b.length);
//        RequestEntity re = new InputStreamRequestEntity(is,
//                b.length, "application/soap+xml; charset=utf-8");
//        postMethod.setRequestEntity(re);
//        // 最后生成一个HttpClient对象，并发出postMethod请求
//        HttpClient httpClient = new HttpClient();
//        try {
//            int statusCode = httpClient.executeMethod(postMethod);
//            if (statusCode == 200) {
//                Log.d("soapRequestData", "调用成功！");
//                StringBuffer buffer = new StringBuffer();
//                // 解析器 工厂类
//                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                DocumentBuilder db = dbf.newDocumentBuilder();
//                //返回流式数据
//                InputStream soapResponseData = postMethod
//                        .getResponseBodyAsStream();
//                Document dm = db.parse(soapResponseData);
//                // element和node是同一概念
//                // 不同的是element提供更多方法
//                if (dm.getElementsByTagName("Root").item(0)
//                        .getFirstChild() != null) {
//                    // j是Root即根节点下面节点个数
//                    for (int j = 0; j < dm  .getElementsByTagName("Root").item(0)
//                            .getChildNodes().getLength(); j++) {
//                        String result3 = dm.getElementsByTagName("Root")                                 .item(0).getChildNodes().item(j).getTextContent();
//                        buffer.append(result3);
//                    }
//                }
//            } else {
//                Log.d("soapRequestData", "调用失败！错误码：" + statusCode);
//            }
//        } catch (HttpException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (ParserConfigurationException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SAXException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    }
//
//
//}

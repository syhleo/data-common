package com.steer.data.webservice.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClie {

    public static String sendSoapPost(String url, String xml,
                                      String contentType, String soapAction) {
        String urlString = url;
        HttpURLConnection httpConn = null;
        OutputStream out = null;
        String returnXml = "";
        try {
            httpConn = (HttpURLConnection) new URL(urlString).openConnection();
            httpConn.setRequestProperty("Content-Type", contentType);
            if (null != soapAction) {
                httpConn.setRequestProperty("SOAPAction", soapAction);
            }
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.connect();
            out = httpConn.getOutputStream(); // 获取输出流对象
            httpConn.getOutputStream().write(xml.getBytes(StandardCharsets.UTF_8)); // 将要提交服务器的SOAP请求字符流写入输出流
            out.flush();
            out.close();
            int code = httpConn.getResponseCode(); // 用来获取服务器响应状态
            String tempString = null;
            StringBuffer sb1 = new StringBuffer();
            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(httpConn.getInputStream(),
                                StandardCharsets.UTF_8));
                while ((tempString = reader.readLine()) != null) {
                    sb1.append(tempString);
                }
                if (null != reader) {
                    reader.close();
                }
            } else {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(httpConn.getErrorStream(),
                                StandardCharsets.UTF_8));
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    sb1.append(tempString);
                }
                if (null != reader) {
                    reader.close();
                }
            }
            // 响应报文
            returnXml = sb1.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnXml;
    }

    public static void main(String[] args) {
        String url = "http://172.16.21.166:8730/RfidService?wsdl";

        StringBuilder sb = new StringBuilder();
        String soapRequestData = "<?xml version=\"1.0\"       encoding=\"utf-8\"?>"
                + "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""
                + " xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
                + " <soap12:Body>"
                + "<" + "Get_RFID_Data_XML_String" + "" + "xmlns=" + "\"" + "http://tempuri.org/" + "\""
                + "</soap12:Body>"
                + " </soap12:Envelope>";
        /*
         * sb.append(
         * "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
         * ) .append(
         * "xmlns:web=\"http://WebXml.com.cn/\"><soapenv:Header/><soapenv:Body><web:getSupportCity>"
         * ) .append(
         * "<web:byProvinceName>河北</web:byProvinceName></web:getSupportCity></soapenv:Body></soapenv:Envelope>"
         * );
         */
        String dataXml = soapRequestData;
        String contentType = "application/soap+xml; charset=utf-8";
        String soapAction = "http://tempuri.org/IRfidService/Get_RFID_Data_XML_String";
        // String soapAction =
        // "\"document/http://pengjunnlee.com/CustomUI:GetWeatherById\"";
        String resultXml = HttpClie.sendSoapPost(url, dataXml, contentType,
                soapAction);
        System.out.println(resultXml);
    }
}

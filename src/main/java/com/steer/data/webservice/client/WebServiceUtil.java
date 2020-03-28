package com.steer.data.webservice.client;


import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

//import org.apache.log4j.Logger;


/**
 * webservice发送请求工具类
 * Author:lsf
 * Date:19-01-08
 */
public class WebServiceUtil {
    static int socketTimeout = 30000;// 请求超时时间
    static int connectTimeout = 30000;// 传输超时时间
    static Logger logger = LoggerFactory.getLogger(WebServiceUtil.class);

    public static void main(String[] args) {
        String url = "http://172.16.21.166:8730/RfidService?wsdl";

        String soapRequestData = "<?xml version=\"1.0\"       encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""
                + " xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
                + " <soap:Body>"
                + "<" + "Get_RFID_Data_XML_String" + "" + " xmlns=" + "\"" + "http://tempuri.org/>"
                + "</Get_RFID_Data_XML_String>"
                + "</soap:Body>"
                + " </soap:Envelope>";
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
        String resultXml = doPostSoap(url, dataXml,
                soapAction);
        System.out.println(resultXml);
    }


    /**
     * 使用SOAP发送消息（HttpClient方式）
     *
     * @param postUrl
     * @param soapXml
     * @param soapAction
     * @return
     */
    public static String doPostSoap(String postUrl, String soapXml,
                                    String soapAction) {
        String retStr = "";
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(postUrl);
        //  设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);
        try {
            //httpPost.setHeader("Content-Type", "application/soap+xml;charset=UTF-8");
            httpPost.setHeader("Content-Type", "text/xml");
            httpPost.setHeader("SOAPAction", soapAction);
            StringEntity data = new StringEntity(soapXml,
                    Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient
                    .execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                retStr = EntityUtils.toString(httpEntity, "UTF-8");
                logger.info("response:" + retStr);
            }
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            logger.error("请求出错！", e);
        }
        return retStr;
    }

    /**
     * 使用HttpURLConnection方式连接
     *
     * @param soapurl
     * @param soapXML
     * @return
     * @throws IOException
     */
    public static String urlConnectionUtil(String soapurl, String soapXML) throws IOException {
        //第一步：创建服务地址
        //http://172.25.37.31:8080/PeopleSoftService/getPerson.wsdl
        URL url = new URL(soapurl);
        //第二步：打开一个通向服务地址的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //第三步：设置参数
        //3.1发送方式设置：POST必须大写
        connection.setRequestMethod("POST");
        //3.2设置数据格式：content-type
        connection.setRequestProperty("content-type", "text/xml;charset=utf-8");
        //3.3设置输入输出，因为默认新创建的connection没有读写权限，
        connection.setDoInput(true);
        connection.setDoOutput(true);
        //第四步：组织SOAP数据，发送请求
        //将信息以流的方式发送出去
        OutputStream os = connection.getOutputStream();
        os.write(soapXML.getBytes());
        StringBuilder sb = new StringBuilder();
        //第五步：接收服务端响应，打印
        int responseCode = connection.getResponseCode();
        if (200 == responseCode) {//表示服务端响应成功
            //获取当前连接请求返回的数据流
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

//            StringBuilder sb = new StringBuilder();
            String temp = null;
            while (null != (temp = br.readLine())) {
                sb.append(temp);
            }
            is.close();
            isr.close();
            br.close();
        }
        os.close();
        return sb.toString();
    }

}

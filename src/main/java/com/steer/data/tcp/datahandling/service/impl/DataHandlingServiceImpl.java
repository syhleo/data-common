package com.steer.data.tcp.datahandling.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.steer.data.common.utils.DataAnal;
import com.steer.data.common.utils.RestfulHttpClient;
import com.steer.data.tcp.dametadata.service.IDAMetaDataService;
import com.steer.data.tcp.datahandling.message.Message;
import com.steer.data.tcp.datahandling.message.TpmMessage;
import com.steer.data.tcp.datahandling.message.ZtlMessage;
import com.steer.data.tcp.datahandling.service.DataHandlingService;
import com.steer.data.tcp.netty.client.ConnectionMap;
import com.steer.data.tcp.netty.client.NettyClientBootstrap;
import io.netty.channel.socket.SocketChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DataHandlingServiceImpl implements DataHandlingService {

    @Autowired
    @Qualifier(value = "DAMetaDataServiceImpl")
    IDAMetaDataService DAMetaDataService;

    NettyClientBootstrap bootstrap = null;

    Logger logger = LoggerFactory.getLogger(this.getClass());

//	@Autowired
//	IDaErrorMsgService daErrorMsgService;

//	@Value("${acq.testleo}")
//	String sdfsdfsdfsdf;


    @Value("${L2.ip}")
    String ip;
    @Value("${L2.port}")
    String port;
    @Value("${nettyserver.port}")
    String nettyserver_port;
    @Value("${acq.deviceName}")
    String deviceName;
    @Value("${acq.tpmTail}")
    String tpmTail;
    @Value("${acq.ztlTail}")
    String ztlTail;
    @Value("${acq.type}")
    String type;
    //netty服务端分隔符，为空时不按分隔符接收,用于netty对于拆包，粘包处理
    @Value("${acq.tail}")
    String tail;
    @Value("${acq.sendDC}")
    String sendDC;
    @Value("${acq.receiceDC}")
    String receiceDC;

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    /**
     * 把JSON数据转成成电文
     *
     * @param ifName  电文标识
     * @param dataMap 电文数据
     * @throws Exception
     */
    @Override
    public String toMsg(String ifName, HashMap<String, Object> dataMap) throws Exception {
        String msg = "";
        try {
            logger.info("L3下发数据： 接口名：" + ifName + "    数据：" + DataAnal.ToJson(dataMap));

            List<HashMap<String, String>> fieldList = DAMetaDataService.getFieldData(ifName);
            // 封装电文体
            String body = this.JsonToMsg(fieldList, dataMap);
            // 封装电文
            Message message;

            switch (deviceName) {
                case "tpm":
                    message = new TpmMessage(ifName, body, tpmTail, sendDC, receiceDC);
                    break;
                case "ztl":
                    message = new ZtlMessage(ifName, body, ztlTail, sendDC, receiceDC);
                    break;
                default:
                    throw new Exception(deviceName + "设备解析类型不存在");
            }
            msg = message.enCodeMsg();
        } catch (Exception e) {
            logger.error("L3--->L2： 接口名：" + ifName + "    数据解析失败，请检查元数据");
            logger.error("出错原因:" + e.getMessage());
            throw new Exception("数据解析失败 出错原因" + e.getMessage());
        }
        return msg;

    }

    @Override
    /**
     * 把json数据转换成电文发送给L2
     * @param ifName  电文标识
     * @param dataMap 电文数据
     */
    public String sendToL2(String ifName, HashMap<String, Object> dataMap) throws Exception {
        // 返回结果
        String result = "";
        // 把JSON数据转成成电文
        String msg = this.toMsg(ifName, dataMap);
        // 获取map中的连接，并判断连接是否活跃
        try {
            if (!ConnectionMap.conMap.containsKey("connection1")) {
                try {
                    bootstrap = new NettyClientBootstrap(Integer.valueOf(port), ip);
                } catch (Exception e) {
                    bootstrap.getSocketChannel().close();
                    bootstrap.getSocketChannel().closeFuture();
                    throw new Exception("与L2通讯失败！ " + ip + ":" + port);
                }
                ConnectionMap.conMap.put("connection1", bootstrap.getSocketChannel());
            } else if (!ConnectionMap.conMap.get("connection1").isActive()) {
                throw new Exception("与L2通讯失败！ " + ip + ":" + port);
            }
            SocketChannel socketChannel = ConnectionMap.conMap.get("connection1"); // 获取连接信息
            Thread.sleep(100L);
            logger.info("L3下发数据封装后报文： 接口名：" + ifName + "    数据：" + msg);
            String sendMsg = new String(msg.getBytes(StandardCharsets.UTF_8), "GB2312");
            socketChannel.writeAndFlush(sendMsg);

            result = "接口名：" + ifName + "       数据：" + msg + "   下发成功_vsuccess---";
        } catch (Exception e) {
            logger.error("L3--->L2： 接口名：" + ifName + "      数据发送失败");
            logger.error("出错原因： ", e);
            throw new Exception("数据发送失败！ " + ip + ":" + port);
        }
        return result;

    }

    @Override
    @SuppressWarnings({"rawtypes"})
    /**
     * 把电文解析成json格式数据
     *
     * @param msg     电文数据
     * @param message 电文结构体
     */
    public void toJson(String ifName, String body) throws Exception {
        String sendMsg = ""; // 解析后的数据
        String urlSuffix = ""; // 调用三级接口url
        try {
            // 获取元数据信息，包括：字段名、类型、长度等
            List<HashMap<String, String>> fieldList = DAMetaDataService.getFieldData(ifName);
            // 获取url的后缀
            urlSuffix = DAMetaDataService.getUrl(ifName);
            // 将电文转成Json，用于后面传给L3
            HashMap map = this.MsgToJson(fieldList, body);
            List<HashMap> list = new ArrayList<HashMap>();
            list.add(map);
            sendMsg = DataAnal.ToJson(list);
            logger.info("L2电文解析后数据： 接口名：" + ifName + "   数据：" + sendMsg);
        } catch (Exception e) {
            throw new Exception("接口名：ifName，电文解析时出错" + e.getMessage());
        }
        sendToL3(sendMsg, urlSuffix, ifName);

    }

    /**
     * 把解析好的json数据发送给L3
     *
     * @param msg    json数据
     * @param url    L3接口地址
     * @param ifName 接口标识
     * @throws Exception
     */
    public void sendToL3(String msg, String url, String ifName) throws Exception {
        RestfulHttpClient.HttpResponse response;
        if (StringUtils.isEmpty(url)) {
            throw new Exception("数据发送失败  出错原因: L3接口url不能为空");
        }
        try {
            logger.info("L3url:" + "http://" + url);
            response = RestfulHttpClient.getClient("http://" + url).post() // 设置post请求
                    .addPostParam("body", "json").body(msg).addHeader("Content-columnType", "application/json") // 添加header
                    .request();

            if (response.getCode() == 200) {
                logger.info("L2发送电文成功    L3返回:" + response.getContent());
            }
        } catch (IOException e) {
            logger.error("L2--->L3： 接口名：" + ifName + "     数据发送失败");
            throw new Exception("数据发送失败  出错原因:" + e.getMessage());
        } // 发起请求
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    /**
     * 根据元数据把电文体转换HashMap
     *
     * @param fieldList 元数据
     * @param body      电文数据
     */
    public HashMap MsgToJson(List<HashMap<String, String>> fieldList, String body) throws Exception {
        HashMap tempMap = new HashMap();
        try {
            List<Integer> lenList = new ArrayList<Integer>();
            for (HashMap<String, String> hashMap : fieldList) {
                if (hashMap == null)
                    continue;
                lenList.add(Integer.valueOf(hashMap.get("COLUMN_LENGTH").trim()));
            }
            // 根据各个字段长度和电文体拆分
            List<String> dataList = DataAnal.DataDecode(lenList, body);

            for (int i = 0; i < fieldList.size(); i++) {
                String key = fieldList.get(i).get("COLUMN_NAME").trim();
                String columnType = fieldList.get(i).get("COLUMN_TYPE").trim();
                String value = dataList.get(i);
                // key值统一用小写
                String keyLow = key.toLowerCase();
                if (columnType.equals("Long")) {
                    try {
                        tempMap.put(keyLow.trim(), DataAnal.LongToString(value.trim()));
                    } catch (Exception e) {
                        throw new Exception("字段 :" + key + "转换成数字时出错" + e.getMessage());
                    }
                } else if (columnType.equals("String")) {
                    tempMap.put(keyLow.trim(), value.trim());
                } else if (columnType.equals("Double")) {
                    String dp = fieldList.get(i).get("DECIMAL_PLACE");
                    tempMap.put(keyLow.trim(), DataAnal.deAccuracy(value, Integer.valueOf(dp)));
                } else if (columnType.equals("Object")) {
                    // 获取元数据
                    List<HashMap<String, String>> childList = DAMetaDataService
                            .getChildField(fieldList.get(i).get("INTERFACE_NAME"), key);
                    // 递归调用
                    this.MsgToJson(childList, value);
                } else if (columnType.equals("List")) {
                    List<Integer> childlenList = new ArrayList<Integer>();
                    // 获取数组长度
                    int num = Integer.valueOf(fieldList.get(i).get("LIST_NUM"));
                    List<String> valueList = new ArrayList<String>();

                    for (int k = 0; k < num; k++) {
                        int len = value.length();
                        int l = len / num;
                        valueList.add(value.substring(l * k, l * (k + 1)));
                    }

                    List<HashMap> resultList = new ArrayList<HashMap>();
                    List<HashMap<String, String>> childList = DAMetaDataService
                            .getChildField(fieldList.get(i).get("INTERFACE_NAME"), key);

                    for (int j = 0; j < childList.size(); j++) {
                        childlenList.add(Integer.valueOf(childList.get(j).get("COLUMN_LENGTH")));
                    }

                    for (String string : valueList) {
                        resultList.add(this.parser(childList, childlenList, string));
                    }
                    tempMap.put(keyLow, resultList);
                } else {
                    throw new Exception("字段: " + key + "  字段类型：" + columnType + " 无匹配的字段类型");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("数据解析失败 出错原因：" + e.getMessage());
        }
        return tempMap;

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    /**
     * 解析数组类型的电文
     *
     * @param fieldList 元数据
     * @param lenList   字段长度集合
     * @param body      电文数据
     */
    public HashMap parser(List<HashMap<String, String>> fieldList, List<Integer> lenList, String body)
            throws Exception {

        List<String> dataList = DataAnal.DataDecode(lenList, body);
        HashMap tempMap = new HashMap();
        for (int i = 0; i < fieldList.size(); i++) {
            String key = fieldList.get(i).get("COLUMN_NAME");
            String columnType = fieldList.get(i).get("COLUMN_TYPE");
            String value = dataList.get(i);

            String keyLow = key.toLowerCase();

            if (columnType.equals("Long")) {
                tempMap.put(keyLow, DataAnal.LongToString(value.trim()));
            } else if (columnType.equals("String")) {
                tempMap.put(keyLow, value.trim());
            } else if (columnType.equals("Object")) {
                List<HashMap<String, String>> childList = DAMetaDataService
                        .getChildField(fieldList.get(i).get("INTERFACE_NAME"), key);
                // 递归调用
                this.MsgToJson(childList, value);
            } else if (columnType.equals("List")) {
                List<Integer> childlenList = new ArrayList<Integer>();
                int num = Integer.valueOf(fieldList.get(i).get("LIST_NUM"));
                List<String> valueList = new ArrayList<String>();

                for (int k = 0; k < num; k++) {
                    int len = value.length();
                    int l = len / num;
                    valueList.add(value.substring(l * k, l * (k + 1)));
                }

                List<HashMap> resultList = new ArrayList<HashMap>();
                List<HashMap<String, String>> childList = DAMetaDataService
                        .getChildField(fieldList.get(i).get("INTERFACE_NAME"), key);

                for (int j = 0; j < childList.size(); j++) {
                    childlenList.add(Integer.valueOf(childList.get(j).get("COLUMN_LENGTH")));
                }

                for (String string : valueList) {
                    resultList.add(this.parser(childList, childlenList, string));
                }
                tempMap.put(keyLow, resultList);
            }
        }
        return tempMap;
    }

    @SuppressWarnings({"unchecked", "static-access", "rawtypes"})
    /**
     * 把Json数据转换成电文
     *
     * @param fieldList 元数据，包括字段类型，字段长度，字段
     * @param dataMap   输入数据
     */
    public String JsonToMsg(List<HashMap<String, String>> fieldList, HashMap<String, Object> dataMap) throws Exception {
        String result = "";
        for (HashMap<String, String> fieldHashMap : fieldList) {

            String name = fieldHashMap.get("COLUMN_NAME").trim().toLowerCase();
            String columnType = fieldHashMap.get("COLUMN_TYPE");
            if (columnType.equals("List")) {
                String temp = "";
                List<HashMap<String, String>> childList = DAMetaDataService
                        .getChildField(fieldHashMap.get("INTERFACE_NAME"), name.toUpperCase());
                String s = dataMap.get(name).toString();
                JSONArray arry = new JSONArray();
                List<HashMap> DataList = arry.parseArray(s, HashMap.class);
                for (HashMap hashMap : DataList) {
                    temp = temp + this.JsonToMsg(childList, hashMap);
                }
                result = result + temp;
            } else if (columnType.equals("Double")) {
                String dp = fieldHashMap.get("DECIMAL_PLACE");
                Integer length = Integer.valueOf(fieldHashMap.get("COLUMN_LENGTH"));
                Double dou = DataAnal.deAccuracy(dataMap.get(name).toString(), Integer.valueOf(dp));
                result = result + DataAnal.DataEncode(dou, length);
            } else {
                Integer length = Integer.valueOf(fieldHashMap.get("COLUMN_LENGTH"));
                result = result + DataAnal.DataEncode(dataMap.get(name).toString(), length, Integer.valueOf(type));
            }

        }
        return result;
    }

    @Override
    public String getTestData(String ifName) throws Exception {
        String result = "";
        List<HashMap<String, String>> fieldList = DAMetaDataService.getFieldData(ifName);
        for (HashMap<String, String> fieldHashMap : fieldList) {
            String name = fieldHashMap.get("COLUMN_NAME").trim().toLowerCase();
            String columnType = fieldHashMap.get("COLUMN_TYPE");
            Integer columnLen = Integer.valueOf(fieldHashMap.get("COLUMN_LENGTH"));

            if (columnType.equals("List") || columnType.equals("String")) {
                if (columnLen == 1) {
                    result = result + "1";
                } else if (columnLen <= name.length()) {
                    result = result + name.substring(0, columnLen);
                } else if (columnLen > name.length()) {
                    result = result + DataAnal.DataEncode(name, columnLen, 0);
                }
            }
            if (columnType.equals("Long")) {
                result = result + DataAnal.DataEncode(1L, columnLen, 0);
            }
        }
        TpmMessage tpmMessage = new TpmMessage();
        tpmMessage.setBody(result);
        tpmMessage.setMsgNo(ifName);
        result = tpmMessage.enCodeMsg();
        return result;
    }

    public String getPort() {
        return this.port;
    }

    public String getIp() {
        return this.ip;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getTpmTail() {
        return this.tpmTail;
    }

    public String getZtlTail() {
        return this.ztlTail;
    }

    public String getNettyserver_port() {
        return nettyserver_port;
    }


}

package com.steer.data.tcp.datahandling.message;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class ZtlDecode implements Decode {

    Logger logger = (Logger) LoggerFactory.getLogger(ZtlDecode.class);

    @Override
    public Message deCode(String msg) throws Exception {
        List<String> list2 = this.MsgDecode(msg);

        // 解析电文
        ZtlMessage message = new ZtlMessage();

        message.setMsgLen(list2.get(0).trim());
        message.setMsgNo(list2.get(1).trim());
        message.setDateDay(list2.get(2));
        message.setDateTime(list2.get(3));
        message.setSendDC(list2.get(4));
        message.setReceiveDC(list2.get(5));
        message.setSeqNo(list2.get(6));
        message.setReserved(list2.get(7));
        message.setBody(list2.get(9));

        return message;
    }

    //把电文字符串拆分成Message对象
    public List<String> MsgDecode(String body) throws Exception {

        try {
            List<String> resultList = new ArrayList<String>();
            resultList.add(body.substring(0, 4));
            resultList.add(body.substring(4, 10));
            resultList.add(body.substring(10, 18));
            resultList.add(body.substring(18, 24));
            resultList.add(body.substring(24, 26));
            resultList.add(body.substring(26, 28));
            resultList.add(body.substring(28, 32));
            resultList.add(body.substring(32, 34));
            resultList.add(body.substring(34, 42));
            resultList.add(body.substring(42, body.length() - 1));
            return resultList;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}

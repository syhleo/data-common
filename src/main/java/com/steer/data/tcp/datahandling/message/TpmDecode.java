package com.steer.data.tcp.datahandling.message;

import ch.qos.logback.classic.Logger;
import com.steer.data.common.utils.DataAnal;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TpmDecode implements Decode {

    Logger logger = (Logger) LoggerFactory.getLogger(TpmDecode.class);

    @Override
    public TpmMessage deCode(String msg) throws Exception {

        Integer len = Integer.valueOf(msg.substring(0, 4).trim());
        // 校验电文长度
        if (len != msg.length() && len != msg.length() + 1) {
            //logger.error("电文数据丢失，接收长度与约定长度不符");
            throw new Exception("电文数据丢失，接收长度与约定长度不符");
        }
        List<String> list2 = DataAnal.MsgDecode(msg);

        // 解析电文
        TpmMessage message = new TpmMessage();

        message.setMsgLen(list2.get(0).trim());
        message.setMsgNo(list2.get(1).trim());
        message.setDateDay(list2.get(2));
        message.setDateTime(list2.get(3));
        message.setSendDC(list2.get(4));
        message.setReceiveDC(list2.get(5));
        message.setSeqNo(list2.get(6));
        message.setReserved(list2.get(7));
        message.setBody(list2.get(8));

        return message;
    }

}

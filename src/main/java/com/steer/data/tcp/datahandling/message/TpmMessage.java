package com.steer.data.tcp.datahandling.message;

import com.steer.data.common.utils.DataAnal;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TpmMessage implements Message {

    private String msgLen;

    private String msgNo;

    private String dateDay;

    private String dateTime;

    private String sendDC;

    private String receiveDC;

    private String seqNo;

    private String block;

    private String reserved;

    private String body;

    public static Integer msgCounter = 0;

    //结束符
    private String tail;

    public TpmMessage() {
    }

    public TpmMessage(String msgNo, String body, String tail) {
        this.msgNo = msgNo;
        this.body = body;
        this.tail = tail;
    }

    public TpmMessage(String msgNo, String body, String tail, String sendDC, String receiveDC) {
        this.msgNo = msgNo;
        this.body = body;
        this.tail = tail;
        this.sendDC = sendDC;
        this.receiveDC = receiveDC;
    }

    public String getMsgLen() {
        return msgLen;
    }

    public void setMsgLen(String msgLen) {
        this.msgLen = msgLen;
    }

    public String getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(String msgNo) {
        this.msgNo = msgNo;
    }

    public String getDateDay() {
        return dateDay;
    }

    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSendDC() {
        return sendDC;
    }

    public void setSendDC(String sendDC) {
        this.sendDC = sendDC;
    }

    public String getReceiveDC() {
        return receiveDC;
    }

    public void setReceiveDC(String receiveDC) {
        this.receiveDC = receiveDC;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    @Override
    public String enCodeMsg() throws Exception {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(new Date());
        str = str.replace("-", "").replace(":", "").replace(" ", "");
        String result = "";
        try {
            if (TpmMessage.msgCounter > 9999) TpmMessage.msgCounter = 9999;
            result = DataAnal.DataEncode(String.valueOf(this.getBody().length() + 41), 4, 0) +
                    DataAnal.DataEncode(this.getMsgNo(), 6, 0) +
                    DataAnal.DataEncode(str.substring(0, 8), 8, 0) +
                    DataAnal.DataEncode(str.substring(8, 14), 6, 0) +
                    DataAnal.DataEncode(this.getSendDC(), 2, 0) +
                    DataAnal.DataEncode(this.getReceiveDC(), 2, 0) +
                    DataAnal.DataEncode(TpmMessage.msgCounter, 4, 0) +
                    DataAnal.DataEncode("       ", 8, 0) +
                    this.getBody() +
                    DataAnal.hexStringToString("0A");
            TpmMessage.msgCounter++;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("电文TpmMessage对象构建出错" + e.getMessage());
        }
        return result;
    }


}

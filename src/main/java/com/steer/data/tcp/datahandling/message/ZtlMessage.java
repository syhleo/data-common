package com.steer.data.tcp.datahandling.message;

import com.steer.data.common.utils.DataAnal;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ZtlMessage implements Message {

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

    private String tail;

    public ZtlMessage() {

    }

    public ZtlMessage(String msgNo, String body, String tail) {
        this.msgNo = msgNo;
        this.body = body;
        this.tail = tail;
    }

    public ZtlMessage(String msgNo, String body, String tail, String sendDC, String receiveDC) {
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

    @Override
    public String enCodeMsg() throws Exception {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(new Date());
        str = str.replace("-", "").replace(":", "").replace(" ", "");
        String seq = String.valueOf((int) (Math.random() * (9999 - 1000 + 1)) + 1000);
        String result =
                this.DataEncode(String.valueOf(this.getBody().length() + 43), 4) +
                        this.DataEncode(this.getMsgNo(), 6) +
                        this.DataEncode(str.substring(0, 8), 8) +
                        this.DataEncode(str.substring(8, 14), 6) +
                        this.DataEncode(this.getSendDC(), 2) +
                        this.DataEncode(this.getReceiveDC(), 2) +
                        this.DataEncode(seq, 4) +
                        this.DataEncode("00", 2) +
                        this.DataEncode("        ", 8) +
                        this.getBody() +
                        DataAnal.hexStringToString(this.tail);
        return result;

    }

    public String DataEncode(String data, int i) {
        if (data == null) {
            data = " ";
        }
        data = String.format("%1$-" + i + "s", data);
        return data;
    }

    //数字左补0，返回为字符串
    public String DataEncode(Long data, int i) {
        if (data == null) {
            data = 0L;
        }
        String temp = String.format("%0" + i + "d", data);
        return temp;
    }

    //数字左补0，返回为字符串
    public String DataEncode(Integer data, int i) {
        if (data == null) {
            data = 0;
        }
        String temp = String.format("%0" + i + "d", data);
        return temp;
    }

    //数字左补0，返回为字符串
    public String DataEncode(Double data, int i) {
        if (data == null) {
            data = 0.0;
        }
        String temp = String.format("%0" + i + "f", data);
        return temp;
    }

}

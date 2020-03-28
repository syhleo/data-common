package com.steer.data.tcp.datahandling.message;

public interface Message {

    String enCodeMsg() throws Exception;

    String getMsgNo();

    String getBody();
}

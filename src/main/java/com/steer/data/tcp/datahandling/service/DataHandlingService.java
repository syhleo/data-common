package com.steer.data.tcp.datahandling.service;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestBody;

public interface DataHandlingService {

    String toMsg(String ifName, @RequestBody HashMap<String, Object> dataMap) throws Exception;

    void toJson(String ifName, String body) throws Exception;

    String sendToL2(String ifName, HashMap<String, Object> dataMap) throws Exception;

    String getTestData(String ifName) throws Exception;
}

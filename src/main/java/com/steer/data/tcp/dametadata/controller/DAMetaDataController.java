package com.steer.data.tcp.dametadata.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.steer.data.tcp.dametadata.service.IDAMetaDataService;


@RestController
@RequestMapping("/${api.version}/DAMetaDatas")
public class DAMetaDataController {

    @Autowired
    @Qualifier(value = "DAMetaDataServiceImpl")
    IDAMetaDataService DAMetaDataService;

    @PostMapping(value = "/")
    public List<HashMap<String, String>> getFieldData(String interfaceName, String deviceName) {
        List<HashMap<String, String>> list = DAMetaDataService.getFieldData(interfaceName);
        return list;
    }

    @PostMapping(value = "/getUrl/")
    public String getUrl(String interfaceName, String deviceName) {
        String url = DAMetaDataService.getUrl(interfaceName);
        return url;
    }

    @PostMapping(value = "/getChildField")
    public List<HashMap<String, String>> getChildField(String interfaceName, String pColumnName) {
        List<HashMap<String, String>> list = DAMetaDataService.getChildField(interfaceName, pColumnName);
        return list;
    }

}

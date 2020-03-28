package com.steer.data.tcp.dametadata.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steer.data.tcp.dametadata.mapper.DAMetaDataMapper;
import com.steer.data.tcp.dametadata.service.IDAMetaDataService;


@Service
public class DAMetaDataServiceImpl implements IDAMetaDataService {

    @Autowired
    private DAMetaDataMapper DAMetaDataMapper;


    @Override
    public List<HashMap<String, String>> getFieldData(String interfaceName) {
        List<HashMap<String, String>> list = DAMetaDataMapper.getFieldData(interfaceName);
        return list;
    }

    @Override
    public String getUrl(String interfaceName) {
        String url = DAMetaDataMapper.getUrl(interfaceName);
        return url;
    }

    @Override
    public List<HashMap<String, String>> getChildField(String interfaceName, String pColumnName) {
        List<HashMap<String, String>> list = DAMetaDataMapper.getChildField(interfaceName, pColumnName);
        return list;
    }

}

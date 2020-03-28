package com.steer.data.tcp.dametadata.service;

import java.util.HashMap;
import java.util.List;


public interface IDAMetaDataService {

    public List<HashMap<String, String>> getFieldData(String interfaceName);

    public String getUrl(String interfaceName);

    public List<HashMap<String, String>> getChildField(String interfaceName, String pColumnName);
}
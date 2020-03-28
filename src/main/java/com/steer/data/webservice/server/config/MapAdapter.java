/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.webservice.server.config.MapAdapter.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月22日上午9:31:07
 * <p>
 * 负责人: syhleo
 * <p>
 * 部门: 工程服务部
 * <p>
 * 修改者：（修改者姓名）
 * <p>
 * 修改时间：
 * <p>
 * 说明：
 * <p>
 */


package com.steer.data.webservice.server.config;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MapAdapter extends XmlAdapter<MapConvertor, Map<String, Object>> {

    @Override
    public MapConvertor marshal(Map<String, Object> map) throws Exception {
        MapConvertor convertor = new MapConvertor();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            MapConvertor.MapEntry e = new MapConvertor.MapEntry(entry);
            convertor.addEntry(e);
        }
        return convertor;
    }

    @Override
    public Map<String, Object> unmarshal(MapConvertor map) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        for (MapConvertor.MapEntry e : map.getEntries()) {
            result.put(e.getKey(), e.getValue());
        }
        return result;
    }
}


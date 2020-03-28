/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.webservice.server.config.MapConvertor.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月22日上午9:34:02
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "MapConvertor")
@XmlAccessorType(XmlAccessType.FIELD)
public class MapConvertor {
    private List<MapEntry> entries = new ArrayList<MapEntry>();

    public void addEntry(MapEntry entry) {
        entries.add(entry);
    }

    public void setEntries(List<MapEntry> entries) {
        this.entries = entries;
    }

    public List<MapEntry> getEntries() {
        return entries;
    }

    public static class MapEntry {

        private String key;

        private Object value;

        public MapEntry() {
            super();
        }

        public MapEntry(Map.Entry<String, Object> entry) {
            super();
            this.key = entry.getKey();
            this.value = entry.getValue();
        }

        public MapEntry(String key, Object value) {
            super();
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}



/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.db.DynamicRoutingDataSource.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月14日下午4:45:50
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


package com.steer.data.db.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicRoutingDataSource.class);

    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.get();
    }
}


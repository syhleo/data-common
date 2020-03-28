/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.db.DynamicDataSourceContextHolder.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月14日下午4:46:39
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

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataSourceContextHolder {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    private static final ThreadLocal<DataSourceKey> currentDatesource = new ThreadLocal();

    public static void clear() {
        currentDatesource.remove();
    }

    public static DataSourceKey get() {
        return currentDatesource.get();
    }

    public static void set(DataSourceKey value) {
        currentDatesource.set(value);
    }

    public static void setSlave() {
        if (RandomUtils.nextInt(0, 2) > 0)
            set(DataSourceKey.DB_SLAVE2);
        else
            set(DataSourceKey.DB_SLAVE1);
    }
}

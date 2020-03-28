/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.db.DynamicDataSourceAspect.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月14日下午4:28:13
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

import java.lang.reflect.Method;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Pointcut("execution(* com.steer.sysmanage.sysuser.service..*.*(..))")
    public void pointCut() {
    }

    @Before("@annotation(targetDataSource)")
    public void doBefore(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        DataSourceKey dataSourceKey = targetDataSource.dataSourceKey();
        if (dataSourceKey == DataSourceKey.DB_OTHER) {
            LOG.info(String.format("设置数据源为  %s", DataSourceKey.DB_OTHER));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_OTHER);
        } else if (dataSourceKey == DataSourceKey.DB_MASTER) {
            LOG.info(String.format("使用默认数据源  %s", DataSourceKey.DB_MASTER));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_MASTER);
        } else if (dataSourceKey == DataSourceKey.DB_SLAVE1) {
            LOG.info(String.format("设置数据源为  %s", DataSourceKey.DB_SLAVE1));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE1);
        } else if (dataSourceKey == DataSourceKey.DB_SLAVE2) {
            LOG.info(String.format("设置数据源为  %s", DataSourceKey.DB_SLAVE2));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE2);
        } else if (dataSourceKey == DataSourceKey.DB_SLAVE3) {
            LOG.info(String.format("设置数据源为  %s", DataSourceKey.DB_SLAVE3));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE3);
        } else if (dataSourceKey == DataSourceKey.DB_SLAVE4) {
            LOG.info(String.format("设置数据源为  %s", DataSourceKey.DB_SLAVE4));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE4);
        } else if (dataSourceKey == DataSourceKey.DB_SLAVE5) {
            LOG.info(String.format("设置数据源为  %s", DataSourceKey.DB_SLAVE5));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE5);
        } else if (dataSourceKey == DataSourceKey.DB_SLAVE6) {
            LOG.info(String.format("设置数据源为  %s", DataSourceKey.DB_SLAVE6));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE6);
        } else if (dataSourceKey == DataSourceKey.DB_SLAVE7) {
            LOG.info(String.format("设置数据源为  %s", DataSourceKey.DB_SLAVE7));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE7);
        } else if (dataSourceKey == DataSourceKey.DB_SLAVE8) {
            LOG.info(String.format("设置数据源为  %s", DataSourceKey.DB_SLAVE8));
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE8);
        }
    }

    @After("@annotation(targetDataSource)")
    public void doAfter(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        LOG.info(String.format("当前数据源  %s  执行清理方法", targetDataSource.dataSourceKey()));
        DynamicDataSourceContextHolder.clear();
    }

    @Before("pointCut()")
    public void doBeforeWithSlave(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Method method = methodSignature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass()
                        .getDeclaredMethod(joinPoint
                                .getSignature().getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                LOG.error("方法不存在！", e);
            }
        }
        if (null == method.getAnnotation(TargetDataSource.class))
            DynamicDataSourceContextHolder.setSlave();
    }
}
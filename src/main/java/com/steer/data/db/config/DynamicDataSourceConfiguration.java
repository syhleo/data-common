/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.DynamicDataSourceConfiguration.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月14日下午4:47:10
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

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@MapperScan(basePackages = {"com.steer.data.**"})
@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
public class DynamicDataSourceConfiguration {

//  @Value("${genxml.pth}")
//  private String genxmlPath;

    @Bean
    @ConfigurationProperties(prefix = "multiple.datasource.master")
    @Primary
    public DataSource dbMaster() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "multiple.datasource.slave1")
    public DataSource dbSlave1() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "multiple.datasource.slave2")
    public DataSource dbSlave2() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "multiple.datasource.slave3")
    public DataSource dbSlave3() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "multiple.datasource.slave4")
    public DataSource dbSlave4() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "multiple.datasource.slave5")
    public DataSource dbSlave5() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "multiple.datasource.slave6")
    public DataSource dbSlave6() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "multiple.datasource.slave7")
    public DataSource dbSlave7() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "multiple.datasource.slave8")
    public DataSource dbSlave8() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "multiple.datasource.other")
    public DataSource dbOther() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = {"primaryDatasource"})
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setDefaultTargetDataSource(dbMaster());
        Map dataSourceMap = new HashMap(4);
        dataSourceMap.put(DataSourceKey.DB_MASTER, dbMaster());
        dataSourceMap.put(DataSourceKey.DB_SLAVE1, dbSlave1());
        dataSourceMap.put(DataSourceKey.DB_SLAVE2, dbSlave2());
        dataSourceMap.put(DataSourceKey.DB_SLAVE3, dbSlave3());
        dataSourceMap.put(DataSourceKey.DB_SLAVE4, dbSlave4());
        dataSourceMap.put(DataSourceKey.DB_SLAVE5, dbSlave5());
        dataSourceMap.put(DataSourceKey.DB_SLAVE6, dbSlave6());
        dataSourceMap.put(DataSourceKey.DB_SLAVE7, dbSlave7());
        dataSourceMap.put(DataSourceKey.DB_SLAVE8, dbSlave8());
        dataSourceMap.put(DataSourceKey.DB_OTHER, dbOther());
        dataSource.setTargetDataSources(dataSourceMap);
        return dataSource;
    }

    @Bean(name = {"primarySqlSessionFactory"})
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //sqlSessionFactoryBean.setPlugins(new Interceptor[] { new MyBatisInterceptor(), new CommonInterceptor() });

        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCallSettersOnNulls(true);
        sqlSessionFactoryBean.setConfiguration(configuration);

        Resource[] mapperLocations1 = new PathMatchingResourcePatternResolver().getResources("classpath*:com/steer/mapper/*.xml");
        //Resource[] mapperLocations2 = new PathMatchingResourcePatternResolver().getResources("classpath*:" + this.genxmlPath + "*.xml");
        //int length1 = mapperLocations1.length;
        //int length2 = mapperLocations2.length;
        //mapperLocations1 = (Resource[])Arrays.copyOf(mapperLocations1, length1 + length2);
        //System.arraycopy(mapperLocations2, 0, mapperLocations1, length1, length2);

        sqlSessionFactoryBean.setMapperLocations(mapperLocations1);

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    @Bean(name = {"tm"})
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}

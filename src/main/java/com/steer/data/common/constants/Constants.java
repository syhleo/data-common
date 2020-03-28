/***文档注释***********************************************
 * 作者               :                   易鑫
 * 创建日期      :                   2017.05.18
 * 描述               :                   读取配置文件基础数据接口类
 * 注意事项      :                   无
 * 遗留BUG :                   无
 * 修改日期      :                   
 * 修改人员      :                   
 * 修改内容      :                   
 ***********************************************************/

package com.steer.data.common.constants;


import com.steer.data.common.utils.PropertiesUtils;

public interface Constants {
    /**
     * 读取配置文件中，数据库类型
     */
    String DB_TYPE = PropertiesUtils.getProperties().getProperty("DB_TYPE");

    /**
     * 心跳间隔
     */
    int HEART_TIME = Integer.parseInt(PropertiesUtils.getProperties().getProperty("HEART_TIME")) * 1000;

    /**
     * 心跳多少次后断开重连
     */
    int HEART_COUNT = Integer.parseInt(PropertiesUtils.getProperties().getProperty("HEART_COUNT"));

    /**
     * 心跳电文
     */
    String HEART_MSG = PropertiesUtils.getProperties().getProperty("HEART_MSG");

    /**
     * 本机端口
     */
    int PORT = Integer.parseInt(PropertiesUtils.getProperties().getProperty("PORT"));

    /**
     * 读取配置文件中，编码格式
     */
    String ENCODE = PropertiesUtils.getProperties().getProperty("ENCODE");

    /**
     * 缓冲区大小
     * 一个电文最大不能超过该大小
     */
    int BUFFER_SIZE = Integer.parseInt(PropertiesUtils.getProperties().getProperty("BUFFER_SIZE"));

    /**
     * 读取错误日志路径
     */
    String ErrorLogUrl = PropertiesUtils.getProperties().getProperty("ErrorLogUrl");

    /**
     * 错误后是否重新发送 0为不重传，1为重发
     */
    String RETRANSMISSION = PropertiesUtils.getProperties().getProperty("RETRANSMISSION");

    /**
     * 若重传则需要配置哪些状态重新发送，若多个值用英文逗号分隔  [解析失败值5、插表失败值6]
     */
    String RETRANSMISSION_VALUE = PropertiesUtils.getProperties().getProperty("RETRANSMISSION");

    //------------------------------数据库配置-----------------------------------------
    /**
     * 读取数据库驱动
     */
    String driver = PropertiesUtils.getDbProperties().getProperty("ds.driver");

    /**
     * 读取数据库url
     */
    String url = PropertiesUtils.getDbProperties().getProperty("ds.url");

    /**
     * 读取数据库username
     */
    String username = PropertiesUtils.getDbProperties().getProperty("ds.username");

    /**
     * 读取数据库password
     */
    String password = PropertiesUtils.getDbProperties().getProperty("ds.password");


}

package com.steer.data.quartz.service;


import com.steer.data.quartz.model.LoggingEvent;

import java.util.List;

/**
 * <p>
 * quartz定时器动态执行的任务信息 服务类
 * </p>
 *
 * @author syhleo
 * @since 2019-09-16
 */
public interface LoggingEventService {


    List<LoggingEvent> findAll();

    List<LoggingEvent> findByParams(String params);

    void insert(LoggingEvent loggingEvent);

}

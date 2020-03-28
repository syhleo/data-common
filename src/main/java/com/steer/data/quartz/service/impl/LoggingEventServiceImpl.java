package com.steer.data.quartz.service.impl;

import com.steer.data.quartz.mapper.LoggingEventMapper;
import com.steer.data.quartz.model.LoggingEvent;
import com.steer.data.quartz.service.LoggingEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * LoggingEvent日志信息 服务实现类
 * </p>
 *
 * @author syhleo
 * @since 2019-09-16
 */
@Service
public class LoggingEventServiceImpl implements LoggingEventService {

    @Autowired
    private LoggingEventMapper loggingEventMapper;

    @Override
    public List<LoggingEvent> findAll() {
        return loggingEventMapper.findAll();
    }

    @Override
    public List<LoggingEvent> findByParams(String params) {
        return loggingEventMapper.findByParams(params);
    }

    @Override
    public void insert(LoggingEvent loggingEvent) {
        loggingEventMapper.insert(loggingEvent);
    }

}

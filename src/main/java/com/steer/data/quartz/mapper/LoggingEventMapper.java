package com.steer.data.quartz.mapper;

import com.steer.data.quartz.model.LoggingEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoggingEventMapper {

    List<LoggingEvent> findAll();

    List<LoggingEvent> findByParams(@Param("parames") String params);

    void insert(LoggingEvent job);
}

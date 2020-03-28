package com.steer.data.quartz.mapper;

import com.steer.data.quartz.model.QuartzJobEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * quartz定时器动态执行的任务信息 Mapper 接口
 * </p>
 *
 * @author syhleo
 * @since 2019-09-16
 */
@Mapper
public interface QuartzJobEntityMapper {

    /**
     * 本项目配置了多数据源，此处需特别留意是哪个数据源在操作sql语句，在相应的Service实现类中指定，默认是multiple.datasource.master数据源，
     */

    List<QuartzJobEntity> findAll();

    QuartzJobEntity findByJobId(@Param("job_id") Integer job_id);

    List<QuartzJobEntity> findByParams(@Param("parames") String params);

    void updateByJobId(QuartzJobEntity job);

    List<QuartzJobEntity> findByJobName(@Param("jobName") String jobName);
}

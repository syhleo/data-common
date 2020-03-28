package com.steer.data.db.datahandling.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.steer.data.db.datahandling.model.DbMetadataD;

/**
 * <p>
 * 数据库-元数据表明细信息，面向于整个Map移除、新增、交互数据 Mapper 接口
 * </p>
 *
 * @author syhleo
 * @since 2019-09-16
 */
public interface DbMetadataDMapper {

    List<DbMetadataD> findByDbrela(@Param("db_relate") String db_relate);

}

package com.steer.data.db.datahandling.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.steer.data.db.datahandling.model.DbMetadataM;

/**
 * <p>
 * 数据库-元数据主表信息 Mapper 接口
 * </p>
 *
 * @author syhleo
 * @since 2019-09-16
 */
@Mapper
public interface DbMetadataMMapper {

    DbMetadataM findByIdValid(@Param("db_id") Integer parseInt);

}

package com.steer.data.tcp.dametadata.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DAMetaDataMapper {

    List<HashMap<String, String>> getFieldData(@Param("interfaceName") String interfaceName);

    String getUrl(@Param("interfaceName") String interfaceName);

    List<HashMap<String, String>> getChildField(@Param("interfaceName") String interfaceName,
                                                @Param("pColumnName") String pColumnName);

}

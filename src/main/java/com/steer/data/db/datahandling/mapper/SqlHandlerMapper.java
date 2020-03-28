package com.steer.data.db.datahandling.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;

@Mapper
public interface SqlHandlerMapper {

    @Insert("insert into GB_CAIJI_TAB(ID,XIHAO,IN_TIME,WRK_ORD," +
            "PRODUCT_NAME,LUHAO,DINGCHIJS,DINGCHIWT," +
            "FEIDINGJS,FEIDINGWT,CIPINGJS,CIPINGWT) values(#{id},#{xuhao},#{reqi},#{banci}," +
            "#{pingming},#{luhao},#{dingchijs},#{dingchizl},#{feidingcjs},#{feidingzl},#{cipjs},#{cpwt})  ")
    void insertData(@Param("id")int id, @Param("xuhao")int 序号, @Param("reqi")Date 日期,
                    @Param("banci")String 班次, @Param("pingming")String 品名, @Param("luhao")String 炉号,
                    @Param("dingchijs") int 定尺件数,@Param("dingchizl") float 定尺重量, @Param("feidingcjs")int 非定尺件数,
                    @Param("feidingzl")float 非定尺重量, @Param("cipjs")int 次品件数, @Param("cpwt")float 次品重量);

    @Delete("delete from GB_CAIJI_TAB where IN_TIME=#{rqda} and XIHAO=#{xuhao} and WRK_ORD=#{banci} and LUHAO=#{luhao} ")
    void deleteByDate(@Param("rqda")Date 日期, @Param("xuhao")int 序号, @Param("banci")String 班次, @Param("luhao")String 炉号);


}

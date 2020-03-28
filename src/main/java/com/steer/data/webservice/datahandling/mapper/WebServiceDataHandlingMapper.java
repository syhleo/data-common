package com.steer.data.webservice.datahandling.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface WebServiceDataHandlingMapper {

    @Select(" select t.gou_hao  from (" +
            "  select s.*,row_number() over(order by insert_time desc) as rwnum from ${tab_name} s where 1=1 " +
            "  ) t" +
            "  where t.rwnum=#{rownumcount}")
    List<String> getGouHao(@Param("tab_name") String tab_name, @Param("rownumcount") Integer rownumcount);

    @Select(" select t.* from ( select s.*,row_number() over(order by insert_time desc) as rwnum from ${tab_name} s " +
            " where 1=1 ) t where rwnum=#{rownumcount} ")
    List<HashMap> getRfidMsg(@Param("tab_name") String tab_name, @Param("rownumcount") Integer rownumcount);

    @Select("SELECT mgp.batchno,pgr.batchseq,pgr.BATCHQUANTITY,pgr.AIM_DMT,t.hook_no,t.unload_coilno,t.load_coilno coilno,mgp.steelcode from MIL_GX_HOOKS_INFO t " +
            " left join MIL_GX_POTHOOKRESULTS mgp on t.load_coilno =mgp.OBJECTNO " +
            " left join PPC_GX_ROLLBATCHPLAN pgr on mgp.batchno=pgr.batchno  where t.hook_no=#{gou_hao} order by t.create_time desc ")
    List<HashMap> getGxHookMsg(@Param("gou_hao") Object gou_hao);

    @Select(" select * from PPC_GX_ROLLBATCHPLAN t where t.batchseq > #{seqbatch} order by t.batchseq ")
    List<HashMap> getNext1(@Param("seqbatch") Object seqbatch);


    @Select(" select t.* from (" +
            "select s.BATCHNO,s.STEELCODE,s.BATCHSEQ,s.BATCHQUANTITY,s.AIM_DMT,s.ROLLED_CNT,(s.BATCHQUANTITY-s.ROLLED_CNT) as shengyzhishu,row_number() over(order by create_time desc) as rwnum " +
            " from PPC_GX_ROLLBATCHPLAN s where s.status=3 and s.ROLLED_CNT>0 and s.BATCHQUANTITY>0 and (s.BATCHQUANTITY-s.ROLLED_CNT>0) " +
            ") t " +
            " where rwnum=#{rownumcount} ")
    List<HashMap> getJzMsg(@Param("rownumcount") Integer rownumcount);

    @Select(" select t.gou_hao from ${tab_name} t where t.gou_hao=#{gou_hao} and ( t.insert_time between sysdate-10/(24*60) and sysdate) ")
    List<String> getGouhao5Min(@Param("tab_name") String tab_name, @Param("gou_hao") Object gou_hao);

    @Select("select * from RTD_GX_ROLLING1 ")
    List<HashMap> getWenDu();

}

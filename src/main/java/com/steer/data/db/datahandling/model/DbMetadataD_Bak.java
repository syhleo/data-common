/***文档注释***********************************************
 * 作者            ：
 * 创建日期     ：2019-09-09
 * 描述            ：数据库-元数据表明细信息，面向于整个Map移除、新增、交互数据
 * 注意事项     ：
 * 遗留BUG   ：
 * 修改日期     ：
 * 修改人员     ：
 * 修改内容     ：
 ***********************************************************/
package com.steer.data.db.datahandling.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 面向于整个Map移除、新增、交互数据
 *
 * @author syhleo
 */
public class DbMetadataD_Bak implements Serializable {


//	@Override
//    public boolean equals(Object obj) {
//        if(this == obj){
//            return true;//地址相等
//        }
//        if(obj == null){
//            return false;//非空性：对于任意非空引用x，x.equals(null)应该返回false。
//        }
//        if(obj instanceof DbMetadataD){
//        	DbMetadataD other = (DbMetadataD) obj;
//            //需要比较的字段相等，则这两个对象相等
//            if(equalsStr(this.source_table_column, other.source_table_column)){
//                return true;
//            }
//        }
//        return false;
//    }
//    private boolean equalsStr(String str1, String str2){
//        if(ObjectUtil.isEmpty(str1) && ObjectUtil.isEmpty(str2)){
//            return true;
//        }
//        if(!ObjectUtil.isEmpty(str1) && str1.equals(str2)){
//            return true;
//        }
//        return false;
//    }

//    @Override
//    public int hashCode() {
//        int result = 17;
//        result = 31 * result + (source_table_column == null ? 0 : source_table_column.hashCode());
//        return result;
//    }


    /**
     * 关联相应的数据库主表/Map信息
     */

    private String db_relate;

    public String getDb_relate() {
        return this.db_relate;
    }

    public void setDb_relate(String db_relate) {
        this.db_relate = db_relate;
    }

    /**
     * 源数据库表或者Map里面字段
     */

    private String source_table_column;

    public String getSource_table_column() {
        return this.source_table_column;
    }

    public void setSource_table_column(String source_table_column) {
        this.source_table_column = source_table_column;
    }

    /**
     * 目标数据库表字段
     */

    private String target_table_column;

    public String getTarget_table_column() {
        return this.target_table_column;
    }

    public void setTarget_table_column(String target_table_column) {
        this.target_table_column = target_table_column;
    }

    /**
     * 无效标识，默认是0，（0:有效，1:无效，禁用），用于禁用参与对接的字段
     */

    private String invalid_flg;

    public String getInvalid_flg() {
        return this.invalid_flg;
    }

    public void setInvalid_flg(String invalid_flg) {
        this.invalid_flg = invalid_flg;
    }

    /**
     * 创建人
     */

    private String create_opr;

    public String getCreate_opr() {
        return this.create_opr;
    }

    public void setCreate_opr(String create_opr) {
        this.create_opr = create_opr;
    }

    /**
     * 创建时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    public Date getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 修改人
     */

    private String update_opr;

    public String getUpdate_opr() {
        return this.update_opr;
    }

    public void setUpdate_opr(String update_opr) {
        this.update_opr = update_opr;
    }

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    public Date getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

}

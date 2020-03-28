package com.steer.data.db.datahandling.model;

import java.util.Date;

/**
 * <p>
 * 数据库-元数据表明细信息，面向于整个Map移除、新增、交互数据
 * </p>
 *
 * @author syhleo
 * @since 2019-09-16
 */

public class DbMetadataD {

    private static final long serialVersionUID = 1L;

    /**
     * 关联相应的数据库主表信息
     */
    private String dbRelate;

    /**
     * 源数据库表或者Map里面字段
     */
    private String sourceTableColumn;

    /**
     * 目标数据库表字段
     */
    private String targetTableColumn;

    /**
     * 无效标识，默认是0，（0:有效，1:无效），用于禁用参与对接的字段
     */
    private String invalidFlg;

    /**
     * 创建人
     */
    private String createOpr;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateOpr;

    /**
     * 修改时间
     */
    private Date updateTime;


    public String getDbRelate() {
        return dbRelate;
    }

    public void setDbRelate(String dbRelate) {
        this.dbRelate = dbRelate;
    }

    public String getSourceTableColumn() {
        return sourceTableColumn;
    }

    public void setSourceTableColumn(String sourceTableColumn) {
        this.sourceTableColumn = sourceTableColumn;
    }

    public String getTargetTableColumn() {
        return targetTableColumn;
    }

    public void setTargetTableColumn(String targetTableColumn) {
        this.targetTableColumn = targetTableColumn;
    }

    public String getInvalidFlg() {
        return invalidFlg;
    }

    public void setInvalidFlg(String invalidFlg) {
        this.invalidFlg = invalidFlg;
    }

    public String getCreateOpr() {
        return createOpr;
    }

    public void setCreateOpr(String createOpr) {
        this.createOpr = createOpr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateOpr() {
        return updateOpr;
    }

    public void setUpdateOpr(String updateOpr) {
        this.updateOpr = updateOpr;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    @Override
    public String toString() {
        return "DbMetadataD{" +
                "dbRelate=" + dbRelate +
                ", sourceTableColumn=" + sourceTableColumn +
                ", targetTableColumn=" + targetTableColumn +
                ", invalidFlg=" + invalidFlg +
                ", createOpr=" + createOpr +
                ", createTime=" + createTime +
                ", updateOpr=" + updateOpr +
                ", updateTime=" + updateTime +
                "}";
    }
}

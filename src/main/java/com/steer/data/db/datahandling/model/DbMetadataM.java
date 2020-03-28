package com.steer.data.db.datahandling.model;

import java.util.Date;

/**
 * <p>
 * 数据库-元数据主表信息
 * </p>
 *
 * @author syhleo
 * @since 2019-09-16
 */
public class DbMetadataM {

    private static final long serialVersionUID = 1L;

    /**
     * 源数据库名称，标识数据源类型即可，比如：datasource_slave1
     */
    private String sourceDb;

    /**
     * 源数据库表名
     */
    private String sourceTableName;

    /**
     * 源数据库表中需要查询出来的字段，为空表示查询所有字段，不为空以,分割 比如：coil_no,coil_no_vt
     */
    private String sourceTableColumns;

    /**
     * 源数据库表查询条件,为空表示查询所有,示例：status=0
     */
    private String sourceTableParams;

    /**
     * 目标数据库名称，标识数据源类型即可
     */
    private String targetDb;

    /**
     * 目标数据库表名
     */
    private String targetTableName;

    /**
     * 关联相应的数据库从表明细信息
     */
    private String dbRelate;

    /**
     * 有效标识，默认是1，（1:有效，0:无效）
     */
    private String validFlg;

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

    /**
     * 源数据库描述，比如：优特钢正式数据库
     */
    private String sourceDbDesc;

    /**
     * 目标数据库描述，比如：中厚板正式数据库
     */
    private String targetDbDesc;

    /**
     * 数据库唯一ID主键值，中间表对接接口标识
     */
    private Integer dbId;

    /**
     * 源数据库表查询成功以后,需要更改的字段，为空表示不更改,示例：status=1
     */
    private String sourceUpdateColumns;

    /**
     * 目标数据库表插入成功以后,需要更改的字段，为空表示不更改,示例：status=1
     */
    private String targetUpdateColumns;

    /**
     * 关联HttpClient通讯接口的ID值，多个接口以逗号,分割
     */
    private String httpIds;

    /**
     * 关联TCP通讯接口的ID值，多个接口以逗号分割
     */
    private String tcpIds;

    /**
     * 关联WebService通讯接口的ID值，多个接口以逗号分割
     */
    private String wsIds;


    public String getSourceDb() {
        return sourceDb;
    }

    public void setSourceDb(String sourceDb) {
        this.sourceDb = sourceDb;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getSourceTableColumns() {
        return sourceTableColumns;
    }

    public void setSourceTableColumns(String sourceTableColumns) {
        this.sourceTableColumns = sourceTableColumns;
    }

    public String getSourceTableParams() {
        return sourceTableParams;
    }

    public void setSourceTableParams(String sourceTableParams) {
        this.sourceTableParams = sourceTableParams;
    }

    public String getTargetDb() {
        return targetDb;
    }

    public void setTargetDb(String targetDb) {
        this.targetDb = targetDb;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getDbRelate() {
        return dbRelate;
    }

    public void setDbRelate(String dbRelate) {
        this.dbRelate = dbRelate;
    }

    public String getValidFlg() {
        return validFlg;
    }

    public void setValidFlg(String validFlg) {
        this.validFlg = validFlg;
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

    public String getSourceDbDesc() {
        return sourceDbDesc;
    }

    public void setSourceDbDesc(String sourceDbDesc) {
        this.sourceDbDesc = sourceDbDesc;
    }

    public String getTargetDbDesc() {
        return targetDbDesc;
    }

    public void setTargetDbDesc(String targetDbDesc) {
        this.targetDbDesc = targetDbDesc;
    }

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public String getSourceUpdateColumns() {
        return sourceUpdateColumns;
    }

    public void setSourceUpdateColumns(String sourceUpdateColumns) {
        this.sourceUpdateColumns = sourceUpdateColumns;
    }

    public String getTargetUpdateColumns() {
        return targetUpdateColumns;
    }

    public void setTargetUpdateColumns(String targetUpdateColumns) {
        this.targetUpdateColumns = targetUpdateColumns;
    }

    public String getHttpIds() {
        return httpIds;
    }

    public void setHttpIds(String httpIds) {
        this.httpIds = httpIds;
    }

    public String getTcpIds() {
        return tcpIds;
    }

    public void setTcpIds(String tcpIds) {
        this.tcpIds = tcpIds;
    }

    public String getWsIds() {
        return wsIds;
    }

    public void setWsIds(String wsIds) {
        this.wsIds = wsIds;
    }

    @Override
    public String toString() {
        return "DbMetadataM{" +
                "sourceDb=" + sourceDb +
                ", sourceTableName=" + sourceTableName +
                ", sourceTableColumns=" + sourceTableColumns +
                ", sourceTableParams=" + sourceTableParams +
                ", targetDb=" + targetDb +
                ", targetTableName=" + targetTableName +
                ", dbRelate=" + dbRelate +
                ", validFlg=" + validFlg +
                ", createOpr=" + createOpr +
                ", createTime=" + createTime +
                ", updateOpr=" + updateOpr +
                ", updateTime=" + updateTime +
                ", sourceDbDesc=" + sourceDbDesc +
                ", targetDbDesc=" + targetDbDesc +
                ", dbId=" + dbId +
                ", sourceUpdateColumns=" + sourceUpdateColumns +
                ", targetUpdateColumns=" + targetUpdateColumns +
                ", httpIds=" + httpIds +
                ", tcpIds=" + tcpIds +
                ", wsIds=" + wsIds +
                "}";
    }
}

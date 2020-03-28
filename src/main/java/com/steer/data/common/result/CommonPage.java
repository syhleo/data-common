/***文档注释***********************************************
 * 作者               :                   邓佳威
 * 创建日期      :                   2017.07.20
 * 描述               :                   web端查询请求及返回pojo
 * 注意事项      :                   无
 * 遗留BUG :                   无
 * 修改日期      :                  
 * 修改人员      :                   
 * 修改内容      :                   
 ***********************************************************/

package com.steer.data.common.result;

import java.util.List;


public class CommonPage<ObjectType> {
    //接收的对象
    private ObjectType object;

    //接收对象的集合
    private List<ObjectType> objectList;

    //页码(查询条件)
    private int pageIndex;

    //条数(查询条件)
    private int pageSize;

    //开始时间(查询条件)
    private String startTime;

    //结束时间(查询条件)
    private String endTime;

    //开始时间2(查询条件)
    private String startTime2;

    //结束时间2(查询条件)
    private String endTime2;

    //条件1(查询条件)
    private String memo1;

    //条件2(查询条件)
    private String memo2;

    //条件3(查询条件)
    private String memo3;

    //条件4(查询条件)
    private String memo4;

    //对象参数
    private Object objParam;

    //排序字段
    private String sortField;

    //排序类型 0正序 1倒叙
    private String sortType;

    //排序条件
    private String sortSqlCondition;

    //额外集合
    private List<ObjectType> list;

    public String getSortSqlCondition() {
        if (sortSqlCondition == null || "desc".equals(sortSqlCondition.trim()))
            return "";
        return sortSqlCondition;
    }

    /**
     * @param ide 标识，有时候是多表，所以需要用类似 t1,t2之类的标识来表示是哪个表字段排序
     */
    public void setSortSqlCondition(String ide) {
        if (sortSqlCondition != null && !"".equals(sortSqlCondition.trim()) && !"desc".equals(sortSqlCondition.trim())) {
            String sqlCondition = "";
            for (String s : this.sortSqlCondition.split(",")) {
                sqlCondition += ide + "." + s + ",";
            }
            this.sortSqlCondition = sqlCondition.substring(0, sqlCondition.length() - 1);
        } else
            this.sortSqlCondition = "";
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
        sortSqlCondition = (sortField == null ? "" : sortField.trim()) + " " + (sortSqlCondition == null ? "" : sortSqlCondition);
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
        sortSqlCondition = (sortSqlCondition == null ? "" : sortSqlCondition) + " " + (sortType == null ? "" : ("1".equals(sortType.trim()) ? "desc" : ""));
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2;
    }

    public String getMemo3() {
        return memo3;
    }

    public void setMemo3(String memo3) {
        this.memo3 = memo3;
    }

    public void setObject(ObjectType object) {
        this.object = object;
    }

    public ObjectType getObject() {
        return object;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setObjParam(Object objParam) {
        this.objParam = objParam;
    }

    public Object getObjParam() {
        return objParam;
    }

    public String getMemo4() {
        return memo4;
    }

    public void setMemo4(String memo4) {
        this.memo4 = memo4;
    }

    public List<ObjectType> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<ObjectType> objectList) {
        this.objectList = objectList;
    }

    public String getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(String startTime2) {
        this.startTime2 = startTime2;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2;
    }

    /**
     * 忽略的记录数
     */
    private int skipResults;

    public int getSkipResults() {
        skipResults = pageSize * (pageIndex - 1);
        return skipResults;
    }

    //查询条件
    private String memo5;

    public String getMemo5() {
        return memo5;
    }

    public void setMemo5(String memo5) {
        this.memo5 = memo5;
    }

    public List<ObjectType> getList() {
        return list;
    }

    public void setList(List<ObjectType> list) {
        this.list = list;
    }

}

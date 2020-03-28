package com.steer.data.quartz.model;


/**
 * <p>
 * quartz定时器动态执行的任务信息，可自定义相关属性
 * </p>
 *
 * @author syhleo
 * @since 2019-09-16
 */
public class QuartzJobEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID主键
     */
    private Integer jobId;

    /**
     * job名称
     */
    private String name;

    /**
     * job组名
     */
    private String jobGroup;

    /**
     * 执行的cron
     */
    private String cron;

    /**
     * 执行的cron的描述
     */
    private String cronDesc;

    /**
     * job的参数
     */
    private String jobParam;

    /**
     * job描述信息
     */
    private String jobDesc;

    /**
     * vm参数
     */
    private String vmParam;

    /**
     * job的jar路径,要执行的jar包路径
     */
    private String jarPath;

    /**
     * job的执行状态,这里我设置为OPEN/CLOSE且只有该值为OPEN才会执行该Job
     */
    private String status;

    /**
     * 关联中间表接口的ID值，多个接口以逗号分割
     */
    private String dbIds;

    /**
     * 关联TCP通讯接口的ID值，多个接口以逗号分割
     */
    private String tcpIds;

    /**
     * 关联WebService通讯接口的ID值，多个接口以逗号分割
     */
    private String wsIds;

    /**
     * 关联HttpClient通讯接口的ID值，多个接口以逗号,分割
     */
    private String httpIds;

    /**
     * 关联UDP通讯接口的ID值，多个接口以逗号,分割
     */
    private String udpIds;

    /**
     * 关联其他情况的ID值，多个接口以逗号,分割
     */
    private String otherIds;


    /**
     * 运行次数（<0:表示不限次数）
     */
    private Integer jobTimes;


    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getCronDesc() {
        return cronDesc;
    }

    public void setCronDesc(String cronDesc) {
        this.cronDesc = cronDesc;
    }

    public String getJobParam() {
        return jobParam;
    }

    public void setJobParam(String jobParam) {
        this.jobParam = jobParam;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getVmParam() {
        return vmParam;
    }

    public void setVmParam(String vmParam) {
        this.vmParam = vmParam;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDbIds() {
        return dbIds;
    }

    public void setDbIds(String dbIds) {
        this.dbIds = dbIds;
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

    public String getHttpIds() {
        return httpIds;
    }

    public void setHttpIds(String httpIds) {
        this.httpIds = httpIds;
    }

    public Integer getJobTimes() {
        return jobTimes;
    }

    public void setJobTimes(Integer jobTimes) {
        this.jobTimes = jobTimes;
    }

    public String getUdpIds() {
        return udpIds;
    }

    public void setUdpIds(String udpIds) {
        this.udpIds = udpIds;
    }

    public String getOtherIds() {
        return otherIds;
    }

    public void setOtherIds(String otherIds) {
        this.otherIds = otherIds;
    }

    @Override
    public String toString() {
        return "QuartzJobEntity{" +
                "jobId=" + jobId +
                ", name=" + name +
                ", jobGroup=" + jobGroup +
                ", cron=" + cron +
                ", cronDesc=" + cronDesc +
                ", jobParam=" + jobParam +
                ", jobDesc=" + jobDesc +
                ", vmParam=" + vmParam +
                ", jarPath=" + jarPath +
                ", status=" + status +
                ", dbIds=" + dbIds +
                ", tcpIds=" + tcpIds +
                ", udpIds=" + udpIds +
                ", wsIds=" + wsIds +
                ", httpIds=" + httpIds +
                ", jobTimes=" + jobTimes +
                "}";
    }


}

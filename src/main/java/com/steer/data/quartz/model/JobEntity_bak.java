package com.steer.data.quartz.model;

import java.io.Serializable;

/**
 * Created by syhleo on 2019/9/4 14:09
 * 这里个人示例,可自定义相关属性
 */

public class JobEntity_bak implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer job_id;
    private String name;          //job名称
    private String job_group;      //job组名
    private String cron;          //执行的cron
    private String cron_desc;     //执行的cron的描述
    private String job_param;     //job的参数
    private String job_desc;   //job描述信息
    private String vm_param;       //vm参数
    private String jar_path;       //job的jar路径
    private String status;        //job的执行状态,这里我设置为OPEN/CLOSE且只有该值为OPEN才会执行该Job
    private String db_ids;        //关联中间表接口的ID值，多个接口以逗号,分割
    private String tcp_ids;       //关联TCP通讯接口的ID值，多个接口以逗号,分割
    private String ws_ids;        //关联WebService通讯接口的ID值，多个接口以逗号,分割
    private String http_ids;      //关联HttpClient通讯接口的ID值，多个接口以逗号,分割
    private Integer job_times;    //任务执行的次数，小于0，表示不限次数。


    public String getDb_ids() {
        return db_ids;
    }

    public void setDb_ids(String db_ids) {
        this.db_ids = db_ids;
    }

    public String getTcp_ids() {
        return tcp_ids;
    }

    public void setTcp_ids(String tcp_ids) {
        this.tcp_ids = tcp_ids;
    }

    public String getWs_ids() {
        return ws_ids;
    }

    public void setWs_ids(String ws_ids) {
        this.ws_ids = ws_ids;
    }

    public String getHttp_ids() {
        return http_ids;
    }

    public void setHttp_ids(String http_ids) {
        this.http_ids = http_ids;
    }

    public Integer getJob_times() {
        return job_times;
    }

    public void setJob_times(Integer job_times) {
        this.job_times = job_times;
    }

    public Integer getJob_id() {
        return job_id;
    }

    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob_group() {
        return job_group;
    }

    public void setJob_group(String job_group) {
        this.job_group = job_group;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getCron_desc() {
        return cron_desc;
    }

    public void setCron_desc(String cron_desc) {
        this.cron_desc = cron_desc;
    }

    public String getJob_param() {
        return job_param;
    }

    public void setJob_param(String job_param) {
        this.job_param = job_param;
    }

    public String getJob_desc() {
        return job_desc;
    }

    public void setJob_desc(String job_desc) {
        this.job_desc = job_desc;
    }

    public String getVm_param() {
        return vm_param;
    }

    public void setVm_param(String vm_param) {
        this.vm_param = vm_param;
    }

    public String getJar_path() {
        return jar_path;
    }

    public void setJar_path(String jar_path) {
        this.jar_path = jar_path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

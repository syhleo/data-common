package com.steer.data.quartz.model;

import java.sql.Timestamp;

/**
 * 日志记录实体信息
 */
public class LoggingEvent {

    //日志记录时间
    private Timestamp create_time;
    //类-方法
    private String caller_clamth;
    //方向，比如L3-L2 ,L3发送给L2的
    private String direction;
    //入参
    private String params_desc;
    //结果描述
    private String result_desc;
    //事件成功与否，（1代表成功，0代表失败）
    private String event_flag;
    //异常信息描述
    private String err_msg;
    //特定描述  比如tcp通讯中电文接口名称。存储 R1M108
    private String spec_desc;
    //访问者ip地址，即调用方IP。
    private String ip_addr;
    //操作人。即访问者描述
    private String create_opr;

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public String getCaller_clamth() {
        return caller_clamth;
    }

    public void setCaller_clamth(String caller_clamth) {
        this.caller_clamth = caller_clamth;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getParams_desc() {
        return params_desc;
    }

    public void setParams_desc(String params_desc) {
        this.params_desc = params_desc;
    }

    public String getResult_desc() {
        return result_desc;
    }

    public void setResult_desc(String result_desc) {
        this.result_desc = result_desc;
    }

    public String getEvent_flag() {
        return event_flag;
    }

    public void setEvent_flag(String event_flag) {
        this.event_flag = event_flag;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public String getSpec_desc() {
        return spec_desc;
    }

    public void setSpec_desc(String spec_desc) {
        this.spec_desc = spec_desc;
    }

    public String getIp_addr() {
        return ip_addr;
    }

    public void setIp_addr(String ip_addr) {
        this.ip_addr = ip_addr;
    }

    public String getCreate_opr() {
        return create_opr;
    }

    public void setCreate_opr(String create_opr) {
        this.create_opr = create_opr;
    }


}

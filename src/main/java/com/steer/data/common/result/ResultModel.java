/***文档注释***********************************************
 * 作者               :                   易鑫
 * 创建日期      :                   2017.12.6
 * 描述               :        			 返回操作结果
 * 注意事项      :                   无
 * 遗留BUG :                   无
 * 修改日期      :                   
 * 修改人员      :                   
 * 修改内容      :                   
 ***********************************************************/
package com.steer.data.common.result;

/**
 * 返回操作结果
 */
public class ResultModel {

    public ResultModel() {
    }

    /**
     * 初始化 state、msgInfo
     *
     * @param state
     * @param msgInfo
     */
    public ResultModel(String state, String msgInfo) {
        this.state = state;
        this.msgInfo = msgInfo;
    }

    public ResultModel(String state, String msgInfo, Object data) {
        this.state = state;
        this.msgInfo = msgInfo;
        this.data = data;
    }


    /**
     * 200正确请求
     * 500错误
     */
    private String state;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }


    /**
     * 成功时调用
     *
     * @param
     * @return
     */
    public static ResultModel success(String msgInfo) {
        return new ResultModel("200", msgInfo);
    }

    public static ResultModel success(String state, String msgInfo) {
        return new ResultModel(state, msgInfo);
    }

    /**
     * 失败时调用
     *
     * @param
     * @return
     */
    public static ResultModel error(String msgInfo) {
        return new ResultModel("500", msgInfo);
    }

    public static ResultModel error(String state, String msgInfo) {
        return new ResultModel(state, msgInfo);
    }


    //返回的信息
    private String msgInfo;

    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }

    public String getMsgInfo() {
        return msgInfo;
    }

    //数据
    private Object data;

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

//	private String errorInfo;
//	public String getErrorInfo() {
//		return errorInfo;
//	}
//	public void setErrorInfo(String errorInfo) {
//		this.errorInfo = errorInfo;
//	}


}

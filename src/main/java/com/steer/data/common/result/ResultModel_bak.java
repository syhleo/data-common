package com.steer.data.common.result;

public class ResultModel_bak<T> {

    private int code;
    private String msg;
    private T data;

    //public ResultModel_bak(T data) {
    private ResultModel_bak(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private ResultModel_bak(CodeMsg_bak CodeMsg_bak) {
        if (CodeMsg_bak == null) {
            return;
        }
        this.code = CodeMsg_bak.getCode();
        this.msg = CodeMsg_bak.getMsg();
    }

    /**
     * 成功时调用
     *
     * @param data
     * @return
     */
    public static <T> ResultModel_bak<T> success(T data) {
        return new ResultModel_bak<T>(data);
    }

    /**
     * 失败时调用
     *
     * @param CodeMsg_bak
     * @return
     */
    public static <T> ResultModel_bak<T> error(CodeMsg_bak CodeMsg_bak) {
        return new ResultModel_bak<T>(CodeMsg_bak);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

package com.steer.data.common.result;

public class CodeMsg_bak {
    private int code;
    private String msg;

    //通用的错误码
    public static CodeMsg_bak SUCCESS = new CodeMsg_bak(0, "success");
    public static CodeMsg_bak SERVER_ERROR = new CodeMsg_bak(500100, "服务端异常");
    public static CodeMsg_bak BIND_ERROR = new CodeMsg_bak(500101, "参数校验异常：%s");
    //登录模块 5002XX
    public static CodeMsg_bak SESSION_ERROR = new CodeMsg_bak(500210, "Session不存在或者已经失效");
    public static CodeMsg_bak PASSWORD_EMPTY = new CodeMsg_bak(500211, "登录密码不能为空");
    public static CodeMsg_bak MOBILE_EMPTY = new CodeMsg_bak(500212, "手机号不能为空");
    public static CodeMsg_bak MOBILE_ERROR = new CodeMsg_bak(500213, "手机号格式错误");
    public static CodeMsg_bak MOBILE_NOT_EXIST = new CodeMsg_bak(500214, "手机号不存在");
    public static CodeMsg_bak PASSWORD_ERROR = new CodeMsg_bak(500215, "密码错误");


    //商品模块 5003XX

    //订单模块 5004XX

    //秒杀模块 5005XX
    public static CodeMsg_bak MIAO_SHA_OVER = new CodeMsg_bak(500500, "商品已经秒杀完毕");
    public static CodeMsg_bak REPEATE_MIAOSHA = new CodeMsg_bak(500501, "不能重复秒杀");


    private CodeMsg_bak(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private CodeMsg_bak() {

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

    @Override
    public String toString() {
        return "CodeMsg_bak [code=" + code + ", msg=" + msg + "]";
    }

    public CodeMsg_bak fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg_bak(code, message);
    }

}

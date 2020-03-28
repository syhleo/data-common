package com.steer.data.common.result;

public class ResultModelUtil {

    public static ResultModel success(Object object) {
        ResultModel resultVO = new ResultModel();
        resultVO.setData(object);
        resultVO.setState("200");
        resultVO.setMsgInfo("成功");
        return resultVO;
    }
}

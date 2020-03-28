/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.test.Test1.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年9月16日下午2:18:30
 * <p>
 * 负责人: syhleo
 * <p>
 * 部门: 工程服务部
 * <p>
 * 修改者：（修改者姓名）
 * <p>
 * 修改时间：
 * <p>
 * 说明：
 * <p>
 */


package com.steer.data.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Test1 {

    public static void main(String[] args) {
        //List<HashMap> mapList =null;//Exception in thread "main" java.lang.NullPointerException
        List<HashMap> mapList = new ArrayList<>();//不会报错，for循环里面内容也不会打印leo----------------
        for (HashMap map : mapList) {
            System.out.println("leo----------------");
        }
    }
}


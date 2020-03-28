/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.tcp.netty.server.HeartBeatServerHandler.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月19日下午2:18:06
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


package com.steer.data.tcp.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class HeartBeatServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)
            throws Exception {

    }

}


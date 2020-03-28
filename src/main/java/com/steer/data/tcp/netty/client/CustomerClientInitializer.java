/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.tcp.netty.client.CustomerClientInitializer.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月17日下午6:28:59
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


package com.steer.data.tcp.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;


public class CustomerClientInitializer extends ChannelInitializer<SocketChannel> {

    private NettyClientBootstrap nettyClientBootstrap;

    public CustomerClientInitializer(NettyClientBootstrap nettyClientBootstrap) {
        this.nettyClientBootstrap = nettyClientBootstrap;
    }

    public NettyClientBootstrap getNettyClientBootstrap() {
        return nettyClientBootstrap;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(
                new IdleStateHandler(0, 0, 60),//30秒中客户端循环心跳监测
                new StringDecoder(Charset.forName("utf-8")),
                new StringEncoder(Charset.forName("utf-8")), new NettyClientHandler(nettyClientBootstrap));
    }

}


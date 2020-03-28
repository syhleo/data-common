/**
 * @Copyright 湖南视拓信息技术股份有限公司. All rights reserved.
 * <p>
 * 版本: ICT 1.0版
 * 文件名：com.steer.data.tcp.netty.server.CustomerServerInitializer.java
 * <p>
 * 作者: syhleo
 * <p>
 * 创建时间: 2019年8月17日下午5:19:03
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

import com.steer.data.common.utils.DataAnal;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;


/**
 * 初始化器，channel注册后，会执行里面的相应的初始化方法
 * @author syhleo
 *
 */
public class CustomerServerInitializer extends ChannelInitializer<SocketChannel> {

    private String tail;

    public CustomerServerInitializer(String tail) {
        this.tail = tail;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 通过SocketChannel去获得对应的管道
        ChannelPipeline p = socketChannel.pipeline();
        //通过管道，添加handler
        p.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
        if (!StringUtils.isEmpty(tail)) {
            ByteBuf delimiter = Unpooled.copiedBuffer(DataAnal.hexStringToString("0A").getBytes());
            //DelimiterBasedFrameDecoder用来解决以特殊符号作为消息结束符的粘包问题
            p.addLast(new DelimiterBasedFrameDecoder(65535, delimiter));
        }
        //p.addLast(new LineBasedFrameDecoder(65535));//以“\n”作为结束符截取电文
        p.addLast(new StringEncoder(), new StringDecoder(),
                new NettyServerHandler());//
    }

}


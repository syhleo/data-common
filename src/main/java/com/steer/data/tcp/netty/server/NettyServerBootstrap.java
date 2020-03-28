package com.steer.data.tcp.netty.server;

import ch.qos.logback.classic.Logger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.LoggerFactory;


/**
 * ServerBootstrap负责初始化netty服务器，并且开始监听端口的socket请求。
 *
 * @author syhleo
 */
public class NettyServerBootstrap {

    private Integer port;

    private String tail;

    public static ChannelFuture f;

    public static SocketChannel socketChannel;

    Logger logger = (Logger) LoggerFactory.getLogger(NettyServerHandler.class);

    private void bind(int serverPort) throws Exception {
        // 定义一对线程组
        // 连接处理group  主线程组, 用于接受客户端的连接，但是不做任何处理.
        EventLoopGroup boss = new NioEventLoopGroup();
        // 事件处理group  从线程组, 主线程组会把任务丢给从线程组，让从线程组去做任务
        EventLoopGroup worker = new NioEventLoopGroup();
        // netty服务器的创建, ServerBootstrap 是一个启动类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 绑定处理group 设置主从线程组
        serverBootstrap.group(boss, worker);
        // 设置nio的双向通道
        serverBootstrap.channel(NioServerSocketChannel.class);
        // 保持连接数 1024
        //serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024 * 1024);
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        // 有数据立即发送   通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
        serverBootstrap.option(ChannelOption.TCP_NODELAY, true);
        // 保持连接   保持长连接状态
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        // Netty4适用对象池，重用缓冲区
        serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

        // 子处理器，用于处理workerGroup 处理新连接
        serverBootstrap.childHandler(new CustomerServerInitializer(tail));
        // 启动server，并且设置serverPort为启动的端口号，同时启动方式为同步
        ChannelFuture f = serverBootstrap.bind(serverPort).sync();
        if (f.isSuccess()) {
            logger.info("netty服务端启动成功，端口号： " + serverPort);
        }
    }

    public NettyServerBootstrap(Integer port, String tail) throws Exception {
        this.port = port;
        this.tail = tail;
        bind(port);
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        NettyServerBootstrap.socketChannel = socketChannel;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

}
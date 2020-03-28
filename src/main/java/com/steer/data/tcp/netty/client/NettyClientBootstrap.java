package com.steer.data.tcp.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClientBootstrap {
    private int port;

    private String host;

    public SocketChannel socketChannel;

    //尝试连接次数
    private static int attempts;

    private Bootstrap bootstrap;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public NettyClientBootstrap(int port, String host) throws Exception {
        this.host = host;
        this.port = port;
        start();
    }

    private void start() throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.option(ChannelOption.SO_LINGER, 0);
            bootstrap.group(eventLoopGroup);
            bootstrap.remoteAddress(this.host, this.port);
            bootstrap.handler(new CustomerClientInitializer(NettyClientBootstrap.this));

            //future.addListener(new ConnectionListener());
            //doConnect();
            ChannelFuture future = bootstrap.connect(this.host, this.port).sync();//同步
            if (future.isSuccess()) {
                socketChannel = (SocketChannel) future.channel();
                logger.info("与L2服务器连接成功！" + host + ":" + port);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("与L2服务器连接失败！" + host + ":" + port);
        } finally {
            //eventLoopGroup.shutdownGracefully();
        }
    }


    /**
     * 重连机制,每隔10s重新连接一次服务器【共尝试10次】
     *
     * @throws InterruptedException
     */
    protected void doConnect() {
        if (socketChannel != null && socketChannel.isActive()) {
            logger.info("NettyClientBootstrap的socketChannel活跃状态！");
            return;
        }
        attempts++;
        ChannelFuture future = bootstrap.connect(this.host, this.port);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                    socketChannel = (SocketChannel) futureListener.channel();
                    logger.info("与L2服务器连接成功！" + host + ":" + port);
                    attempts = 0;//清0
                } else {
                    if (attempts <= 10) {
                        logger.info("Failed to connect to server[" + host + ":" + port + "]" + " try connect after 30s，attempts=" + attempts);
                        futureListener.channel().eventLoop().schedule(new Runnable() {
                            @Override
                            public void run() {
                                doConnect();
                            }
                        }, 30, TimeUnit.SECONDS);
                    }
                }
            }
        });
    }


    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
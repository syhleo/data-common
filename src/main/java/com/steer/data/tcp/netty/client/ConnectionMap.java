package com.steer.data.tcp.netty.client;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.socket.SocketChannel;

public class ConnectionMap {

    public static ConcurrentHashMap<String, SocketChannel> conMap = new ConcurrentHashMap<String, SocketChannel>();

}

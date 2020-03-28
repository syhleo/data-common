package com.steer.data.udp;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class UdpNioClient {
    private static Charset charset = Charset.forName("utf8");
    private static String hostname = "172.16.21.220";
    private static Integer port = 5050;
    private static String sendDataStr = "KLB0001000000570009020000567KLE8F";

    public static void main(String[] args) throws Exception {
        //创建channel
        DatagramChannel channel = DatagramChannel.open();
        //指定为非阻塞方式
        channel.configureBlocking(false);

        //指定发送目的地
        InetSocketAddress address = new InetSocketAddress(hostname, port);
        //虚拟连接，实际没有连接，仅绑定目的地，也可不绑定，在实际发送指定目标地址
        channel.connect(address);

        //新起发送消息线程
        new UdpNioClient.WriteThread(channel).start();

        //创建监听器
        Selector selector = Selector.open();
        //注册读事件
        channel.register(selector, SelectionKey.OP_READ);
        //读缓冲
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        while (true) {
            //等事件出现
            if (selector.select() < 1) {
                continue;
            }

            //获取发生的事件
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()) {
                //获取事件，移除正在处理的事件
                SelectionKey key = it.next();
                it.remove();

                //读取消息
                if (key.isReadable()) {
                    readBuffer.clear();
                    channel.receive(readBuffer);
                    readBuffer.flip();
                    String msg = charset.decode(readBuffer).toString();
                    System.out.println("receive msg : " + msg);

                }
            }
        }
    }


    /**
     * 发送消息线程
     */
    public static class WriteThread extends Thread {
        private DatagramChannel channel;

        public WriteThread(DatagramChannel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            try {
                //写缓冲
                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.print("client send msg:" + sendDataStr + "\n");
                    //String msg = scanner.nextLine();

                    //消息写入缓冲区，并发送消息
                    writeBuffer.put(sendDataStr.getBytes());
                    writeBuffer.flip();
                    //发送消息，发送时还可指定目的地址channel.send(writeBuffer, socketAddress);
                    channel.write(writeBuffer);

                    //发送完后，清空缓冲区
                    writeBuffer.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * https://blog.csdn.net/chinabestchina/article/details/86103153
     */
}


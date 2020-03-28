package com.steer.data.udp;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpTest {

    /**
     * 可以，但是IP如果错误的话，在socket.receive(dp);会停住，卡在那，因为receive()为阻塞式方法
     */
    @Test
    public void test() throws Exception {
        DatagramSocket socket = new DatagramSocket();
        try {

            System.out.println("Client: Start connecting\n");

            String sendData = "KLB0001000000570009020000567KLE8F";
            socket.setSoTimeout(4000);
            DatagramPacket packet = new DatagramPacket(sendData.getBytes(), sendData.getBytes().length, InetAddress.getByName("172.16.21.222"), 5050);
            System.out.println("Client: Sending " + sendData.length() + " bytes'" + sendData + "'\n");
            socket.send(packet);
            System.out.println("Client: Message sent\n");

            //定义数据包,用于存储数据
            byte[] buf = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            socket.receive(dp);//通过服务的receive方法将收到数据存入数据包中,receive()为阻塞式方法
            //通过数据包的方法获取其中的数据
            String ip = dp.getAddress().getHostAddress();
            String data = new String(dp.getData(), 0, dp.getLength());
            //KLB00000001505700010KLEF8   表示发送成功
            System.out.println(ip + "::" + data);

            socket.close();
            System.out.println("Client: Succeed!\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Client: Error!\n");
            socket.close();
        }

    }

    public static void main(String[] args) throws Exception{
        int position=0;
        String[] bufstring=new String[1024];
        //打开带读取的文件
        BufferedReader br = new BufferedReader(new FileReader("D:\\data.txt"));
        String line=null;
        while((line=br.readLine())!=null) {
            bufstring[position]=line;
            position++;
        }
        br.close();//关闭文件
        for(int i=0;i<position;i++) {
            System.out.println(bufstring[i]);
        }

    }



}

package com.steer.data.udp.CustomLed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class CLS_UDPAsyncClient {

    public static Logger logger = LoggerFactory.getLogger(CLS_UDPAsyncClient.class);

    public static String JieXi_String(String _strSend, String _IP, LED_Display_Type _type) {
        //123
        //KLB0001000000570009000000123KLE81 
        String str_Acc = "";
        str_Acc = "KLB" + "00010000";
        str_Acc += "0057";

        String str_01 = "";
        if (_strSend.length() < 1)
            return "";
        if (_strSend.length() > 15)
            str_01 = _strSend.substring(0, 15);
        else
            str_01 = _strSend;

        if (str_01.length() < 4)
            str_Acc += "000" + (str_01.length() + 6);
        else
            str_Acc += "00" + (str_01.length() + 6);
        if (_IP == "226") {
            if (_type == LED_Display_Type.ZhaPiHao)
                str_Acc += CLS_XinyuLED_226_JinZha.area_ZhaPiHao;
            else if (_type == LED_Display_Type.GangZhong)
                str_Acc += CLS_XinyuLED_226_JinZha.area_GangZhong;

            else if (_type == LED_Display_Type.JiHuaZhiShu)
                str_Acc += CLS_XinyuLED_226_JinZha.area_JiHuaZhiShu;
            else if (_type == LED_Display_Type.ShengYuZhiShu)
                str_Acc += CLS_XinyuLED_226_JinZha.area_ShengYuZhiShu;
            else if (_type == LED_Display_Type.JinZha_WenDu)
                str_Acc += CLS_XinyuLED_226_JinZha.area_JinZha_WenDu;
            else if (_type == LED_Display_Type.TuSi_WenDu)
                str_Acc += CLS_XinyuLED_226_JinZha.area_TuSi_WenDu;
            else if (_type == LED_Display_Type.GuiGe)
                str_Acc += CLS_XinyuLED_226_JinZha.area_GuiGe;
            else
                return "";
        }

        if (_IP == "227" || _IP == "228" || _IP == "229") {
            if (_type == LED_Display_Type.ZhaPiHao)
                str_Acc += CLS_XinyuLED_227_228_229.area_ZhaPiHao;
            else if (_type == LED_Display_Type.GangZhong)
                str_Acc += CLS_XinyuLED_227_228_229.area_GangZhong;
            else if (_type == LED_Display_Type.GouHao)
                str_Acc += CLS_XinyuLED_227_228_229.area_GouHao;
            else
                return "";
        }
        str_Acc += "0000";
        str_Acc += _strSend;
        str_Acc += "KLE";


        //=============
        //String str_02 = length(str_Acc);
        //str_Acc += str_02;

        String str_02 = str_Acc;
        String str_03_1 = str_02.substring(0, 1);
        String str_03_2 = str_02.substring(1, 2);
        //字符转ASCII码：
//        int i_01 = Asc(str_03_1);
//        int i_02 = Asc(str_03_2);
        int i_01 = Integer.valueOf(stringToAscii(str_03_1));
        int i_02 = Integer.valueOf(stringToAscii(str_03_2));

        //String str_04 = CalculateChecksum(str_Acc);

        //str_Acc += CalculateChecksum(str_Acc);   //CalculateChecksum   //ADD2
        str_Acc += CalculateLength(str_Acc);   //CalculateChecksum   //ADD2
        return str_Acc;
    }


    public static String JieXi_String_228(String _strSend, String _IP, LED_Display_Type _type) {
        //123
        //KLB0001000000570009000000123KLE81
        String str_Acc = "";
        str_Acc = "KLB" + "00010000";
        str_Acc += "0057";

        String str_01 = "";
        if (_strSend.length() < 1)
            return "";
        if (_strSend.length() > 31)
            str_01 = _strSend.substring(0, 31);
        else
            str_01 = _strSend;

        if (str_01.length() < 4)
            str_Acc += "000" + (str_01.length() + 6);
        else
            str_Acc += "00" + (str_01.length() + 6);
        if (_IP == "226") {
            if (_type == LED_Display_Type.ZhaPiHao)
                str_Acc += CLS_XinyuLED_226_JinZha.area_ZhaPiHao;
            else if (_type == LED_Display_Type.GangZhong)
                str_Acc += CLS_XinyuLED_226_JinZha.area_GangZhong;

            else if (_type == LED_Display_Type.JiHuaZhiShu)
                str_Acc += CLS_XinyuLED_226_JinZha.area_JiHuaZhiShu;
            else if (_type == LED_Display_Type.ShengYuZhiShu)
                str_Acc += CLS_XinyuLED_226_JinZha.area_ShengYuZhiShu;
            else if (_type == LED_Display_Type.JinZha_WenDu)
                str_Acc += CLS_XinyuLED_226_JinZha.area_JinZha_WenDu;
            else if (_type == LED_Display_Type.TuSi_WenDu)
                str_Acc += CLS_XinyuLED_226_JinZha.area_TuSi_WenDu;
            else if (_type == LED_Display_Type.GuiGe)
                str_Acc += CLS_XinyuLED_226_JinZha.area_GuiGe;
            else
                return "";
        }

        if (_IP == "227" || _IP == "228" || _IP == "229") {
            if (_type == LED_Display_Type.ZhaPiHao)
                str_Acc += CLS_XinyuLED_227_228_229.area_ZhaPiHao;
            else if (_type == LED_Display_Type.GangZhong)
                str_Acc += CLS_XinyuLED_227_228_229.area_GangZhong;
            else if (_type == LED_Display_Type.GouHao)
                str_Acc += CLS_XinyuLED_227_228_229.area_GouHao;
            else
                return "";
        }
        str_Acc += "0000";
        str_Acc += _strSend;
        str_Acc += "KLE";


        //=============
        //String str_02 = length(str_Acc);
        //str_Acc += str_02;

        String str_02 = str_Acc;
        String str_03_1 = str_02.substring(0, 1);
        String str_03_2 = str_02.substring(1, 2);
        //字符转ASCII码：
//        int i_01 = Asc(str_03_1);
//        int i_02 = Asc(str_03_2);
        int i_01 = Integer.valueOf(stringToAscii(str_03_1));
        int i_02 = Integer.valueOf(stringToAscii(str_03_2));

        //String str_04 = CalculateChecksum(str_Acc);

        //str_Acc += CalculateChecksum(str_Acc);   //CalculateChecksum   //ADD2
        str_Acc += CalculateLength(str_Acc);   //CalculateChecksum   //ADD2
        return str_Acc;
    }




    public static String UDP_Client_Sent_String(String _strSend, String _IP, int _i_Host_Port) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        try {
            String str_IP = "";
            if (_IP == "226")
                str_IP = "172.16.21." + _IP;
            if (_IP == "227")
                str_IP = "172.16.21." + _IP;
            if (_IP == "228")
                str_IP = "172.16.21." + _IP;
            if (_IP == "229")
                str_IP = "172.16.21." + _IP;

            if (str_IP == "")
                return "";

            logger.info("Client: Start connecting");
//            String sendData="KLB0001000000570009020000567KLE8F";
            socket.setSoTimeout(4000);//设置超时时间，单位为毫秒。
            DatagramPacket packet = new DatagramPacket(_strSend.getBytes(), _strSend.getBytes().length, InetAddress.getByName(str_IP), 5050);
            logger.info("Client: Sending " + _strSend.length() + " bytes' " + _strSend);
            socket.send(packet);
            logger.info("Client: Message sent");

            //定义数据包,用于存储数据
            byte[] buf = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            //阻塞”是一个专业名词，它会产生一个内部循环，使程序暂停在这个地方，直到一个条件触发。
            socket.receive(dp);//通过服务的receive方法将收到数据存入数据包中,receive()为阻塞式方法
            //通过数据包的方法获取其中的数据
            String ip = dp.getAddress().getHostAddress();
            String data = new String(dp.getData(), 0, dp.getLength());
            logger.info(ip + "::" + data);
            socket.close();
            logger.info("Client: Succeed!");
            //KLB00000001505700010KLEF8   表示发送成功
            if ("KLB00000001505700010KLEF8".equals(data)) {
                logger.info("发送成功");
                return "OK";
            } else {
                return "Bad";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Client: Error!" + _strSend + "【" + _IP + "】");
            socket.close();
            return "Bad";
        }
        /*
        close()：关闭DatagramSocket。在应用程序退出的时候，通常会主动释放资源，关闭Socket，
        但是由于异常地退出可能造成资源无法回收。所以，应该在程序完成时，主动使用此方法关闭Socket，或在捕获到异常抛出后关闭Sock
        */
    }

    public static String CalculateLength(String content) {
        return (null == content) ? Integer.toHexString(0).toUpperCase() :
                Integer.toHexString(content.chars().reduce(0, (a, b) -> a + b >= 256 ? (a + b) - 256 : a + b)).toUpperCase();
    }


    public static String CalculateChecksum(String dataToCalculate) {
        byte[] byteToCalculate = dataToCalculate.getBytes();
        int checksum = 0;
//        foreach (byte chData in byteToCalculate)
//        {
//            checksum += chData;
//        }
        for (byte chData : byteToCalculate) {
            checksum += chData;
        }
        checksum &= 0xff;
        /**
         * C# ToString("x2")的理解
         * 1).转化为16进制。
         * 2).大写X:ToString("X2")即转化为大写的16进制。
         * 3).小写x:ToString("x2")即转化为小写的16进制。
         * 4).2表示输出两位，不足的2位的前面补0,如 0x0A 如果没有2,就只会输出0xA
         */
        //return checksum.ToString("X2");
        return String.format("%02x", checksum).toUpperCase();//2表示需要两个16进制数
    }

    /**
     * 字符串转换为Ascii
     *
     * @param value
     * @return
     */
    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

}

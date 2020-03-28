package com.steer.data.tcp.datahandling.controller;

import ch.qos.logback.classic.Logger;
import com.steer.data.common.constants.CommonConstants;
import com.steer.data.common.result.ResultModel;
import com.steer.data.tcp.datahandling.message.Message;
import com.steer.data.tcp.datahandling.message.TpmMessage;
import com.steer.data.tcp.datahandling.service.impl.DataHandlingServiceImpl;
import com.steer.data.tcp.netty.client.ConnectionMap;
import com.steer.data.tcp.netty.client.NettyClientBootstrap;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/${api.version}/DataHandling")
public class DataHandlingController {

    @Autowired
    DataHandlingServiceImpl dataHandlingService;

    @Value("${L2.ip}")
    String ip;
    @Value("${L2.port}")
    String port;

    NettyClientBootstrap bootstrap = null;

    Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @PostMapping("/toMsg/")
    public ResultModel toMsg(String ifName, String deviceName, @RequestBody HashMap<String, Object> dataMap) {
        try {
            String result = dataHandlingService.sendToL2(ifName, dataMap);
            return ResultModel.success(result);
        } catch (Exception e) {
            e.printStackTrace();

            /**
             * -------此处补偿方式仅适用于（同步方法中/业务支持消息重复消费/QUARTZ定时器串行执行）的情况，--------
             * 因为可能在补偿情况中Thread.sleep的时候，
             * 刚好有另外的线程【客户点击按钮重新操作】会出现重复调用dataHandlingService.sendToL2(ifName, dataMap);的情况。
             *
             */
            //补偿机制，分别在10s,30s,180s
            for (int i = 1; i <= CommonConstants.RETRY_COUNT; i++) {
                try {
                    if (i == 1) {//第一次补偿
                        Thread.sleep(CommonConstants.RETRY_FIRST);
                    } else if (i == 2) {//第二次补偿
                        Thread.sleep(CommonConstants.RETRY_SECOND);
                    } else if (i == 3) {//第三次补偿
                        Thread.sleep(CommonConstants.RETRY_THIRD);
                    }
                    String result = dataHandlingService.sendToL2(ifName, dataMap);
                    logger.info("第" + i + "次补偿成功");
                    return ResultModel.success(result);
                } catch (Exception ex) {
                    logger.error("第" + i + "次补偿失败");
                }
            }
            return ResultModel.error(e.getMessage());
        }

    }

    @GetMapping("/getTestDate/")
    public ResultModel getTestData(String ifName) {
        String result;
        try {
            result = dataHandlingService.getTestData(ifName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.error(e.getMessage());
        }
        return ResultModel.success(result);
    }


    @PostMapping("/testTcp/")
    public ResultModel testTcp() {
        try {
            new NettyClientBootstrap(Integer.valueOf(port), ip);
            return ResultModel.success("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.error(e.getMessage());
        }

    }

    @PostMapping("/testTcp2/")
    public ResultModel testTcp2() {
        try {


            // 返回结果
            String result = "";
            // 把JSON数据转成成电文
            //String msg = this.toMsg(ifName, dataMap);
            // 获取map中的连接，并判断连接是否活跃
            String ifName = "测试";
            try {
                if (!ConnectionMap.conMap.containsKey("connection1")) {
                    try {
                        bootstrap = new NettyClientBootstrap(Integer.valueOf(port), ip);
                        System.out.println(bootstrap == null ? "bootstrap为空" : "bootstrap不为空");
                    } catch (Exception e) {
                        bootstrap.getSocketChannel().close();
                        bootstrap.getSocketChannel().closeFuture();
                        throw new Exception("与L2通讯失败！ " + ip + ":" + port);
                    }
                    System.out.println(bootstrap.getSocketChannel() == null ? "SocketChannel为空" : "SocketChannel不为空");
                    ConnectionMap.conMap.put("connection1", bootstrap.getSocketChannel());
                } else if (!ConnectionMap.conMap.get("connection1").isActive()) {
                    throw new Exception("与L2通讯失败！ " + ip + ":" + port);
                }
                SocketChannel socketChannel = ConnectionMap.conMap.get("connection1"); // 获取连接信息
                Thread.sleep(100L);
                logger.info("L3下发数据封装后报文： 接口名：" + ifName + "    数据：" + "");

                /** 封装心跳电文 */
                Message message = new TpmMessage("999999", "", "");
                String heart = "";
                try {
                    heart = message.enCodeMsg();
                    heart = "0043299439201" + heart + "AtestTcpA";
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                socketChannel.writeAndFlush(heart);

                result = "接口名：" + ifName + "       数据：" + "ttttxx" + "   下发成功_vsuccess---";
            } catch (Exception e) {
                logger.error("L3--->L2： 接口名：" + ifName + "      数据发送失败");
                logger.error("出错原因： ", e);
                throw new Exception("数据发送失败！ " + ip + ":" + port);
            }

            return ResultModel.success("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.error(e.getMessage());
        }

    }

    @PostMapping("/testTcp3/")
    public ResultModel testTcp3() {
        try {

            // 返回结果
            String result = "";
            // 把JSON数据转成成电文
            //String msg = this.toMsg(ifName, dataMap);
            // 获取map中的连接，并判断连接是否活跃
            String ifName = "测试";
            try {
                if (!ConnectionMap.conMap.containsKey("connection1")) {
                    try {
                        bootstrap = new NettyClientBootstrap(Integer.valueOf(port), ip);
                        System.out.println(bootstrap == null ? "bootstrap为空" : "bootstrap不为空");
                    } catch (Exception e) {
                        bootstrap.getSocketChannel().close();
                        bootstrap.getSocketChannel().closeFuture();
                        throw new Exception("与L2通讯失败！ " + ip + ":" + port);
                    }
                    System.out.println(bootstrap.getSocketChannel() == null ? "SocketChannel为空" : "SocketChannel不为空");
                    ConnectionMap.conMap.put("connection1", bootstrap.getSocketChannel());
                } else if (!ConnectionMap.conMap.get("connection1").isActive()) {
                    throw new Exception("与L2通讯失败！ " + ip + ":" + port);
                }
                SocketChannel socketChannel = ConnectionMap.conMap.get("connection1"); // 获取连接信息
                Thread.sleep(100L);


                /** 封装心跳电文 */
                Message message = new TpmMessage("999999", "", "");
                String heart = "";
                try {
                    heart = message.enCodeMsg();
                    //heart="009520190819201800276to66syhleo"+heart+"AtestTcpA";heart中有回车键即换行建，根据我们接受方0A回车换行，会拼凑成两条电文。
                    heart = "009520190819201800276to66syhleoAtestTcpA" + heart;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                logger.info("L3下发数据,接口名：" + ifName + "    数据：" + heart);
                socketChannel.writeAndFlush(heart);

                result = "接口名：" + ifName + "       数据：" + "ttttxx" + "   下发成功_vsuccess---";
            } catch (Exception e) {
                logger.error("L3--->L2： 接口名：" + ifName + "      数据发送失败");
                logger.error("出错原因： ", e);
                throw new Exception("数据发送失败！ " + ip + ":" + port);
            }

            return ResultModel.success("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.error(e.getMessage());
        }

    }


}

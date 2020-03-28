package com.steer.data.tcp.netty.server;

import ch.qos.logback.classic.Logger;
import com.steer.data.common.utils.SpringUtil;
import com.steer.data.tcp.datahandling.message.*;
import com.steer.data.tcp.datahandling.service.impl.DataHandlingServiceImpl;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;


/**
 * 创建自定义助手类   服务端事件处理
 *
 * @author syhleo
 */
@Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
    private ChannelHandlerContext context;

    Logger logger = (Logger) LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Message message = null;
        if (StringUtils.isEmpty(msg)) return;
        //DaErrorMsgServiceImpl des = SpringUtil.getBean(DaErrorMsgServiceImpl.class);
        try {
            setContext(ctx);
            logger.info("L2发送数据：" + msg + "#");
            //心跳电文不处理
            if ("999999".equals(msg.substring(4, 10))) return;
            //logger.info("L2发送数据：" + msg+"#");
            DataHandlingServiceImpl dataHandlingService = SpringUtil
                    .getBean(DataHandlingServiceImpl.class);

            // 解析电文
            switch (dataHandlingService.getDeviceName()) {
                case "tpm":
                    Decode decode = new TpmDecode();
                    message = decode.deCode(msg);
                    break;

                case "ztl":
                    Decode decode11 = new ZtlDecode();
                    message = decode11.deCode(msg);
                    break;

                default:
                    logger.error("L2--->L3" + dataHandlingService.getDeviceName() + "设备类型不存在");
                    return;
            }
            if (StringUtils.isEmpty(message.getMsgNo())) {
                logger.error("L2--->L3接口名称不能为空");
            }
            dataHandlingService.toJson(message.getMsgNo(), message.getBody());
        } catch (Exception e) {
            //这里处理方式不能再抛出异常了，切记。
//			DaErrorMsg daErrorMsg = new DaErrorMsg();
//			daErrorMsg.setDirection("L2--->L3");
//			daErrorMsg.setMsg(msg);
//			daErrorMsg.setErrorMsg(e.getMessage());
//			if(message!=null){
//				daErrorMsg.setIfName(message.getMsgNo());
//			}
            //des.add(daErrorMsg);
            logger.error(e.getMessage());
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("服务端发生异常");
        if (cause != null) {
            cause.printStackTrace();
        }
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.error("===服务端channelInactive===(客户端失效)");
        ctx.close();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    public ChannelHandlerContext getContext() {
        return context;
    }

    public void setContext(ChannelHandlerContext context) {
        this.context = context;
    }

}

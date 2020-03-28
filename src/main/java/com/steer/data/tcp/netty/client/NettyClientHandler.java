package com.steer.data.tcp.netty.client;

import com.steer.data.common.utils.SpringUtil;
import com.steer.data.tcp.datahandling.message.Message;
import com.steer.data.tcp.datahandling.message.TpmMessage;
import com.steer.data.tcp.datahandling.service.impl.DataHandlingServiceImpl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;


/**
 * 创建自定义助手类   客户端事件处理
 *
 * @author syhleo
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    DataHandlingServiceImpl dataHandling = SpringUtil.getBean(DataHandlingServiceImpl.class);

    String port = dataHandling.getPort();

    String ip = dataHandling.getIp();

    private static int attempts;

    private NettyClientBootstrap nettyClientBootstrap;

    public NettyClientHandler(NettyClientBootstrap nettyClientBootstrap) {
        this.nettyClientBootstrap = nettyClientBootstrap;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        String mg = new String(req, StandardCharsets.UTF_8);
        System.out.println(mg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("NettyClientHandler类捕获到异常");
        logger.error("客户端发生错误:" + cause.getMessage());
        ctx.close();
        ConnectionMap.conMap.clear();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //logger.info("NettyClientHandler类channel。。。不活跃");
        // 使用过程中断线重连
		/*final EventLoop eventLoop = ctx.channel().eventLoop();
		eventLoop.schedule(new Runnable() {
			@Override
			public void run() {
				logger.error("无法连接服务器：ip"+ip+":"+port+"，正在尝试重新连接");
				SocketChannel socketChannel = (SocketChannel)imConnection.connect(ip, Integer.valueOf(port.trim()));
				ConnectionMap.conMap.put("connection1", socketChannel);
			}
		}, 1L, TimeUnit.SECONDS);
		super.channelInactive(ctx);*/


        ConnectionMap.conMap.clear();
        super.channelInactive(ctx);
        //ctx.close();
        nettyClientBootstrap.doConnect();
        //logger.info("NettyClientHandler类channelInactive方法结束。");
    }

    /**
     * channel链路每次active的时候，将其连接的次数重新☞ 0
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //logger.info("当前链路已经激活了，重连尝试次数重新置为0");
        logger.info("当前链路已经激活了，重连尝试次数重新置为0");
        //attempts = 0;
        ctx.fireChannelActive();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        /** 封装心跳电文 */
        Message message = new TpmMessage("999999", "", "\n", "M1", "R1");
        //TpmMessage(String msgNo,String body,String tail,String sendDC,String receiveDC)
        String heart = "";
        try {
            heart = message.enCodeMsg();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //测试修改
        //heart="AbcDef"+heart;
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                if (attempts < 12) {
                    attempts++;
                    ctx.writeAndFlush(heart);
                }
            }
        }
        logger.info("客户端循环心跳监测发送: " + heart);
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        //logger.info("channel。。。注册");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        //logger.info("channel。。。移除");
        super.channelUnregistered(ctx);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //logger.info("channeld读取完毕。。。");
        super.channelReadComplete(ctx);
    }


    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        //logger.info("channel可写更改");
        super.channelWritabilityChanged(ctx);
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //logger.info("助手类添加");
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //logger.info("助手类移除");
        super.handlerRemoved(ctx);
    }


}

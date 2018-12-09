package io.ashu.client.handler;

import io.ashu.protocol.command.requeset.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.TimeUnit;

public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {
  private static final int HEARTBEAT_INTERVAL = 5;
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {

    ctx.executor().scheduleAtFixedRate(() -> {
      if (ctx.channel().isActive()) {
        System.out.println("向服务器发送心跳");
        ctx.writeAndFlush(new HeartBeatRequestPacket());
      }
    },1, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);


    super.channelActive(ctx);
  }




}

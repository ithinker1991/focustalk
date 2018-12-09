package io.ashu.server.handler;

import io.ashu.attribute.Attributes;
import io.ashu.protocol.command.reponse.HeartBeatResponsePacket;
import io.ashu.protocol.command.requeset.HeartBeatRequestPacket;
import io.ashu.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Date;

public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg)
      throws Exception {
    Session session = ctx.channel().attr(Attributes.SESSION).get();
    String userId;
    if (session != null) {
      userId = session.getUserId();
    } else {
      userId = "未登录用户";
    }
    System.out.println(new Date() + " 收到用户[" + userId + "]的心跳包");
    ctx.writeAndFlush(new HeartBeatResponsePacket());
  }
}

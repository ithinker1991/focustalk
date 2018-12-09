package io.ashu.server.handler;

import io.ashu.protocol.command.reponse.MessageResponsePacket;
import io.ashu.protocol.command.requeset.MessageRequestPacket;
import io.ashu.session.Session;
import io.ashu.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket requestPacket)
      throws Exception {

    Session session = SessionUtil.getSession(ctx.channel());

    String toUserId = requestPacket.getToUserId();
    Channel toChannel = SessionUtil.getChannelByUserId(toUserId);
    if (toChannel == null) {
      MessageResponsePacket responsePacket = new MessageResponsePacket();
      responsePacket.setFromUserName("服务器");
      responsePacket.setMessage(requestPacket.getMessage());
      ctx.writeAndFlush(responsePacket);
    } else {
      MessageResponsePacket responsePacket = new MessageResponsePacket();
      responsePacket.setFromUserId(session.getUserId());
      responsePacket.setFromUserName(session.getUserName());
      responsePacket.setMessage(requestPacket.getMessage());
      toChannel.writeAndFlush(responsePacket);
    }
  }
}

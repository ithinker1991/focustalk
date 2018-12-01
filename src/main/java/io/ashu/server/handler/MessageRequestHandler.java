package io.ashu.server.handler;

import io.ashu.protocol.command.reponse.MessageResponsePacket;
import io.ashu.protocol.command.requeset.MessageRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket requestPacket)
      throws Exception {
    System.out.println(new Date() + " : 收到用户消息[" + requestPacket.getMessage() + "]");
    MessageResponsePacket responsePacket = new MessageResponsePacket();
    responsePacket.setMessage("服务端收到你的消息[" + requestPacket.getMessage() + "]");
    ctx.channel().writeAndFlush(responsePacket);
  }
}

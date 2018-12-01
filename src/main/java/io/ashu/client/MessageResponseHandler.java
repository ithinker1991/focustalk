package io.ashu.client;

import io.ashu.protocol.command.reponse.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Date;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg)
      throws Exception {
    System.out.println(new Date() + " : 收到服务器消息[" + msg.getMessage() + "]");
  }
}

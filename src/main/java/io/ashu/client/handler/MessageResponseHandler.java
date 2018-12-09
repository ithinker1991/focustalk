package io.ashu.client.handler;

import io.ashu.protocol.command.reponse.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Date;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
  public static final MessageResponseHandler INSTANCE = new MessageResponseHandler();

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg)
      throws Exception {
    System.out.println(new Date() + " :" + msg.toString());
  }
}

package io.ashu.client.handler;

import io.ashu.protocol.command.reponse.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Date;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg)
      throws Exception {
    System.out.println(new Date() + " 收到群消息[" + msg.getMessage() + "]");
  }
}

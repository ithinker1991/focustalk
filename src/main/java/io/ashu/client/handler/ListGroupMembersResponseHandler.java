package io.ashu.client.handler;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import io.ashu.protocol.command.reponse.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Date;

public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket msg)
      throws Exception {
    String userList = Joiner.on(",").join(msg.getUserIdList());
    System.out.println(new Date() + " 群成员:[" + userList +"]");
  }
}

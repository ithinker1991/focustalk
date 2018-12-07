package io.ashu.server.handler;

import io.ashu.client.handler.GroupMessageResponseHandler;
import io.ashu.protocol.command.reponse.GroupMessageResponsePacket;
import io.ashu.protocol.command.requeset.GroupMessageRequestPacket;
import io.ashu.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.List;

public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg)
      throws Exception {
    String groupId = msg.getGroupId();
    String message = msg.getMessage();

    GroupMessageResponsePacket response = new GroupMessageResponsePacket();
    response.setMessage(message);
    ChannelGroup group = SessionUtil.getGroupMembersById(groupId);
    group.writeAndFlush(response);

  }
}

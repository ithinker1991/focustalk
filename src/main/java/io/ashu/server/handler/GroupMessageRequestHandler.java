package io.ashu.server.handler;

import io.ashu.protocol.command.reponse.GroupMessageResponsePacket;
import io.ashu.protocol.command.requeset.GroupMessageRequestPacket;
import io.ashu.util.SessionUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@Sharable
public class GroupMessageRequestHandler extends
    SimpleChannelInboundHandler<GroupMessageRequestPacket> {

  public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

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

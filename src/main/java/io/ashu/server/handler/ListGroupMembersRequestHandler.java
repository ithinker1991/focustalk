package io.ashu.server.handler;

import io.ashu.protocol.command.reponse.ListGroupMembersResponsePacket;
import io.ashu.protocol.command.requeset.ListGroupMembersRequestPacket;
import io.ashu.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.List;

public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg)
      throws Exception {
    String groupId = msg.getId();

    List<String> userIdList = SessionUtil.getGroupMembersById(groupId);
    ListGroupMembersResponsePacket response = new ListGroupMembersResponsePacket();
    response.setUserIdList(userIdList);
    ctx.channel().writeAndFlush(response);
  }
}

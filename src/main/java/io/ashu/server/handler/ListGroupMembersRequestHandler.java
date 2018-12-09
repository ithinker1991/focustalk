package io.ashu.server.handler;

import io.ashu.attribute.Attributes;
import io.ashu.protocol.command.reponse.ListGroupMembersResponsePacket;
import io.ashu.protocol.command.requeset.ListGroupMembersRequestPacket;
import io.ashu.session.Session;
import io.ashu.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends
    SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

  public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg)
      throws Exception {
    String groupId = msg.getId();

    ChannelGroup group = SessionUtil.getGroupMembersById(groupId);
    ListGroupMembersResponsePacket response = new ListGroupMembersResponsePacket();
    List<String> userIdList = new ArrayList<>();
    for (Channel channel : group) {
      Session session = channel.attr(Attributes.SESSION).get();
      userIdList.add(session.getUserId());
    }
    response.setUserIdList(userIdList);
    ctx.channel().writeAndFlush(response);
  }
}

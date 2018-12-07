package io.ashu.server.handler;

import io.ashu.protocol.command.reponse.CreateGroupResponsePacket;
import io.ashu.protocol.command.requeset.CreateGroupRequestPacket;
import io.ashu.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        String groupId = msg.getId();
        List<String> users = msg.getUserIdList();
        ChannelGroup channels = new DefaultChannelGroup(ctx.executor());
        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        users.forEach(userId ->
        {
            Channel channel = SessionUtil.getChannelByUserId(userId);
            if (channel != null) {
                channels.add(channel);
            }
        });
        SessionUtil.createGroup(groupId, channels);
        channels.writeAndFlush(responsePacket);
    }
}

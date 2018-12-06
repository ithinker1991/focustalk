package io.ashu.server.handler;

import io.ashu.protocol.command.reponse.CreateGroupResponsePacket;
import io.ashu.protocol.command.requeset.CreateGroupRequestPacket;
import io.ashu.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        String groupId = msg.getId();
        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        if (SessionUtil.createGroup(groupId, msg.getUserIdList())) {
            responsePacket.setSuccess(true);
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("已经存在");
        }
        ctx.channel().writeAndFlush(responsePacket);
    }
}

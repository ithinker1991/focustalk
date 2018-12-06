package io.ashu.client.handler;

import io.ashu.protocol.command.reponse.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class GreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println(new Date() + " 创建群组成功");
        } else {
            System.err.println(new Date() + " 创建群组失败，原因[" + msg.getReason() +  "]");
        }
    }
}

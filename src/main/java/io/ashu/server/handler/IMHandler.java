package io.ashu.server.handler;

import io.ashu.client.handler.GroupMessageResponseHandler;
import io.ashu.client.handler.MessageResponseHandler;
import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketType;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.HashMap;
import java.util.Map;

@Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

  public static final IMHandler INSTANCE = new IMHandler();
  private static Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

  static {
    handlerMap = new HashMap<>();
    handlerMap.put(PacketType.GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
    handlerMap.put(PacketType.MESSAGE_REQUEST, MessageResponseHandler.INSTANCE);
    handlerMap.put(PacketType.CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
    handlerMap.put(PacketType.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
    Byte type = msg.getCommand();
    SimpleChannelInboundHandler handler = handlerMap.get(type);
    if (handler != null) {
      handler.channelRead(ctx, msg);
    }
  }
}

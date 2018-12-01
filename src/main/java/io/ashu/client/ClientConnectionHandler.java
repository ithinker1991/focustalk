package io.ashu.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.util.Date;

public class ClientConnectionHandler extends ChannelInboundHandlerAdapter {
  private String username;
  public ClientConnectionHandler(String username) {
    this.username = username;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println(new Date() + ": 客户端写出数据");
    // TODO 这里为什么要用ctx去获取一个分配器
    ByteBuf buf = getByteBuf(ctx);

    ctx.channel().writeAndFlush(buf);
  }

  private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
    byte[] bytes = this.username.getBytes(CharsetUtil.UTF_8);
    ByteBuf buf = ctx.alloc().buffer();
    buf.writeBytes(bytes);
    return buf;
  }
}

package io.ashu.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.util.Date;

public class ServerConectionHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf byteBuf = (ByteBuf) msg;
    System.out.printf(new Date() + ": 服务端读取数据-> " + byteBuf.toString(CharsetUtil.UTF_8));


  }

  private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
    ByteBuf byteBuf = ctx.alloc().ioBuffer();
    byte[] bytes = "已经与服务器建立连接！".getBytes(CharsetUtil.UTF_8);
    byteBuf.writeBytes(bytes);
    return byteBuf;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("与一个客户端建立了连接");
    super.channelActive(ctx);
  }
}

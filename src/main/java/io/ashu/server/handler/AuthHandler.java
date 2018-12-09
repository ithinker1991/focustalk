package io.ashu.server.handler;

import io.ashu.util.SessionUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

  public static final AuthHandler INSTANCE = new AuthHandler();

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (!SessionUtil.hasLogin(ctx.channel())) {
      ctx.channel().close();
    } else {
      ctx.pipeline().remove(this);
      super.channelRead(ctx, msg);
    }
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    if (SessionUtil.hasLogin(ctx.channel())) {
      System.out.println("当前登录验证已经完成，无需重复登录， AuthHandler 被移除");
    } else {
      System.out.println("无需登录?");
    }
  }
}

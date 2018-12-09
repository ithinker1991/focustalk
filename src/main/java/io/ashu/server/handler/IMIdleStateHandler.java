package io.ashu.server.handler;

import io.ashu.attribute.Attributes;
import io.ashu.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class IMIdleStateHandler extends IdleStateHandler {

  private static final int READ_IDLE_TIME = 15;

  public IMIdleStateHandler() {
    super(READ_IDLE_TIME, -1, -1, TimeUnit.SECONDS);
  }

  @Override
  protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
    Session session = ctx.channel().attr(Attributes.SESSION).get();
    if (session!=null) {
      System.out.println(
          new Date() + " 该连接[" + session.getUserId() + "]超时");
    } else {
      System.out.println(
          new Date() + " 该连接[非登录用户]超时");
    }
    ctx.channel().close();
  }
}

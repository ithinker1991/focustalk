package io.ashu.server;

import io.ashu.protocol.command.reponse.LoginResponsePacket;
import io.ashu.protocol.command.requeset.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket requestPacket) throws Exception {
    String username = requestPacket.getUsername();
    String password = requestPacket.getPassword();
    System.out.printf(new Date() + ": 用户[" + username + "]尝试登陆");

    LoginResponsePacket responsePacket = new LoginResponsePacket();
    if (vailate(username, password)) {
      responsePacket.setSuccess(true);
      System.out.printf(new Date() + ": 用户[" + username + "]登陆成功");
    } else {
      responsePacket.setSuccess(false);
      responsePacket.setMsg("密码或者用户名错误");
      System.out.printf(new Date() + ": 用户[" + username + "]登陆失败");
    }
    ctx.channel().writeAndFlush(responsePacket);
  }

  private static boolean vailate(String username, String password) {
    return true;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("与一个客户端建立了连接");
    super.channelActive(ctx);
  }
}

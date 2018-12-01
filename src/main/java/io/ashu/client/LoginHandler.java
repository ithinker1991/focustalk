package io.ashu.client;

import io.ashu.protocol.command.PacketCodec;
import io.ashu.protocol.command.reponse.LoginResponsePacket;
import io.ashu.protocol.command.reponse.MessageResponsePacket;
import io.ashu.protocol.command.requeset.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import java.util.Date;

public class LoginHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
  private String username;
  public LoginHandler(String username) {
    this.username = username;
  }

  private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
    byte[] bytes = this.username.getBytes(CharsetUtil.UTF_8);
    ByteBuf buf = ctx.alloc().buffer();
    buf.writeBytes(bytes);
    return buf;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println(new Date() + ": 尝试登陆");
    // TODO 这里为什么要用ctx去获取一个分配器

    LoginRequestPacket packet = new LoginRequestPacket();
    packet.setUserId(0);
    packet.setUsername("ashu");
    packet.setPassword("password");

    ctx.channel().writeAndFlush(packet);
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
      if (msg.isSuccess()) {
        System.out.println(new Date() + " : 登陆成功" );
      } else {
        System.err.println(new Date() + " : 登陆失败，失败原因[" + msg.getMsg() + "]");
      }
  }

}

package io.ashu.client;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.reponse.LoginResponsePacket;
import io.ashu.protocol.command.requeset.LoginRequestPacket;
import io.ashu.protocol.command.PacketCodec;
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
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf byteBuf = (ByteBuf) msg;
    System.out.println(new Date() + " :收到消息");

    PacketCodec codec = PacketCodec.instance;

    Packet packet = codec.decode(byteBuf);
    if (packet != null) {
      if (packet instanceof LoginResponsePacket) {
        LoginResponsePacket responsePacket = (LoginResponsePacket) packet;
        if (responsePacket.isSuccess()) {
          System.out.println(new Date() + " ：登陆成功" );
        } else {
          System.err.println(new Date() + " : 登陆失败，失败原因[" + responsePacket.getMsg() + "]");
        }
      }

    } else {
      // 消息穿透
      ctx.fireChannelRead(msg);
    }

//    super.channelRead(ctx, msg);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println(new Date() + ": 尝试登陆");
    // TODO 这里为什么要用ctx去获取一个分配器

    LoginRequestPacket packet = new LoginRequestPacket();
    packet.setUserId(0);
    packet.setUsername("ashu");
    packet.setPassword("password");

    PacketCodec codec = PacketCodec.instance;

    ByteBuf buf = codec.encode(packet);

    ctx.channel().writeAndFlush(buf);
  }

  private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
    byte[] bytes = this.username.getBytes(CharsetUtil.UTF_8);
    ByteBuf buf = ctx.alloc().buffer();
    buf.writeBytes(bytes);
    return buf;
  }
}

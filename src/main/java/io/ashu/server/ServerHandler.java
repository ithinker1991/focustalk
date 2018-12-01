package io.ashu.server;

import io.ashu.protocol.command.reponse.LoginResponsePacket;
import io.ashu.protocol.command.reponse.MessageResponsePacket;
import io.ashu.protocol.command.requeset.LoginRequestPacket;
import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketCodec;
import io.ashu.protocol.command.requeset.MessageRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf byteBuf = (ByteBuf) msg;
    System.out.println(new Date() + " :收到一条用户消息");


    PacketCodec codec = PacketCodec.instance;
    Packet packet = codec.decode(byteBuf);
    if (packet instanceof LoginRequestPacket) {
      LoginRequestPacket requestPacket = (LoginRequestPacket) packet;
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
      ByteBuf response = codec.encode(responsePacket);
      ctx.channel().writeAndFlush(response);
    } else if (packet instanceof MessageRequestPacket) {
      MessageRequestPacket requestPacket = (MessageRequestPacket) packet;
      System.out.println(new Date() + " : 收到用户消息[" + requestPacket.getMessage() + "]");
      MessageResponsePacket responsePacket = new MessageResponsePacket();
      responsePacket.setMessage("服务端收到你的消息[" + requestPacket.getMessage() + "]");
      ctx.channel().writeAndFlush(codec.encode(responsePacket));
    }
  }

  private static boolean vailate(String username, String password) {
    return true;
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
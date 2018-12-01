package io.ashu.client;

import io.ashu.protocol.command.PacketCodec;
import io.ashu.protocol.command.requeset.MessageRequestPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.Date;
import java.util.Scanner;

public class NettyClient {
  private static final int MAX_RETRY = 5;
  public static final String HOST = "127.0.0.1";
  public static final int PORT = 8000;
  public static final String USERNAME = "尹树成";


  public static void main(String[] args) {
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();

    bootstrap
        .group(workerGroup)
        // TODO 可以学习以这种传class的好处
        .channel(NioSocketChannel.class)
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .option(ChannelOption.SO_KEEPALIVE, true)
        // TODO 这个配置什么含义？
        .option(ChannelOption.TCP_NODELAY, true)
        // TODO lambda的写法
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new ClientConnectionHandler(USERNAME));
          }
        });
    connect(bootstrap, HOST, PORT, MAX_RETRY);


  }

  private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
    bootstrap.connect(host, port).addListener(future -> {
      if (future.isSuccess()) {
        System.out.println("连接成功");
        Channel channel = ((ChannelFuture) future).channel();
        startConsoleThread(channel);
      } else if (retry == 0) {
        System.out.println("连接失败");
      } else {
        connect(bootstrap, host, port, retry - 1);
      }
    });
  }

  private static void startConsoleThread(Channel channel) {
    new Thread(() -> {
      while (!Thread.interrupted()) {
        System.out.print(new Date() + " 输入消息发送到服务端:");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        System.out.println(new Date() + " 发送消息[" + line + "]");
        MessageRequestPacket packet = new MessageRequestPacket();
        packet.setMessage(line);
        ByteBuf byteBuf = PacketCodec.instance.encode(packet);
        channel.writeAndFlush(byteBuf);
      }
    }).start();
  }
}

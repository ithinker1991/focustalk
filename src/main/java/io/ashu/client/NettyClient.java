package io.ashu.client;

import io.ashu.client.handler.GreateGroupResponseHandler;
import io.ashu.client.handler.LoginResponseHandler;
import io.ashu.client.handler.MessageResponseHandler;
import io.ashu.codec.Spliter;
import io.ashu.console.ConsoleCommandManager;
import io.ashu.console.impl.LoginCommand;
import io.ashu.protocol.command.requeset.LoginRequestPacket;
import io.ashu.protocol.command.requeset.MessageRequestPacket;
import io.ashu.codec.PacketDecoder;
import io.ashu.codec.PacketEncoder;
import io.ashu.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
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
            ch.pipeline().addLast(new Spliter());
            ch.pipeline().addLast(new PacketDecoder());
            ch.pipeline().addLast(new LoginResponseHandler(USERNAME));
            ch.pipeline().addLast(new MessageResponseHandler());
            ch.pipeline().addLast(new GreateGroupResponseHandler());


            ch.pipeline().addLast(new PacketEncoder());
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
        LoginCommand loginCommand = new LoginCommand();
        ConsoleCommandManager manager = new ConsoleCommandManager();
        Scanner sc = new Scanner(System.in);
        if (!SessionUtil.hasLogin(channel)) {
          loginCommand.exec(sc, channel);
          try {
            Thread.sleep(1000);
          } catch (InterruptedException ignored) {
          }
        } else {
          manager.exec(sc, channel);
        }
      }
    }).start();
  }
}

package io.ashu.server;

import io.ashu.codec.PacketCodecHandler;
import io.ashu.codec.Spliter;
import io.ashu.server.handler.AuthHandler;
import io.ashu.server.handler.CreateGroupRequestHandler;
import io.ashu.server.handler.GroupMessageRequestHandler;
import io.ashu.server.handler.HeartBeatRequestHandler;
import io.ashu.server.handler.IMHandler;
import io.ashu.server.handler.IMIdleStateHandler;
import io.ashu.server.handler.ListGroupMembersRequestHandler;
import io.ashu.server.handler.LoginRequestHandler;
import io.ashu.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class IMServer {
  private static final int MAX_RETRY = 5;
  public static final String HOST = "127.0.0.1";
  public static final int PORT = 8000;


  public static void main(String[] args) {
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    final ServerBootstrap bootstrap = new ServerBootstrap();

    bootstrap
        .group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .option(ChannelOption.SO_BACKLOG, 1024)
        .childOption(ChannelOption.SO_KEEPALIVE, true)
        .childOption(ChannelOption.TCP_NODELAY, true)
        .childHandler((new ChannelInitializer<NioSocketChannel>() {
          @Override
          protected void initChannel(NioSocketChannel ch) throws Exception {
            ch.pipeline().addLast(new IMIdleStateHandler());
            ch.pipeline().addLast(new Spliter());

            ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
            ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
            ch.pipeline().addLast(new HeartBeatRequestHandler());
            ch.pipeline().addLast(AuthHandler.INSTANCE);
            ch.pipeline().addLast(IMHandler.INSTANCE);
          }
        }));

    bind(bootstrap, PORT);

  }

  private static void bind(final ServerBootstrap bootstrap, final int port) {
    bootstrap.bind(port).addListener(future -> {
      if (future.isSuccess()) {
        System.out.println("端口[" + port + "]绑定成功");
      } else {
        System.out.println("端口[" + port + "]绑定失败");
      }
    });
  }
}

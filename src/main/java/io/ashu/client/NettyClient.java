package io.ashu.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
      } else if (retry == 0) {
        System.out.println("连接失败");
      } else {
        connect(bootstrap, host, port, retry - 1);
      }
    });
  }
}

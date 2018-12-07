package io.ashu.codec;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import java.nio.ByteBuffer;
import java.util.List;

@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {
  public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();
  private PacketCodecHandler() {}

  @Override
  protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
    ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
    PacketCodec.INSTANCE.encode(byteBuf, msg);
    out.add(byteBuf);
  }

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
    out.add(PacketCodec.INSTANCE.decode(msg));
  }
}

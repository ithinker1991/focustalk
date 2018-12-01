package io.ashu.codec;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

  @Override
  protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
    PacketCodec.INSTANCE.encode(out, msg);
  }
}

package io.ashu.codec;

import io.ashu.protocol.command.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class Spliter extends LengthFieldBasedFrameDecoder {

  private static final int LENGTH_FILED_OFFSET = 7;
  private static final int LENGHT_FILED_LENGTH = 4;

  public Spliter() {
    super(Integer.MAX_VALUE, LENGTH_FILED_OFFSET, LENGHT_FILED_LENGTH);
  }

  @Override
  protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
    if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
      ctx.channel().close();
      return null;
    }
    return super.decode(ctx, in);
  }
}

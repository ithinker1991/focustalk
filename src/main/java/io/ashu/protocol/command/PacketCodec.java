package io.ashu.protocol.command;


import io.ashu.protocol.command.reponse.LoginResponsePacket;
import io.ashu.protocol.command.requeset.LoginRequestPacket;
import io.ashu.serialize.Serializer;
import io.ashu.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.util.HashMap;
import java.util.Map;

public class PacketCodec {
  private static final int MAGIC_NUMBER = 0xabcd1234;
  private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
  private static final Map<Byte, Serializer> serializerMap;

  public static final PacketCodec INSTANCE = new PacketCodec();

  static {
    packetTypeMap = new HashMap<>();
    packetTypeMap.put(PacketType.LOING_REQUEST, LoginRequestPacket.class);
    packetTypeMap.put(PacketType.LOING_REPONSE, LoginResponsePacket.class);

    serializerMap = new HashMap<>();
    Serializer serializer = new JSONSerializer();
    serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
  }

  public ByteBuf encode(Packet packet) {
    ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

    byte[] bytes = Serializer.DEFAULT.serialize(packet);

    byteBuf.writeInt(MAGIC_NUMBER);
    byteBuf.writeByte(packet.getVersion());
    byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
    byteBuf.writeByte(packet.getCommand());
    byteBuf.writeInt(bytes.length);
    byteBuf.writeBytes(bytes);

    return byteBuf;
  }

  public Packet decode(ByteBuf byteBuf) {
    byteBuf.skipBytes(4);
    byteBuf.skipBytes(1);

    byte serializerAlgorithm = byteBuf.readByte();
    byte command = byteBuf.readByte();
    int length = byteBuf.readInt();
    byte[] bytes = new byte[length];
    byteBuf.readBytes(bytes);

    Serializer serializer = serializerMap.get(serializerAlgorithm);
    Class<? extends Packet> clazz = packetTypeMap.get(command);

    if (clazz != null && serializer != null) {
      return serializer.deserialize(clazz, bytes);
    }
    return null;

  }

}

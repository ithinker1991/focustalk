package io.ashu.protocol.command;


import io.ashu.protocol.command.reponse.LoginResponsePacket;
import io.ashu.protocol.command.reponse.MessageResponsePacket;
import io.ashu.protocol.command.requeset.LoginRequestPacket;
import io.ashu.protocol.command.requeset.MessageRequestPacket;
import io.ashu.serialize.Serializer;
import io.ashu.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PacketCodec {
  private static final int MAGIC_NUMBER = 0xabcd1234;
  private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
  private static final Map<Byte, Serializer> serializerMap;

  public static final PacketCodec instance = new PacketCodec();

  static {
    packetTypeMap = new HashMap<>();
    packetTypeMap.put(PacketType.LOING_REQUEST, LoginRequestPacket.class);
    packetTypeMap.put(PacketType.LOING_RESPONSE, LoginResponsePacket.class);
    packetTypeMap.put(PacketType.MESSAGE_REQUEST, MessageRequestPacket.class);
    packetTypeMap.put(PacketType.MESSAGE_RESPONSE, MessageResponsePacket.class);

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
    } else {
      System.err.println(new Date() + "无法decode消息[" + byteBuf+"]" + ",command["+command + "]");
    }
    return null;

  }

}

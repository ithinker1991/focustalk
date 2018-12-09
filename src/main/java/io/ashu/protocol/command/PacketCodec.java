package io.ashu.protocol.command;


import com.sun.xml.internal.ws.server.sei.MessageFiller.Header;
import io.ashu.protocol.command.reponse.CreateGroupResponsePacket;
import io.ashu.protocol.command.reponse.GroupMessageResponsePacket;
import io.ashu.protocol.command.reponse.HeartBeatResponsePacket;
import io.ashu.protocol.command.reponse.ListGroupMembersResponsePacket;
import io.ashu.protocol.command.reponse.LoginResponsePacket;
import io.ashu.protocol.command.reponse.MessageResponsePacket;
import io.ashu.protocol.command.requeset.CreateGroupRequestPacket;
import io.ashu.protocol.command.requeset.GroupMessageRequestPacket;
import io.ashu.protocol.command.requeset.HeartBeatRequestPacket;
import io.ashu.protocol.command.requeset.ListGroupMembersRequestPacket;
import io.ashu.protocol.command.requeset.LoginRequestPacket;
import io.ashu.protocol.command.requeset.MessageRequestPacket;
import io.ashu.serialize.Serializer;
import io.ashu.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PacketCodec {
  public static final int MAGIC_NUMBER = 0xabcd1234;
  private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
  private static final Map<Byte, Serializer> serializerMap;

  public static final PacketCodec INSTANCE = new PacketCodec();

  static {
    packetTypeMap = new HashMap<>();
    packetTypeMap.put(PacketType.LOING_REQUEST, LoginRequestPacket.class);
    packetTypeMap.put(PacketType.LOING_RESPONSE, LoginResponsePacket.class);
    packetTypeMap.put(PacketType.MESSAGE_REQUEST, MessageRequestPacket.class);
    packetTypeMap.put(PacketType.MESSAGE_RESPONSE, MessageResponsePacket.class);
    packetTypeMap.put(PacketType.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
    packetTypeMap.put(PacketType.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
    packetTypeMap.put(PacketType.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
    packetTypeMap.put(PacketType.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
    packetTypeMap.put(PacketType.GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
    packetTypeMap.put(PacketType.GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
    packetTypeMap.put(PacketType.HEART_BEAT_REQUEST, HeartBeatRequestPacket.class);
    packetTypeMap.put(PacketType.HEART_BEAT_RESPONSE, HeartBeatResponsePacket.class);


    serializerMap = new HashMap<>();
    Serializer serializer = new JSONSerializer();
    serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
  }

  public void encode(ByteBuf byteBuf, Packet packet) {

    byte[] bytes = Serializer.DEFAULT.serialize(packet);

    byteBuf.writeInt(MAGIC_NUMBER);
    byteBuf.writeByte(packet.getVersion());
    byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
    byteBuf.writeByte(packet.getCommand());
    byteBuf.writeInt(bytes.length);
    byteBuf.writeBytes(bytes);

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

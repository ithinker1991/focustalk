package io.ashu.protocol.command.reponse;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketType;
import lombok.Data;

@Data
public class GroupMessageResponsePacket extends Packet {
  String message;

  @Override
  public Byte getCommand() {
    return PacketType.GROUP_MESSAGE_RESPONSE;
  }
}

package io.ashu.protocol.command.requeset;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketType;
import lombok.Data;

@Data
public class GroupMessageRequestPacket extends Packet {
  String groupId;
  String message;

  @Override
  public Byte getCommand() {
    return PacketType.GROUP_MESSAGE_REQUEST;
  }
}

package io.ashu.protocol.command.requeset;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketType;
import lombok.Data;

@Data
public class ListGroupMembersRequestPacket extends Packet {
  private String id;

  @Override
  public Byte getCommand() {
    return PacketType.LIST_GROUP_MEMBERS_REQUEST;
  }
}

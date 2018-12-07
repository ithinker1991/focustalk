package io.ashu.protocol.command.reponse;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketType;
import java.util.List;
import lombok.Data;

@Data
public class ListGroupMembersResponsePacket extends Packet {
  List<String> userIdList;

  @Override
  public Byte getCommand() {
    return PacketType.LIST_GROUP_MEMBERS_RESPONSE;
  }
}

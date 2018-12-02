package io.ashu.protocol.command.reponse;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketType;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {
  String userId;
  String userName;

  boolean success;
  String msg;

  @Override
  public Byte getCommand() {
    return PacketType.LOING_RESPONSE;
  }
}

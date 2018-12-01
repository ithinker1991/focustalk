package io.ashu.protocol.command.reponse;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketType;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {

  boolean success;
  String msg;

  @Override
  public Byte getCommand() {
    return PacketType.LOING_REPONSE;
  }
}

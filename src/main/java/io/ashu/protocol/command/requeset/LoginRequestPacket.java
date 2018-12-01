package io.ashu.protocol.command.requeset;

import io.ashu.protocol.command.PacketType;
import io.ashu.protocol.command.Packet;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {
  private Integer userId;
  private String username;
  private String password;

  @Override
  public Byte getCommand() {
    return PacketType.LOING_REQUEST;
  }
}

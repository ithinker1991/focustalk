package io.ashu.protocol.command;

import lombok.Data;

@Data
public class LoginRequestPacket extends  Packet {
  private Integer userId;
  private String username;
  private String password;

  @Override
  public Byte getCommand() {
    return Command.LOING_REQUEST;
  }
}

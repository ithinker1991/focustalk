package io.ashu.protocol.command.requeset;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketType;
import lombok.Data;

@Data
public class MessageRequestPacket extends Packet {

  private String message;

  @Override
  public Byte getCommand() {
    return PacketType.MESSAGE_REQUEST;
  }
}

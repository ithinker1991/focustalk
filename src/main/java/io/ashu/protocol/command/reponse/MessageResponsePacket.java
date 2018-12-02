package io.ashu.protocol.command.reponse;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketType;
import lombok.Data;

@Data
public class MessageResponsePacket extends Packet {
  private String fromUserId;
  private String fromUserName;
  private String message;

  @Override
  public Byte getCommand() {
    return PacketType.MESSAGE_RESPONSE;
  }

  @Override
  public String toString() {
    return "收到fromUserId[" + fromUserId + "],fromUserName[" + fromUserName +"]:" + message;
  }
}

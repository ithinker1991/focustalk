package io.ashu.protocol.command.requeset;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketType;

public class HeartBeatRequestPacket extends Packet {

  @Override
  public Byte getCommand() {
    return PacketType.HEART_BEAT_REQUEST;
  }
}

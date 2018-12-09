package io.ashu.protocol.command.reponse;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketType;

public class HeartBeatResponsePacket extends Packet {

  @Override
  public Byte getCommand() {
    return PacketType.HEART_BEAT_RESPONSE;
  }
}

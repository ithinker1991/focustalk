package io.ashu.protocol.command.reponse;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketType;
import lombok.Data;

@Data
public class CreateGroupResponsePacket extends Packet {
    boolean success;
    String reason;

    @Override
    public Byte getCommand() {
        return PacketType.CREATE_GROUP_RESPONSE;
    }
}

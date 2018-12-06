package io.ashu.protocol.command.requeset;

import io.ashu.protocol.command.Packet;
import io.ashu.protocol.command.PacketType;
import lombok.Data;

import java.util.List;

@Data
public class CreateGroupRequestPacket extends Packet {
    private String id;
    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return PacketType.CREATE_GROUP_REQUEST;
    }
}

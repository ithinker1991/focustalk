package io.ashu.protocol.command;

public interface PacketType {
  Byte LOING_REQUEST = 1;
  Byte LOING_RESPONSE = 2;
  Byte MESSAGE_REQUEST = 3;
  Byte MESSAGE_RESPONSE = 4;
  Byte CREATE_GROUP_REQUEST = 5;
  Byte CREATE_GROUP_RESPONSE = 6;
  Byte LIST_GROUP_MEMBERS_REQUEST = 7;
  Byte LIST_GROUP_MEMBERS_RESPONSE = 8;
}

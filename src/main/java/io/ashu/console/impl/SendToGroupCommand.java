package io.ashu.console.impl;

import io.ashu.console.ConsoleCommand;
import io.ashu.protocol.command.requeset.GroupMessageRequestPacket;
import io.netty.channel.Channel;
import java.util.Date;
import java.util.Scanner;

public class SendToGroupCommand implements ConsoleCommand {

  @Override
  public void exec(Scanner scanner, Channel channel) {
    System.out.println(new Date() + " 选择你要发送的群组和消息:");
    String groupId = scanner.next();
    String message = scanner.next();
    GroupMessageRequestPacket requestPacket = new GroupMessageRequestPacket();
    requestPacket.setGroupId(groupId);
    requestPacket.setMessage(message);
    channel.writeAndFlush(requestPacket);
  }
}

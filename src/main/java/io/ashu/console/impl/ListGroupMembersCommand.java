package io.ashu.console.impl;

import io.ashu.console.ConsoleCommand;
import io.ashu.protocol.command.requeset.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;
import java.util.Date;
import java.util.Scanner;

public class ListGroupMembersCommand implements ConsoleCommand {

  @Override
  public void exec(Scanner scanner, Channel channel) {
    System.out.println(new Date() + " 请输入要查询的groupID:");
    String groupId = scanner.next();

    ListGroupMembersRequestPacket request = new ListGroupMembersRequestPacket();
    request.setId(groupId);
    channel.writeAndFlush(request);
  }
}

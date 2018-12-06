package io.ashu.console.impl;

import io.ashu.console.ConsoleCommand;
import io.ashu.protocol.command.requeset.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Date;
import java.util.Scanner;

public class SendToCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        System.out.println(new Date() + " 输入消息发送到服务端:");
        MessageRequestPacket packet = new MessageRequestPacket();
        packet.setToUserId(sc.next());
        packet.setMessage(sc.next());
        channel.writeAndFlush(packet);
    }
}

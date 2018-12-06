package io.ashu.console.impl;

import io.ashu.console.ConsoleCommand;
import io.ashu.protocol.command.requeset.CreateGroupRequestPacket;
import io.ashu.protocol.command.requeset.MessageRequestPacket;
import io.netty.channel.Channel;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class CreateGroupCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        CreateGroupRequestPacket packet = new CreateGroupRequestPacket();
        System.out.println(new Date() + " 创建群组，请输入用户id,以逗号隔开:");
        packet.setUserIdList(Arrays.asList(sc.next().split(",")));
        System.out.println(new Date() + " 请输入组id:");
        packet.setId(sc.next());
        channel.writeAndFlush(packet);
    }
}

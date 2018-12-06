package io.ashu.console.impl;

import io.ashu.console.ConsoleCommand;
import io.ashu.protocol.command.requeset.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Date;
import java.util.Scanner;

public class LoginCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        System.out.println(new Date() + ": 尝试登陆");
        // TODO 这里为什么要用ctx去获取一个分配器

        LoginRequestPacket packet = new LoginRequestPacket();
        System.out.println("请输入id");
        packet.setUserId(sc.next());
        System.out.println("请输入用户名");
        packet.setUsername(sc.next());
        System.out.println("请输入密码");
        packet.setPassword(sc.next());
        channel.writeAndFlush(packet);
    }
}

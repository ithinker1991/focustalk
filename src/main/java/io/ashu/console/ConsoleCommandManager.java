package io.ashu.console;

import io.ashu.console.impl.CreateGroupCommand;
import io.ashu.console.impl.ListGroupMembersCommand;
import io.ashu.console.impl.SendToCommand;
import io.netty.channel.Channel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {
    private Map<String, ConsoleCommand> commandMap;


    public ConsoleCommandManager() {
        commandMap = new HashMap<>();
        commandMap.put("createGroup", new CreateGroupCommand());
        commandMap.put("sendTo", new SendToCommand());
        commandMap.put("ListMembers", new ListGroupMembersCommand());
    }


    @Override
    public void exec(Scanner scanner, Channel channel) {
        String commandType = scanner.next();
        ConsoleCommand command = commandMap.get(commandType);
        if (command != null) {
            command.exec(scanner, channel);
        } else {
            System.err.println(new Date() + " 不能识别该指令[" + commandType + "]");
        }

    }
}

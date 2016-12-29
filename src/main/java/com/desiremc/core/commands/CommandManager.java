package com.desiremc.core.commands;

import com.desiremc.core.DesireCore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sneling on 12/11/2016 for Core.
 * <p>
 * Copyright &copy; 2016 - Sneling
 * <p>
 * You are not allowed to copy/use any of the code contained in this file.
 * If you have any questions about this, what it means, and in which circumstances you're allowed to use this code,
 * send an email to: contact@sneling.net
 */
public class CommandManager {

    private List<DesireCommand> commands;
    private CommandRunner runner;

    public CommandManager() {
        commands = new ArrayList<>();
        runner = new CommandRunner();
    }

    public void registerCommand(DesireCommand command){
        DesireCore.getInstance()
                .getCommand(
                        command.getName()
                ).setExecutor(
                        runner);
        commands.add(command);
    }

    public List<DesireCommand> getCommands() {
        return commands;
    }
}
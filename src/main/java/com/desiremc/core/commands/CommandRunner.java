package com.desiremc.core.commands;

import com.desiremc.core.DesireCore;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.PermChecker;
import com.desiremc.core.util.logger.Logger;
import com.desiremc.core.util.numbers.IntegerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
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
public class CommandRunner implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Logger.debug(sender.getName() + " sent a request for " + command.getName());

        List<DesireCommand> commands = DesireCore.getInstance().getCommandManager().getCommands();

        for (DesireCommand cmd : commands) {
            if (cmd.isValidCall(command.getName())) {
                Logger.debug("Found matching command for " + cmd.getName() + ", as request by " + sender.getName());
                ArrayList<String> argList = new ArrayList<>();
                argList.addAll(Arrays.asList(args));
                findAndExecuteCommand(cmd, sender, argList);
                return true;
            }
        }

        return true;
    }

    private void findAndExecuteCommand(DesireCommand base, CommandSender sender, ArrayList<String> args){
        if(!PermChecker.has(sender, base.getPermission())){
            sender.sendMessage(Lang.COMMAND_NO_PERM);
            return;
        }

        if(args.size() == 0){
            Logger.debug("Running command " + base.getName() + " for " + sender.getName());

            if(!base.run(sender, args))
                HelpGenerator.sendHelp(sender, base, 1);
        }else{
            DesireCommand potentialChild = getMatchingChild(base, args.get(0));

            if(potentialChild == null){
                if(args.get(0).equalsIgnoreCase("help") || args.get(0).equalsIgnoreCase("?")){
                    int page;

                    if(args.size() >= 2){
                        page = IntegerUtil.getInt(args.get(1), 1);
                    }else{
                        page = 1;
                    }

                    HelpGenerator.sendHelp(sender, base, page);
                }else if(!base.run(sender, args)){
                    HelpGenerator.sendHelp(sender, base, 1);
                }
            }else{
                Logger.debug("Found child " + potentialChild.getName() + " for command " + base.getName());
                args.remove(0);

                findAndExecuteCommand(potentialChild, sender, args);
            }
        }
    }

    private DesireCommand getMatchingChild(DesireCommand cmd, String childName){
        if(cmd.getChildCommands() == null || cmd.getChildCommands().isEmpty())
            return null;

        List<DesireCommand> childs = cmd.getChildCommands();
        for(DesireCommand child: childs)
            if(child.isValidCall(childName))
                return child;

        return null;
    }

}
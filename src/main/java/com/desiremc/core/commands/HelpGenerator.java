package com.desiremc.core.commands;

import com.desiremc.core.lang.Lang;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

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
public class HelpGenerator {

    private static final int COMMANDS_PER_PAGE = 4;

    public static void sendHelp(CommandSender sender, DesireCommand cmd, int page){
        Logger.debug("Generating help for command '" + cmd.getName() + "' at page " + page + "' as requested by " + sender.getName());
        sender.sendMessage(generateHelp(cmd, sender, page));
    }

    private static String generateHelp(DesireCommand cmd, CommandSender sender, int page){
        if(!cmd.getPermission().has(sender))
            return Lang.COMMAND_HELP_NO_PERM;

        List<DesireCommand> childs = getAvailableChildsForPage(cmd, sender, page);

        if(childs == null){ // No childs for this command for this sender
            if(cmd.getUsage().equals("empty")){
                return Lang.COMMAND_HELP_NONE;
            }else if(cmd.getUsage().equals("description")){
                return generateHeader(cmd, "Description") + "\n" + generateInfo(cmd);
            }else{
                return generateHeader(cmd, "Info") + "\n" + generateInfo(cmd);
            }
        }

        if(childs.size() == 0){
            Logger.debug(sender.getName() + " tried to open help page [" + page + "] for [" + cmd.getName() + "] but it didn't exist for him.");
            return Lang.COMMAND_HELP_NONE;
        }

        int nbOfPage;

        //noinspection ConstantConditions
        if(getAvailableChilds(cmd, sender).size() % COMMANDS_PER_PAGE == 0){
            //noinspection ConstantConditions
            nbOfPage = getAvailableChilds(cmd, sender).size() / COMMANDS_PER_PAGE;
        }else{
            //noinspection ConstantConditions
            nbOfPage = getAvailableChilds(cmd, sender).size() / COMMANDS_PER_PAGE + 1;
        }

        String info = "";

        if(page == 1)
            info = generateInfo(cmd) + "\n";

        return generateHeader(cmd, page + "/" + nbOfPage) + "\n" + info + generateHelpList(childs);
    }

    private static String generateInfo(DesireCommand cmd){
        if(cmd.getUsage().equals("empty"))
            return "";

        if(cmd.getUsage().equals("description"))
            return ChatColor.WHITE + " " + cmd.getDescription();

        return ChatColor.GRAY + " Usage: " + cmd.getUsage() + "\n "
                + ChatColor.GRAY + cmd.getDescription();
    }

    private static String generateHeader(DesireCommand cmd, String info){
        return "\n"
                + ChatColor.GRAY + " - "
                + ChatColor.AQUA + "[Help]"
                + ChatColor.GRAY + " -- "
                + ChatColor.YELLOW + cmd.getName()
                + ChatColor.GRAY + " -- "
                + ChatColor.YELLOW + info;
    }

    private static List<DesireCommand> getAvailableChilds(DesireCommand command, CommandSender sender){
        List<DesireCommand> commands = new ArrayList<>();

        if(command.getChildCommands() == null || command.getChildCommands().isEmpty())
            return null;

        for(DesireCommand child: command.getChildCommands())
            if(child.isVisibleFor(sender))
                commands.add(child);

        return commands;
    }

    private static List<DesireCommand> getAvailableChildsForPage(DesireCommand command, CommandSender sender, int page){
        List<DesireCommand> childs = getAvailableChilds(command, sender);

        if(childs == null)
            return null;

        List<DesireCommand> childsForPage = new ArrayList<>();

        page--;
        int skip = page * COMMANDS_PER_PAGE;

        for(int i = 0; i < COMMANDS_PER_PAGE; i++){
            try{
                childsForPage.add(childs.get(skip + i));
            }catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        return childsForPage;
    }

    private static String generateHelpList(List<DesireCommand> cmds){
        // TODO Transform into a tellraw to allow for hovering and clicking

        String rslt = "";
        for(DesireCommand cmd: cmds){
            rslt += generateHelpLine(cmd) + "\n";
        }

        return rslt;
    }

    private static String generateHelpLine(DesireCommand cmd){
        String name = cmd.getName();

//        if(!cmd.getArguments().isEmpty())
//            name += " ...";
//
        return ChatColor.YELLOW + " " + name + ChatColor.GRAY + " - " + cmd.getDescription();
    }

}
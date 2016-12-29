package com.desiremc.core.staff.commands.chat;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.commands.args.ArgInfo;
import com.desiremc.core.commands.args.ArgType;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Timothy Lampen on 11/18/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class ClearCommand extends DesireCommand{

    public ClearCommand(){
        super("clear");
        addAliases("c");
        setDescription("Clears the chat.");
    }

    @Override
    public void execute(CommandSender sender, List<String> args){
        for(int i =0; i <50; i++){
            Bukkit.broadcastMessage("");
        }
        Bukkit.broadcastMessage(Lang.STAFF_COMMAND_CLEARCHAT.replaceAll("%PLAYER%", sender.getName()));
    }
}

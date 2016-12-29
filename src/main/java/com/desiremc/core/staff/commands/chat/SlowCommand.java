package com.desiremc.core.staff.commands.chat;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.commands.args.ArgInfo;
import com.desiremc.core.commands.args.ArgRequirement;
import com.desiremc.core.commands.args.ArgType;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.staff.StaffModule;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by Timothy Lampen on 11/18/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class SlowCommand extends DesireCommand {

    public SlowCommand(){
        super("slow");
        setDescription("Sets the speed that players can send messages to 15sec for X amount of seconds.");
        addAliases("s");
        addArgument(new ArgInfo("time", ArgType.MANDATORY, ArgRequirement.INTEGER));
    }

    @Override
    public void execute(CommandSender sender, List<String> args){
        Integer time = Integer.parseInt(args.get(0));
        StaffModule.getInstance().setMuteTime(time*1000+System.currentTimeMillis());
        sender.sendMessage(Lang.STAFF_COMMAND_SLOW_CHAT.replaceAll("%TIME%", time + ""));
    }
}

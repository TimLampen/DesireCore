package com.desiremc.core.enderchest.commands;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.commands.args.ArgInfo;
import com.desiremc.core.commands.args.ArgRequirement;
import com.desiremc.core.commands.args.ArgType;
import com.desiremc.core.enderchest.EnderChestModule;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by Timothy Lampen on 11/15/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class EnderChestCommand extends DesireCommand{

    public EnderChestCommand(){
        super("enderchest");
        addAliases("ec");
        addArgument(new ArgInfo("true/false", ArgType.MANDATORY, ArgRequirement.BOOLEAN));
        setPermission(Perm.ENDERCHEST_TOGGLE);
        setDescription("Toggles the use of ender chests");
    }

    @Override
    public void execute(CommandSender sender, List<String> args){
        boolean b = Boolean.parseBoolean(args.get(0));
        String s = b ? "can" : "cannot";
        EnderChestModule.getInstance().setDisabled(!b);
        sender.sendMessage(Lang.ENDMANAGEMENT_COMMAND.replaceAll("%BOOLEAN%", s));
    }
}

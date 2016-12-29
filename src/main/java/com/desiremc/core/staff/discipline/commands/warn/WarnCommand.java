package com.desiremc.core.staff.discipline.commands.warn;

import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.commands.args.ArgInfo;
import com.desiremc.core.commands.args.ArgRequirement;
import com.desiremc.core.commands.args.ArgType;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.staff.StaffModule;

import com.desiremc.core.staff.discipline.DisciplineHandler;
import com.desiremc.core.staff.discipline.ReportType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Timothy Lampen on 11/16/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class WarnCommand extends DesireCommand{

    public WarnCommand(){
        super("Warn");
        setSenderType(Player.class);
        setPermission(Perm.STAFF_WARN);
        setDescription("Logs Warns connected to players");
        addArgument(new ArgInfo("target", ArgType.MANDATORY, ArgRequirement.PLAYER));
        addChild(new GetCommand());
    }

    @Override
    public void execute(CommandSender sender, List<String> args){
        Player player = (Player)sender;
        Player target = Bukkit.getPlayer(args.get(0));
        DisciplineHandler.showDisciplineInv(player, target, ReportType.WARN);
    }
}

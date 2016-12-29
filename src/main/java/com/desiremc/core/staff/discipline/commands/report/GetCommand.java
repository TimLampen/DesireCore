package com.desiremc.core.staff.discipline.commands.report;

import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.commands.args.ArgInfo;
import com.desiremc.core.commands.args.ArgType;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.staff.StaffModule;
import com.desiremc.core.staff.discipline.DisciplineHandler;
import com.desiremc.core.staff.discipline.ReportType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Timothy Lampen on 11/17/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class GetCommand extends DesireCommand{

    public GetCommand(){
        super("get");
        addAliases("g");
        addArgument(new ArgInfo("target", ArgType.MANDATORY));
        setPermission(Perm.STAFF_REPORT_GET);
        setDescription("Gets all the report against a certain player.");
        setSenderType(Player.class);
    }

    @Override
    public void execute(CommandSender sender, List<String> args){
        Player player = (Player) sender;
        String targetName = args.get(0);
        StaffModule.getInstance().getDisciplineHandler().showStaffDisciplineInv(player, targetName, ReportType.REPORT);
    }


}

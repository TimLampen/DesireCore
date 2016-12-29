package com.desiremc.core.staff.commands;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.staff.StaffModule;
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
public class AlertsCommand extends DesireCommand{

    public AlertsCommand(){
        super("alerts");
        addAliases("alert");
        setDescription("Toggles viewing new updates to admin-related things for players.");
        setPermission(Perm.STAFF_ALERTS);
        setSenderType(Player.class);
    }

    @Override
    public void execute(CommandSender sender, List<String> args){
        Player player = (Player)sender;
        boolean b = StaffModule.getInstance().getAlerts().contains(player.getUniqueId());
        if(b){
            StaffModule.getInstance().removeAlert(player);
        }
        else{
            StaffModule.getInstance().addAlert(player);
        }
    }
}

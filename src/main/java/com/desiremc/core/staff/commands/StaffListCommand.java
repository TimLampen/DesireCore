package com.desiremc.core.staff.commands;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.permissions.PermChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timothy Lampen on 11/16/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class StaffListCommand extends DesireCommand {

    public StaffListCommand(){
        super("stafflist");
        setDescription("Lists all currently online staff members.");
        setPermission(Perm.STAFF_LIST);
    }

    @Override
    public void execute(CommandSender sender, List<String> args){
        ArrayList<String> online = new ArrayList<String>();
        for(Player p : Bukkit.getOnlinePlayers()){
            if(PermChecker.has(p, Perm.STAFF_OWNER) || PermChecker.has(p, Perm.STAFF_ADMIN) || PermChecker.has(p, Perm.STAFF_MOD) || PermChecker.has(p, Perm.STAFF_HELPER)){
                online.add(Lang.STAFF_COMMAND_STAFF_LIST.replaceAll("%PLAYER%", p.getName()).replaceAll("%PREFIX%",         DesireCore.getInstance().getChat().getPlayerPrefix(p)));
            }
        }
        sender.sendMessage(ChatColor.GOLD + "Staff Currently Online:");
        for(String s : online){
            sender.sendMessage(s);
        }
    }
}

package com.desiremc.core.wrench.commands;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.util.inventory.InventoryUtil;
import com.desiremc.core.wrench.WrenchModule;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Timothy Lampen on 11/13/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file,
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class WrenchCommand extends DesireCommand{

    public WrenchCommand(){
        super("wrench");
        setPermission(Perm.WRENCH_COMMANDS_GIVE);
        setSenderType(Player.class);
        setDescription("Generates a wrench and adds it to your inventory");
        addAliases("w");
    }

    @Override
    public void execute(CommandSender sender, List<String> args){
        Player player = (Player) sender;
        player.sendMessage(Lang.WRENCH_GIVE);
        InventoryUtil.safeInventoryAdd(player, WrenchModule.getInstance().generateWrench(), "wrench");
    }

}

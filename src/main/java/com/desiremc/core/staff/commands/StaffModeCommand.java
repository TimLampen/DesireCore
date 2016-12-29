package com.desiremc.core.staff.commands;

import com.desiremc.core.DesireCore;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.permissions.Perm;
import com.desiremc.core.staff.StaffModule;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Timothy Lampen on 11/21/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com//
 */
public class StaffModeCommand extends DesireCommand {


    public StaffModeCommand(){
        super("staff");
        addAliases("staffmode");
        setDescription("Enters 'staff mode' enabling the user to gain access to certain functions.");
        setPermission(Perm.STAFF_STAFFMODE);
        setSenderType(Player.class);
    }

    @Override
    public void execute(CommandSender sender, List<String> args){
        Player player = (Player)sender;
        ArrayList<ItemStack> items = StaffModule.getInstance().getStaffModeHandler().getInventory(player.getUniqueId());
        if(items!=null){
            player.getInventory().clear();
            for(ItemStack is: items) {
                if (is != null) {
                    player.getInventory().addItem(is);
                }
            }
            StaffModule.getInstance().getStaffModeHandler().removeInventory(player.getUniqueId());
            player.sendMessage(Lang.STAFF_COMMAND_STAFFMODE_DEACTIVATE);
            player.setAllowFlight(false);
        }
        else{
            //isnt in staff mode
            items = new ArrayList<ItemStack>();
            for(ItemStack is : player.getInventory().getContents()){
                items.add(is);
            }
            StaffModule.getInstance().getStaffModeHandler().addInventory(player.getUniqueId(), items);
            player.getInventory().clear();
            int slot = 0;
            for(ItemStack is : StaffModule.getInstance().getStaffModeHandler().getStaffModeItems()){
                player.getInventory().setItem(slot, is);
                slot++;
            }
            player.setAllowFlight(true);
            player.sendMessage(Lang.STAFF_COMMAND_STAFFMODE_ACTIVATE);
            boolean b = StaffModule.getInstance().getAlerts().contains(player.getUniqueId());
            if(!b){
                StaffModule.getInstance().addAlert(player);
            }
        }
    }
}

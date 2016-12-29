package com.desiremc.core.classes.listeners;

import com.desiremc.core.classes.ClassModule;
import com.desiremc.core.classes.classes.HCFClass;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Timothy Lampen on 12/10/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class ClassItemUseListener implements Listener {

    //used for checking active items
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem()!=null && event.getAction().toString().contains("RIGHT")){
            ItemStack is = event.getItem();
            for(HCFClass hcfClass : ClassModule.getInstance().getClasses()){
                if(hcfClass.isApplicable(player, hcfClass.getArmorType())){
                    hcfClass.checkActiveItems(player, is.getType());
                }
            }
        }
    }
}

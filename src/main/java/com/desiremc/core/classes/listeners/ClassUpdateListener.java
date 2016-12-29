package com.desiremc.core.classes.listeners;

import com.desiremc.core.DesireCore;
import com.desiremc.core.classes.ClassModule;
import com.desiremc.core.classes.classes.HCFClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Timothy Lampen on 12/10/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class ClassUpdateListener implements Listener{

    @EventHandler
    public void onChange(InventoryClickEvent event){
        final Player player = (Player)event.getWhoClicked();
        boolean isShift = event.isShiftClick();
        Material armor = null;
        if(isShift){
            if(event.getCurrentItem()!=null && event.getSlotType()!= InventoryType.SlotType.ARMOR){
                armor = event.getCurrentItem().getType();
            }
        }
        else {
         if(event.getCursor().getType() != Material.AIR && event.getSlotType()== InventoryType.SlotType.ARMOR) {
                armor = event.getCursor().getType();
            }
        }
        if(armor!=null){//armor has been changed, check to see if they are in any class
            new BukkitRunnable(){
                @Override
                public void run() {
                    for(HCFClass hcfClass : ClassModule.getInstance().getClasses()){
                        if(hcfClass.isApplicable(player, hcfClass.getArmorType())){
                            hcfClass.addPlayer(player.getUniqueId());
                        }
                        else{
                            hcfClass.removePlayer(player.getUniqueId());
                        }
                    }
                }
            }.runTaskLater(DesireCore.getInstance(), 1);
        }
        else if(event.getSlotType()== InventoryType.SlotType.ARMOR){
            for(HCFClass hcfClass : ClassModule.getInstance().getClasses()){
                if(hcfClass.getPlayers().contains(player.getUniqueId())){
                    hcfClass.removePlayer(player.getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        for(HCFClass hcfClass : ClassModule.getInstance().getClasses()){
            if(hcfClass.isApplicable(player, hcfClass.getArmorType())){
                hcfClass.addPlayer(player.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        for(HCFClass hcfClass : ClassModule.getInstance().getClasses()){
            if(hcfClass.getPlayers().contains(player.getUniqueId())){
                hcfClass.removePlayer(player.getUniqueId());
            }
        }
    }
}
